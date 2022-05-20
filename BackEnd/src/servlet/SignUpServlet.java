package servlet;

import bo.BoFactory;
import bo.custom.SignUpBO;
import dto.CustomerDTO;
import dto.SignUpDTO;
import entity.SignUp;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @author Tharindu Nilanga
 * @created 5/21/2022
 */
@WebServlet(urlPatterns = "/signUp")
public class SignUpServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    private final SignUpBO signUpBO= (SignUpBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.SIGNUP);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        try {
            String userName = req.getParameter("userName");
            SignUpDTO signUpDTO = null;
            try {
                signUpDTO = signUpBO.search(dataSource,userName);
                if (signUpDTO != null) {
                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                    objectBuilder.add("userName",signUpDTO.getUserName());
                    objectBuilder.add("email",signUpDTO.getEmail());
                    objectBuilder.add("password",signUpDTO.getPassword());



                    objectBuilder.add("status", 200);
                    objectBuilder.add("data", "");
                    objectBuilder.add("message", "Cashier searched successfully");
                    writer.print(objectBuilder.build());
                } else {
                    objectBuilder.add("status", 400);
                    objectBuilder.add("data", "");
                    objectBuilder.add("message", "Cashier searched unsuccessful");
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
            dataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String userName = jsonObject.getString("userName");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");


        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        try {
            if (signUpBO.addCashier(dataSource,new SignUp(userName,email,password))){
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Cashier saved successfully");
                writer.print(objectBuilder.build());
                dataSource.getConnection().close();
                System.out.println(objectBuilder.build());
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Cashier Details are not valid");
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
