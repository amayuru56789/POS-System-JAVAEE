import com.mysql.cj.jdbc.Driver;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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

                /*---------------------------objectBuilder for generate Response----------------------------*/
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status",200);
                response.add("message","Done");
                response.add("data",arrayBuilder.build());

                /*System.out.println(arrayBuilder.build());*/
                writer.print(response.build());

            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
