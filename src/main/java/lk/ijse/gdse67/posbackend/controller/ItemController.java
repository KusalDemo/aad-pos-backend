package lk.ijse.gdse67.posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse67.posbackend.bo.BoFactory;
import lk.ijse.gdse67.posbackend.bo.custom.ItemBo;
import lk.ijse.gdse67.posbackend.dto.ItemDto;
import lk.ijse.gdse67.posbackend.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@WebServlet(urlPatterns = "/items",loadOnStartup = 1)
public class ItemController extends HttpServlet {
    ItemBo itemBo= (ItemBo) BoFactory.getBoFactory().getBo(BoFactory.BoFactoryTypes.ITEM);
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Override
    public void init() throws ServletException {
        logger.info("Item Controller Initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        Writer writer = resp.getWriter();
        try {
            ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);
            boolean isItemSaved = itemBo.saveItem(itemDto);
            StandardResponse standardResponse;
            if (isItemSaved) {
                resp.setStatus(200);
                standardResponse = new StandardResponse(200, "Item saved successfully", null);
                logger.info("Item saved successfully");
            } else {
                resp.setStatus(400);
                standardResponse = new StandardResponse(400, "Item not saved", null);
                logger.info("Item not saved");
            }
            jsonb.toJson(standardResponse, writer);
        }catch (SQLIntegrityConstraintViolationException e){
            logger.error("Item not saved due to an error -> "+e);
            resp.setStatus(403);
            jsonb.toJson(new StandardResponse(403,"Item name already exists..",null),writer);
        } catch (Exception e) {
            logger.error("Item not saved due to an error -> "+e);
        }finally {
            writer.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Writer writer=resp.getWriter();Jsonb jsonb = JsonbBuilder.create()){
            String pathInfo = req.getPathInfo();
            if(pathInfo==null || pathInfo.isEmpty()){
                List<ItemDto> allItems = itemBo.getAllItems();
                logger.info("All Items retrieved successfully");
                jsonb.toJson(new StandardResponse(200,"All Items retrieved successfully",allItems),writer);
            }else{
                String id = pathInfo.substring(1);
                ItemDto searchedItem = itemBo.searchItem(id);
                logger.info("Single Item retrieved successfully");
                jsonb.toJson(new StandardResponse(200,"Single Item retrieved successfully",searchedItem),writer);
            }
        }catch (Exception e) {
            logger.error("Item not saved due to an error -> "+e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Writer writer=resp.getWriter();Jsonb jsonb = JsonbBuilder.create()){
            ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);
            String pathInfo = req.getPathInfo().substring(1);
            if(itemBo.updateItem(pathInfo,itemDto)){
                jsonb.toJson(new StandardResponse(200,"Item updated successfully",null),writer);
            }else {
                jsonb.toJson(new StandardResponse(404, "Item not updated", null), writer);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Writer writer=resp.getWriter();Jsonb jsonb = JsonbBuilder.create()){
            String pathInfo = req.getPathInfo().substring(1);
            if(itemBo.deleteItem(pathInfo)){
                jsonb.toJson(new StandardResponse(200,"Item deleted successfully",null),writer);
            }else {
                jsonb.toJson(new StandardResponse(404, "Item not deleted", null), writer);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
