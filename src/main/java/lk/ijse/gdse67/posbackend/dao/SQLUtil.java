package lk.ijse.gdse67.posbackend.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    static Connection connection;

    static {
        try {
            var ctx=new InitialContext();
            DataSource pool =(DataSource) ctx.lookup("java:comp/env/jdbc/aadJavaeePos");
            connection = pool.getConnection();
        } catch (NamingException|SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T>T execute(String sql, Object...params) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i + 1, params[i]);
        }

        if(sql.startsWith("SELECT") ||sql.startsWith("select")){
            return (T) pstm.executeQuery();
        }else{
            return (T)(Boolean)(pstm.executeUpdate() > 0);
        }
    }
}
