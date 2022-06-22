package dbcp_listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        /*Create Database Connection Pool*/
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/Pos_System");
        bds.setUsername("root");
        bds.setPassword("1234");
        bds.setMaxTotal(5); //size of connections in our Application
        bds.setInitialSize(5); //Initial connections

        //Common place for any servlet
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("bds",bds);// store pool in servletContext

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        ServletContext servletContext = servletContextEvent.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
        try {
            bds.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
