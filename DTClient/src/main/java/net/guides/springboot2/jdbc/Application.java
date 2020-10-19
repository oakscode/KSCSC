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

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("file:C:\\DTJerseyClient\\config_server\\application.properties")
public class Application implements CommandLineRunner {

	static Random rand = new Random();
	static Logger LOGGER = new InitLogger("Application").getLOGGER();
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	public ClientJDBCRepository clientjdbcrep;

	@Override
	public void run(String... args) throws Exception {

		LOGGER.info("Application Started :");

		final long timeInterval = 5000;

		Runnable runnable = new Runnable() {

			public void run() {

				while (true) {

					
					Httpget();

					try {

						Thread.sleep(timeInterval);

					} catch (InterruptedException e) {

						e.printStackTrace();

					}

				}

			}

		};

		Thread thread = new Thread(runnable);

		thread.start();

		
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	public void Httpget() {

		try {

			Client client = Client.create();

			WebResource webResource = client.resource("http://192.168.5.115:8088/oakscode/process");
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String jsonStr = response.getEntity(String.class);

			// create dump file from response

			List<Object> genQry = clientjdbcrep.GenerateQry(jsonStr); // step 1

			for (Object obj : genQry) {

				ArrayList<String> updatedQry = new ArrayList<String>();
				System.out.println("generated Qry : " + obj.toString());
				String[] param = obj.toString().split(",");

				List<Map<String, Object>> li = clientjdbcrep.Fetchdata(obj.toString());// Data fetch
				String json = new Gson().toJson(li);

				ArrayList<SQLColumn> li2 = clientjdbcrep.FetchMetadata(obj.toString());
				String json2 = new Gson().toJson(li2);

				for (Object obj1 : li) {
					System.out.println("Fetch Data : " + obj1.toString());
				}
				if (li.size() > 0) {
					updatedQry = Httppost(json2, json, param[1]);
				} else {
					System.out.println("Empty Result Set");
				}

				for (String qry : updatedQry) {
					System.out.println("Updated Qry : " + qry.toString());
					int i = clientjdbcrep.insertUpdatedRows(qry);
					LOGGER.info("");
					System.out.println("Update Status :" + i);
				}
			}

			System.out.println("Output: " + jsonStr);

			System.out.println("file saved.");

		} catch (Exception e) {

			LOGGER.info("Exception : Application,Httpget()");
			LOGGER.error("Message" + e.getMessage());
			LOGGER.error("StackTrace" + e.getStackTrace());

			e.printStackTrace();

		}
	}

	public ArrayList<String> Httppost(String meta, String json, String table) {
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

			LOGGER.info("Exception : Application,HttpPost()");
			LOGGER.info("ErrorMsg  : " + e.getMessage());
			LOGGER.error("Stacktrace : " + e.getStackTrace());
			e.printStackTrace();

		}
		return updatedQry;
	}

	private ArrayList<String> buildbatchQuery(String tableName, String tableData, String metaData) {

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

						LOGGER.info("Exception : Application,buildbatchQuery(),Forloop metaData For Date time chack");
						LOGGER.info("msg :" + e.getMessage());
						LOGGER.error("stacktrace :" + e.getStackTrace());

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
