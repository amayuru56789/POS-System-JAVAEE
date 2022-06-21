import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.jdbc.JdbcConnection;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//Exact mapping for bind customerServlet
@WebServlet(name = "AVC",urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*System.out.println("Hello KITT");*/

        /*---------------------------------load driver & access DB-------------------------------------------*/
        try {
            //Mime types
            resp.setContentType("application/json");

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pos System", "root", "1234");
            PreparedStatement pstm = con.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();

            //arrayBuilder
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            /*--------------------Writer for send a Response-------------------*/
            PrintWriter writer = resp.getWriter();

            while (rst.next()) {
                /*String customerID = rst.getString(1);
                String customerName = rst.getString(2);
                String customerAddress = rst.getString(3);
                String customerSalary = rst.getString(4);

                System.out.println(" id "+customerID+" name "+customerName+" address "+customerAddress+" salary "+customerSalary);
*/

                /*--------------------------objectBuilder for generate json object---------------------------*/
                JsonObjectBuilder objBuilder = Json.createObjectBuilder();
                objBuilder.add("id",rst.getString(1));
                objBuilder.add("name",rst.getString(2));
                objBuilder.add("address",rst.getString(3));
                objBuilder.add("salary",rst.getDouble(4));

                arrayBuilder.add(objBuilder.build()); //add to the array of json



            }
            /*---------------------------objectBuilder for generate Response----------------------------*/
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",200);
            response.add("message","Done");
            response.add("data",arrayBuilder.build());

            /*System.out.println(arrayBuilder.build());*/
            writer.print(response.build());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*System.out.println("KITT");*/

        resp.setContentType("application/json");

        /*--------------------Writer for send a Response-------------------*/
        PrintWriter writer = resp.getWriter();

        //gather customer information from textFields Using getParameter Method
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        Double customerSalary = Double.valueOf(req.getParameter("customerSalary"));
        /*System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);*/

        //database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pos System", "root", "1234");
            PreparedStatement pstm = con.prepareStatement("insert into Customer values (?,?,?,?)");
            pstm.setObject(1,customerID);
            pstm.setObject(2,customerName);
            pstm.setObject(3,customerAddress);
            pstm.setObject(4,customerSalary);

            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED); //201
                response.add("status",200);
                response.add("message","Customer Added successfully...");
                response.add("data","");
                writer.print(response.build());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_OK); //200

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",400);
            response.add("message","Error...");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());

            /*resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500*/
        } catch (SQLException e) {
            e.printStackTrace();
            JsonObjectBuilder response = Json.createObjectBuilder();
            /*resp.setStatus(500);*/
            response.add("status",400);
            response.add("message","Error...");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);
        }

        /*PrintWriter writer = resp.getWriter();
        writer.print("Hello KITT");*/

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*System.out.println("Hello KITT");*/

        resp.setContentType("application/json");

        //get customerID Using getParameter Method
        String customerID = req.getParameter("CustID");
        System.out.println(customerID);

        /*--------------------Writer for send a Response-------------------*/
        PrintWriter writer = resp.getWriter();
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pos System", "root", "1234");
            PreparedStatement pstm = con.prepareStatement("delete from Customer where customerID=?");
            pstm.setObject(1,customerID);

            /*boolean b = pstm.executeUpdate() > 0;*/

            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("code",200);
                response.add("data","");
                response.add("message","Customer Successfully Deleted");
                writer.print(response.build());
            }else{
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("code",400);
                response.add("data","Wrong ID inserted");
                response.add("message","Customer ");
                writer.print(response.build());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(200);

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("code",500);
            response.add("data",e.getLocalizedMessage());
            response.add("message","Error");
            writer.print(response.build());

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
        /*System.out.println("Hello KITT");*/

        PrintWriter writer = resp.getWriter();

        /*//gather customer information from textFields Using getParameter Method
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        Double customerSalary = Double.valueOf(req.getParameter("customerSalary"));
        *//*System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);*/

        /*get Customer information from json Request Using JsonReader */
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        Double salary = Double.valueOf(jsonObject.getString("salary"));
        System.out.println(id+" "+name+" "+address+" "+salary);

        /*try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/'Pos System", "root", "1234");
            PreparedStatement pstm = con.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
            pstm.setObject(1,id);
            pstm.setObject(2,name);
            pstm.setObject(3,address);
            pstm.setObject(4,salary);
            boolean b = pstm.executeUpdate() > 0;
            if (b){
                writer.write("Customer Updated");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


    }

}
