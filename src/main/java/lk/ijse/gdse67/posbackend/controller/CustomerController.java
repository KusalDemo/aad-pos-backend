package lk.ijse.gdse67.posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse67.posbackend.dto.CustomerDto;
import lk.ijse.gdse67.posbackend.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {
    Connection connection;
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Override
    public void init() {
        logger.info("Customer Controller Initialized");
        try {
            var ctx=new InitialContext();
            DataSource pool =(DataSource) ctx.lookup("java:comp/env/jdbc/aadJavaeePos");
            this.connection = pool.getConnection();
        } catch (NamingException|SQLException e) {
            logger.error("Error while connecting to database : ",e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){

    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try(Writer writer=response.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto customerDto = jsonb.fromJson(request.getReader(), CustomerDto.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response){

    }
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response){

    }
}
