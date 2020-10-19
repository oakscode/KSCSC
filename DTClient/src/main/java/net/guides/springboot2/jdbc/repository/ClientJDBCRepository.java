package net.guides.springboot2.jdbc.repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.guides.springboot2.jdbc.model.DTServices;
import net.guides.springboot2.jdbc.model.SQLColumn;

@Repository
public class ClientJDBCRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private final ObjectMapper mapper = new ObjectMapper();
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	public String qry;


	@SuppressWarnings("unused")
	public List<Object> GenerateQry(String json) {

		Gson gson = new Gson();
		List<Object> QryList = new ArrayList<>();
		Type ListType = new TypeToken<ArrayList<DTServices>>() {
		}.getType();

		ArrayList<DTServices> processArray = gson.fromJson(json, ListType);

		for (DTServices p : processArray) {

			StringBuilder sb = new StringBuilder();
			sb.append(p.getAction());
			sb.append(" * ");
			sb.append(" FROM ");
			sb.append(p.getTable());
			sb.append(" WHERE ");
			sb.append(" ws_status = '");
			sb.append(p.getWs_status() + "' limit 10 "); 
			QryList.add(sb + "," + p.getTable());

		}
		return QryList;
	}

	public List<Map<String, Object>> Fetchdata(String qry) {

		String[] params = qry.split(",");

		return jdbcTemplate.queryForList(params[0]);
	}

	public ArrayList<SQLColumn> FetchMetadata(String qry) {


		String[] params = qry.split(",");

		return Metadataset(params[1], params[0]);
	}

	public ArrayList<SQLColumn> Metadataset(String table, String sql) {

		ArrayList<SQLColumn> obj = new ArrayList<SQLColumn>();

		obj = jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<SQLColumn>>() {

			@Override
			public ArrayList<SQLColumn> extractData(ResultSet rs) throws SQLException, DataAccessException {

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				ArrayList<SQLColumn> al = new ArrayList<SQLColumn>();

				for (int i = 1; i <= columnCount; i++) {
					SQLColumn column = new SQLColumn();
					column.setName(rsmd.getColumnName(i));
					column.setAutoIncrement(rsmd.isAutoIncrement(i));
					column.setType(rsmd.getColumnTypeName(i));
					column.setTypeCode(rsmd.getColumnType(i));
					column.setTypeName(table);
					al.add(column);
				}

				return al;
			}
		});
		return obj;
	}
	public int insertUpdatedRows(String qry) {
		int i = 0;
		try {
			 i = jdbcTemplate.update(qry);
		} catch (Exception e) {
			logger.info("Exception : ClientJDBCRepository,insertUpdatedRows(String qry)");
			logger.info("msg : "+e.getMessage());
			logger.error("StackTrace : "+e.getStackTrace());
			
			System.out.println("Exception : ClientJDBCRepository,insertUpdatedRows(String qry)");
			System.out.println(e.getStackTrace());
		}
		
		
		return i;
	}

}
