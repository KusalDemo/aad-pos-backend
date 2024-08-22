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
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/item/*",loadOnStartup = 3)
public class ItemController extends HttpServlet {
    ItemBo itemBo= (ItemBo) BoFactory.getBoFactory().getBo(BoFactory.BoFactoryTypes.ITEM);
    static Logger logger = (Logger) LoggerFactory.getLogger(ItemController.class);
    @Override
    public void init() throws ServletException {
        logger.info("Item Controller Initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);
            boolean isItemSaved = itemBo.saveItem(itemDto);
            if(isItemSaved){
                logger.info("Item saved successfully");
                resp.setStatus(200);
                jsonb.toJson(new StandardResponse(200, "Item saved successfully", null), writer);
            }else{
                logger.info("Item not saved");
                resp.setStatus(404);
                jsonb.toJson(new StandardResponse(404, "Item not saved", null), writer);
            }
        } catch (SQLException|ClassNotFoundException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
