package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {

    public CORSFilter(){
        System.out.println("created CORS ");
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest)servletRequest;
        HttpServletResponse resp=(HttpServletResponse)servletResponse;

         resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Access-Control-Allow-Methods","DELETE,PUT");
        resp.addHeader("Access-Control-Allow-Headers","Content-Type");
        
        filterChain.doFilter(servletRequest,servletResponse);


    }

    @Override
    public void destroy() {

    }
}
/*
    Access to XMLHttpRequest at 'http://localhost:8080/Filters_Web_exploded/customer?option=GENERATEID' from origin 'http://localhost:63342' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.*/