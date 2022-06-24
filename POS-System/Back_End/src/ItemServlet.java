import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Exact mapping for bind itemServlet
@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

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

}
