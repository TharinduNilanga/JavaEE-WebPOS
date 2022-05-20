package servlet;

import bo.BoFactory;
import bo.custom.ItemBO;
import dao.DAOFactory;
import dto.CustomerDTO;
import dto.ItemDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/21/2022
 */
@WebServlet(urlPatterns = "/item")
public class itemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    private final ItemBO itemBO= (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String option = req.getParameter("option");
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

            PrintWriter writer = resp.getWriter();
            switch (option){
                case "GETALL":
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    ArrayList<ItemDTO> all=null;
                    try {
                        all = itemBO.getAllItems(dataSource);
                        for (ItemDTO itemDTO : all) {
                            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                            objectBuilder.add("itemId",itemDTO.getItemId());
                            objectBuilder.add("itemName",itemDTO.getItemName());
                            objectBuilder.add("itemPrice",itemDTO.getItemPrice());
                            objectBuilder.add("itemQuantity",itemDTO.getItemQuantity());
                            arrayBuilder.add(objectBuilder.build());
                        }
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        response.add("status",200);
                        response.add("message","Done...");
                        response.add("data",arrayBuilder.build());

                        writer.print(response.build());

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "SEARCH":
                    String itemId = req.getParameter("itemId");
                    ItemDTO itemDTO = null;
                    try {
                        itemDTO = itemBO.search(dataSource, itemId);
                        if (itemDTO != null) {
                            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                            objectBuilder.add("itemId",itemDTO.getItemId());
                            objectBuilder.add("itemName",itemDTO.getItemName());
                            objectBuilder.add("itemPrice",itemDTO.getItemPrice());
                            objectBuilder.add("itemQuantity",itemDTO.getItemQuantity());


                            objectBuilder.add("status", 200);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Item searched successfully");
                            writer.print(objectBuilder.build());
                        } else {
                            objectBuilder.add("status", 400);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Item searched unsuccessful");
                            writer.print(objectBuilder.build());
                        }
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                        resp.setStatus(200);
                        objectBuilder.add("status", 500);
                        objectBuilder.add("data", throwables.getLocalizedMessage());
                        objectBuilder.add("message", "Error");
                        writer.print(objectBuilder.build());
                    }
                    break;
                case "GENERATEID":
                    try {
                        String id = itemBO.generateNewID(dataSource);
                        if (id != null) {
                            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                            objectBuilder.add("itemId", id);
                            objectBuilder.add("status", 200);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Item id generated successfully");
                            writer.print(objectBuilder.build());
                        } else {
                            objectBuilder.add("status", 400);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Item id generated successfully");
                            writer.print(objectBuilder.build());
                        }
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                        resp.setStatus(200);
                        objectBuilder.add("status", 500);
                        objectBuilder.add("data", throwables.getLocalizedMessage());
                        objectBuilder.add("message", "Error");
                        writer.print(objectBuilder.build());
                    }
                    break;
            }

            dataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemId = jsonObject.getString("itemId");
        String itemName = jsonObject.getString("itemName");
        double itemPrice = Double.parseDouble(jsonObject.getString("itemPrice"));
        int itemQuantity = Integer.parseInt(jsonObject.getString("itemQuantity"));


        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (itemBO.addItem(dataSource,new ItemDTO(itemId,itemName,itemPrice,itemQuantity))){
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item saved successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
                System.out.println(objectBuilder.build());
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item Details are not valid");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemId = jsonObject.getString("itemId");
        String itemName = jsonObject.getString("itemName");
        double itemPrice = Double.parseDouble(jsonObject.getString("itemPrice"));
        int itemQuantity = Integer.parseInt(jsonObject.getString("itemQuantity"));

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (itemBO.updateItem(dataSource,new ItemDTO(itemId,itemName,itemPrice,itemQuantity))){
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item Updated");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item Update Fails");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", throwables.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            writer.print(objectBuilder.build());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", e.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            writer.print(objectBuilder.build());
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("itemId");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (itemBO.deleteItem(dataSource,itemId)){
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item Delete");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Item Delete  Fails");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", throwables.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            writer.print(objectBuilder.build());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(200);
            objectBuilder.add("status", 500);
            objectBuilder.add("data", e.getLocalizedMessage());
            objectBuilder.add("message", "Error");
            writer.print(objectBuilder.build());
            e.printStackTrace();
        }
    }
}
