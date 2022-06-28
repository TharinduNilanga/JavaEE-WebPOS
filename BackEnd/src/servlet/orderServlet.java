package servlet;

import bo.BoFactory;
import bo.custom.OrderBO;
import bo.custom.OrderDetailsBO;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Order;
import entity.OrderDetails;
import entity.SignUp;

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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/21/2022
 */
@WebServlet(urlPatterns = "/order")
public class orderServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    private final OrderBO orderBO= (OrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    private final OrderDetailsBO orderDetailsBO= (OrderDetailsBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDERDETAILS);

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

                    ArrayList<OrderDTO> all=null;
                    try {
                        all = orderBO.getAllOrder(dataSource);
                        for (OrderDTO orderDTO : all) {

                            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                            objectBuilder.add("oId",orderDTO.getoId());
                            objectBuilder.add("cusId",orderDTO.getCusId());
                            objectBuilder.add("discount",orderDTO.getDiscount());
                            objectBuilder.add("totalPrice",orderDTO.getTotalPrice());
                            objectBuilder.add("date", String.valueOf(orderDTO.getDate()));
                            objectBuilder.add("time", String.valueOf(orderDTO.getTime()));
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
                case "GENERATEID":
                    try {
                        String id = orderBO.generateNewOrderId(dataSource);
                        System.out.println(id);
                        if (id != null) {
                            System.out.println(id);
                            resp.setStatus(HttpServletResponse.SC_OK);
                            objectBuilder.add("id", id);
                            objectBuilder.add("status", 200);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Order id generated successfully");
                            writer.print(objectBuilder.build());
                        } else {
                            objectBuilder.add("status", 400);
                            objectBuilder.add("data", "");
                            objectBuilder.add("message", "Order id generated successfully");
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
        String oId = jsonObject.getString("oId");
        String cusId = jsonObject.getString("cusId");
        int discount = Integer.parseInt(jsonObject.getString("discount"));
        double totalPrice = Double.parseDouble(jsonObject.getString("totalPrice"));
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        LocalTime time = LocalTime.parse(jsonObject.getString("time"));
        //////
        JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");

        ArrayList<OrderDetailsDTO> getOrderDetails = new ArrayList<>();


        for (int i = 0; i < orderDetails.size(); i++) {
            getOrderDetails.add(new OrderDetailsDTO(
                    orderDetails.getJsonObject(i).getString("oId"),
                    orderDetails.getJsonObject(i).getString("cusId"),
                    orderDetails.getJsonObject(i).getString("itemId"),
                    Integer.parseInt(orderDetails.getJsonObject(i).getString("discount")),
                    Integer.parseInt(orderDetails.getJsonObject(i).getString("quantity")),
                    Double.parseDouble(orderDetails.getJsonObject(i).getString("totalPrice"))
                    ));
        }

        System.out.println(oId);
        System.out.println(cusId);
        System.out.println(discount);
        System.out.println(totalPrice);
        System.out.println(date);
        System.out.println(time);
        //System.out.println(oId);

        resp.setContentType("application/json");

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (orderBO.purchaseOrder(dataSource,new OrderDTO(oId,cusId,discount,totalPrice,date,time,getOrderDetails))){
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Order saved successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
                System.out.println(objectBuilder.build());
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Order Details are not valid");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
