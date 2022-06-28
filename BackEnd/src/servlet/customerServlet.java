package servlet;

import bo.BoFactory;
import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
@WebServlet(urlPatterns = "/customer")

public class customerServlet extends HttpServlet {


    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    private final CustomerBO customerBO= (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
        String option = req.getParameter("option");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        resp.setContentType("application/json");


        PrintWriter writer = resp.getWriter();
        switch (option){
            case "GETALL":
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                ArrayList<CustomerDTO> all=null;
                try {
                   all = customerBO.getAllCustomer(dataSource);
                    for (CustomerDTO customer : all) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("id",customer.getCusId());
                        objectBuilder.add("name",customer.getCusName());
                        objectBuilder.add("address",customer.getCusAddress());
                        objectBuilder.add("contact",customer.getCusContact());
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
                String customerId = req.getParameter("cusId");
                CustomerDTO customerDTO = null;
                System.out.println(customerId);
                try {
                    customerDTO = customerBO.search(dataSource,customerId);
                    System.out.println(customerId);
                    if (customerDTO != null) {
                        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                        objectBuilder.add("cusId",customerDTO.getCusId());
                        objectBuilder.add("cusName",customerDTO.getCusName());
                        objectBuilder.add("cusAddress",customerDTO.getCusAddress());
                        objectBuilder.add("cusContact",customerDTO.getCusContact());


                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer searched successfully");
                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer searched unsuccessful");
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
                    String id = customerBO.generateNewID(dataSource);
                    System.out.println(id);
                    if (id != null) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        objectBuilder.add("cusId", id);
                        objectBuilder.add("status", 200);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer id generated successfully");

                        writer.print(objectBuilder.build());
                    } else {
                        objectBuilder.add("status", 400);
                        objectBuilder.add("data", "");
                        objectBuilder.add("message", "Customer id generated successfully");
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
        String cusId = jsonObject.getString("id");
        String cusName = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        String cusContact = jsonObject.getString("contact");
        resp.setContentType("application/json");
        System.out.println(cusId+cusName+address+cusContact);
      /*  String cusId =req.getParameter("txtCustomerId");
        String cusName =req.getParameter("txtCustomerName");
        String address =  req.getParameter("txtCustomerAddress");
        String cusContact =req.getParameter("txtCustomerContact");
        System.out.println(cusId+cusName+address+cusContact);*/
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        try {
            if (customerBO.addCustomer(dataSource,new CustomerDTO(cusId,cusName,address,cusContact))){
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer saved successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
               // System.out.println(objectBuilder.build());
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer Details are not valid");
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("cusId");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            if (customerBO.deleteCustomer(dataSource,cusId)){
                System.out.println("ok");
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer Delete");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer Delete  Fails");
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String cusId = jsonObject.getString("id");
        String cusName = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        String cusContact = jsonObject.getString("contact");
        System.out.println(cusId+cusName+address+cusContact);
        resp.setContentType("application/json");

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (customerBO.updateCustomer(dataSource,new CustomerDTO(cusId,cusName,address,cusContact))){
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer Updated");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Customer Update Fails");
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
}
