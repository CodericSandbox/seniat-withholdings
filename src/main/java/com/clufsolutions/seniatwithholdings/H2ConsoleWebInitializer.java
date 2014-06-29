package com.clufsolutions.seniatwithholdings;

import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.Registration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Eduardo on 14-06-2014.
 */
public class H2ConsoleWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        WebServlet h2Servlet = new WebServlet();
        ServletRegistration.Dynamic dynamic = container.addServlet("H2Console", h2Servlet);
        dynamic.addMapping("/h2/*");
    }

}