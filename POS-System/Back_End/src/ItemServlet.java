import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//Exact mapping for bind itemServlet
@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Mime types
        resp.setContentType("application/json");

        /*--------------------Writer for send a Response-------------------*/
        PrintWriter writer = resp.getWriter();

        /*--------------------Get connection from Database Connection Pool-------------------*/
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        //arrayBuilder
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        try {

            Connection con = bds.getConnection();
            PreparedStatement pstm = con.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {

                /*--------------------------objectBuilder for generate json object---------------------------*/
                JsonObjectBuilder objBuilder = Json.createObjectBuilder();
                objBuilder.add("code",rst.getString(1));
                objBuilder.add("name",rst.getString(2));
                objBuilder.add("price",rst.getDouble(3));
                objBuilder.add("qtyOnHand",rst.getInt(4));

                arrayBuilder.add(objBuilder.build()); //add to the array of json

            }
            /*---------------------------objectBuilder for generate Response----------------------------*/
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",200);
            response.add("message","Done");
            response.add("data",arrayBuilder.build());

            /*System.out.println(arrayBuilder.build());*/
            writer.print(response.build());


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        /*--------------------Writer for send a Response-------------------*/
        PrintWriter writer = resp.getWriter();

        /*--------------------Get connection from Database Connection Pool-------------------*/
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {

            Connection con = bds.getConnection();

            PreparedStatement pstm = con.prepareStatement("insert into Item values (?,?,?,?)");
            pstm.setObject(1,req.getParameter("itemCode"));
            pstm.setObject(2,req.getParameter("itemName"));
            pstm.setObject(3,req.getParameter("itemPrice"));
            pstm.setObject(4,req.getParameter("qtyOnHand"));

            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED); //201
                response.add("status",200);
                response.add("message","Item Added successfully...");
                response.add("data","");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_OK); //200

            JsonObjectBuilder response = Json.createObjectBuilder();
            /*resp.setStatus(500);*/
            response.add("status",400);
            response.add("message","Error...");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());

            /*resp.setStatus(HttpServletResponse.SC_OK);*/
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        //get itemCode Using getParameter Method
        String itemCode = req.getParameter("ItemCode");

        /*--------------------Writer for send a Response-------------------*/
        PrintWriter writer = resp.getWriter();

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {


            Connection con = bds.getConnection();
            PreparedStatement pstm = con.prepareStatement("delete from Item where itemCode=?");
            pstm.setObject(1,itemCode);


            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("code",200);
                response.add("data","");
                response.add("message","Item Successfully Deleted");
                writer.print(response.build());
            }else{
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("code",400);
                response.add("data","Wrong CODE inserted");
                response.add("message"," ");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(200);

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("code",500);
            response.add("message","Error");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        /*get Item information from json Request Using JsonReader */
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String code = jsonObject.getString("code");
        String name = jsonObject.getString("name");
        Double price = Double.valueOf(jsonObject.getString("price"));
        int qtyOnHand = Integer.parseInt(jsonObject.getString("qtyOnHand"));

        try {

            Connection con = bds.getConnection();
            PreparedStatement pstm = con.prepareStatement("update Item set itemName=?,itemPrice=?,qtyOnHand=? where itemCode=?");
            pstm.setObject(1,code);
            pstm.setObject(2,name);
            pstm.setObject(3,price);
            pstm.setObject(4,qtyOnHand);

            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status",200);
                response.add("message","Item Updated");
                response.add("data","");
                writer.print(response.build());
            }else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status",400);
                response.add("message","Item Update failed...");
                response.add("data","");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",500);
            response.add("message","Error...");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());
        }

    }

}
