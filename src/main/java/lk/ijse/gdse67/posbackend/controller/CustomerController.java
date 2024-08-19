package lk.ijse.gdse67.posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse67.posbackend.bo.BoFactory;
import lk.ijse.gdse67.posbackend.bo.custom.CustomerBo;
import lk.ijse.gdse67.posbackend.dto.CustomerDto;
import lk.ijse.gdse67.posbackend.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer",loadOnStartup = 2)
public class CustomerController extends HttpServlet {
    Connection connection;
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    CustomerBo customerBo=(CustomerBo) BoFactory.getBoFactory().getBo(BoFactory.BoFactoryTypes.CUSTOMER);
    @Override
    public void init() {
        logger.info("Customer Controller Initialized");
        try {
            var ctx=new InitialContext();
            DataSource pool =(DataSource) ctx.lookup("java:comp/env/jdbc/aadJavaeePos");
            this.connection = pool.getConnection();
        } catch (NamingException|SQLException e) {
            logger.error("Error while connecting to database : ",e);
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
            boolean isSaved = customerBo.saveCustomer(customerDto);
            StandardResponse standardResponse;
            if(isSaved){
                response.setStatus(201);
                standardResponse = new StandardResponse(201,"Customer saved successfully",null);
            }else{
                response.setStatus(400);
                standardResponse = new StandardResponse(400,"Customer saving failed",null);
            }
            jsonb.toJson(standardResponse,writer);
            logger.info("Customer saving status : isSaved? - "+isSaved);
        }catch (Exception e) {
            logger.error("Error while saving customer : ", e);
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
