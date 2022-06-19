package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/customer")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        /*cast servletRequest & servletResponse for sub class*/
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        resp.addHeader("Access-Control-Allow-Origin","*"); //added header for CORS Policy issue

        resp.addHeader("Access-Control-Allow-Methods","DELETE, PUT"); //Enable delete method in preflight response

        resp.addHeader("Access-Control-Allow-Headers","Content-Type"); //Allow content type header in preflight response

        filterChain.doFilter(servletRequest,servletResponse); //request proceed to the servlet

    }

    @Override
    public void destroy() {

    }
}
