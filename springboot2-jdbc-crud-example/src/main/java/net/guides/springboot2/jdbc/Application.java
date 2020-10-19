package net.guides.springboot2.jdbc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.config.SchedulerBeanDefinitionParser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import net.guides.springboot2.jdbc.model.DTServices;
import net.guides.springboot2.jdbc.model.SQLColumn;
import net.guides.springboot2.jdbc.repository.ClientJDBCRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	static String mysqlpath = "C:\\Program Files (x86)\\MySQL\\MySQL Server 4.1";
	static Random rand = new Random();
	static String jsonStr;
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	public ClientJDBCRepository clientjdbcrep;

	@Override
	public void run(String... args) throws Exception {
		/*
		 * 
		 * logger.info("Application Started :"); jsonStr = clientjdbcrep.getjson();
		 * 
		 * final long timeInterval = 5000;
		 * 
		 * Runnable runnable = new Runnable() {
		 * 
		 * public void run() {
		 * 
		 * while (true) {
		 * 
		 * logger.info("scheduler run");
		 * 
		 * Httpget(); Httppost();
		 * 
		 * try {
		 * 
		 * Thread.sleep(timeInterval);
		 * 
		 * } catch (InterruptedException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * };
		 * 
		 * Thread thread = new Thread(runnable);
		 * 
		 * thread.start();
		 * 
		 */
		Httpget();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	public void Httpget() {

		try {

			Client client = Client.create();

			WebResource webResource = client.resource("http://192.168.5.115:8088/oakscode/process");
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			logger.info("get method");

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String jsonStr = response.getEntity(String.class);

			// create dump file from response

			FileOutputStream fos = new FileOutputStream(
					"C:\\DTJerseyApp\\Files\\" + rand.nextInt(1000) + "GetResponse.txt", true);

			List<Object> genQry = clientjdbcrep.GenerateQry(jsonStr); // step 1

			for (Object obj : genQry) {
				
				ArrayList<String> updatedQry = new ArrayList<String>();
				System.out.println("generated Qry : " + obj.toString());
				String[] param = obj.toString().split(",");

				List<Map<String, Object>> li = clientjdbcrep.Fetchdata(obj.toString());// Data fetch
				String json = new Gson().toJson(li);

				ArrayList<SQLColumn> li2 = clientjdbcrep.FetchMetadata(obj.toString());
				String json2 = new Gson().toJson(li2);

				FileOutputStream DataFos = new FileOutputStream(
						"C:\\DTJerseyApp\\Files\\" + rand.nextInt(1000) + "Data.json", true);
				byte[] b = json.toString().getBytes(); // converts string into bytes
				DataFos.write(b); // writes bytes into file
				DataFos.close(); // close the file
				
				for (Object obj1 : li) {
					System.out.println("Fetch Data : " + obj1.toString());
				}
				if(li.size() > 0) {
					updatedQry = Httppost(json2, json, param[1]);
				}else {
					System.out.println("Empty Result Set");
				}

				

				
				for (String qry : updatedQry) {
					System.out.println("Updated Qry : " + qry.toString());
					int i = clientjdbcrep.insertUpdatedRows(qry);
					System.out.println("Update Status :" + i);
				}
			}

			System.out.println("Output: " + jsonStr);

			byte[] b = jsonStr.getBytes(); // converts string into bytes

			fos.write(b); // writes bytes into file
			fos.close(); // close the file
			System.out.println("file saved.");

		} catch (Exception e) {

			logger.info("Exception : Application,Httpget()");
			logger.error("Message"+e.getMessage());
			logger.error("StackTrace"+e.getStackTrace());
			
			e.printStackTrace();

		}
	}

	public  ArrayList<String> Httppost(String meta, String json, String table) {
		ArrayList<String> updatedQry = new ArrayList<String>();

		try {

			Client client = Client.create();

			WebResource webResource = client.resource("http://192.168.5.115:8088/oakscode/processpost");

			ClientResponse response = webResource.accept("application/json").type("application/json")
					.post(ClientResponse.class, table + "@" + json + " Metadata" + meta);

			String output = response.getEntity(String.class);

			String[] param = output.toString().split("@");
			String[] param1 = param[1].toString().split("Metadata");

			System.out.println("TableName : " + param[0]);
			System.out.println("TableRows : " + param1[0]);
			System.out.println("Meta Data :" + param1[1]);

			updatedQry = buildbatchQuery(param[0], param1[0], param1[1]);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

		} catch (Exception e) {

			logger.info("Exception : Application,HttpPost()");
			logger.info("ErrorMsg  : "+e.getMessage());
			logger.error("Stacktrace : "+e.getStackTrace());
			e.printStackTrace();

		}
		return updatedQry;
	}

	private  ArrayList<String> buildbatchQuery(String tableName, String tableData, String metaData) {

		Gson gson = new Gson();
		ArrayList<SQLColumn> tMeta = gson.fromJson(metaData, new TypeToken<ArrayList<SQLColumn>>() {
		}.getType());
		StringBuilder insertQueryP = new StringBuilder("UPDATE ").append(tableName)
				.append(" SET WS_status = 'Y' WHERE ");

		ArrayList<String> Qrylist = new ArrayList<String>();

		insertQueryP.deleteCharAt(insertQueryP.length() - 1); // Remove last comma

		ArrayList<Object> tData = gson.fromJson(tableData, new TypeToken<ArrayList<Object>>() {
		}.getType());

		for (Object p : tData) {

			String json = gson.toJson(p);
			JsonParser parser = new JsonParser();
			JsonElement rootNode = parser.parse(json);
			StringBuilder setvalue = new StringBuilder(insertQueryP.toString());

			if (rootNode.isJsonObject()) {
				JsonObject details = rootNode.getAsJsonObject();

				for (SQLColumn tm : tMeta) {

					JsonElement nameNode = details.get(tm.getName());
					try {
						if ("date".equalsIgnoreCase(tm.getType()) || "datetime".equalsIgnoreCase(tm.getType())
								|| "timestamp".equalsIgnoreCase(tm.getType())) {

						} else {

							if (!nameNode.getAsString().trim().equals(""))

							{
								setvalue.append(" " + tm.getName() + " = ");
								setvalue.append("'" + nameNode.getAsString() + "' AND");
							}
						}
					} catch (NullPointerException e) {
						
						logger.info("Exception : Application,buildbatchQuery(),Forloop metaData For Date time chack");
						logger.info("msg :"+e.getMessage());
						logger.error("stacktrace :"+e.getStackTrace());
						
					}
				}
			}

			setvalue.deleteCharAt(setvalue.length() - 1);
			setvalue.deleteCharAt(setvalue.length() - 1);
			setvalue.deleteCharAt(setvalue.length() - 1);
			Qrylist.add(setvalue.toString());
		}
		return Qrylist;
	}
}
