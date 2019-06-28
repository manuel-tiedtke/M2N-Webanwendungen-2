package org.m2n.webapplications2.controllers.database;

import org.m2n.webapplications2.database.Database;
import org.m2n.webapplications2.exceptions.DatabaseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

@Path("/db")
public class DbExplorerController {

    @POST
    @Path("query")
    @Consumes("text/sql")
    @Produces("application/json")
    public QueryResult query(String statement) throws DatabaseException {
        Connection connection = Database.getInstance().getConnection();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(statement);
            ResultSetMetaData metaData = resultSet.getMetaData();

            Set<String> columns = new HashSet<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(metaData.getColumnName(i));
            }

            List<Map<String, Object>> resultData = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for (String column : columns) {
                    rowData.put(column, resultSet.getObject(column));
                }
                resultData.add(rowData);
            }

            QueryResult result = new QueryResult();
            result.columns = columns;
            result.data = resultData;
            return result;
        } catch (SQLException e) {
            QueryResult result = new QueryResult();
            result.columns = Collections.singleton("ERROR");
            result.data = Collections.singletonList(Collections.singletonMap("ERROR", e.getMessage()));
            return result;
        }
    }

    @POST
    @Path("update")
    @Consumes("text/sql")
    @Produces("application/json")
    public QueryResult update(String statement) throws DatabaseException {
        Connection connection = Database.getInstance().getConnection();

        try {
            int affectedRows = connection.createStatement().executeUpdate(statement);
            QueryResult result = new QueryResult();
            result.columns = Collections.singleton("RESULT");
            result.data = Collections.singletonList(Collections.singletonMap("RESULT", affectedRows));
            return result;
        } catch (SQLException e) {
            QueryResult result = new QueryResult();
            result.columns = Collections.singleton("ERROR");
            result.data = Collections.singletonList(Collections.singletonMap("ERROR", e.getMessage()));
            return result;
        }
    }

    public class QueryResult {
        Set<String> columns;
        List<Map<String, Object>> data;

        public Set<String> getColumns() {
            return columns;
        }

        public List<Map<String, Object>> getData() {
            return data;
        }
    }

}
