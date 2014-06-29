package com.clufsolutions.seniatwithholdings;

import com.clufsolutions.seniatwithholdings.config.RestExporterConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Eduardo on 14-06-2014.
 */
public class RestExporterWebInitializer implements WebApplicationInitializer {

    @Override public void onStartup(ServletContext servletContext) throws ServletException {

        // Bootstrap repositories in root application context
        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(MainConfig.class); // Include JPA entities, Repositories
        servletContext.addListener(new ContextLoaderListener(rootCtx));

        // Enable Spring Data REST in the DispatcherServlet
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        webCtx.register(RestExporterConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webCtx);
        ServletRegistration.Dynamic reg = servletContext.addServlet("rest-exporter", dispatcherServlet);
        reg.setLoadOnStartup(1);
        reg.addMapping("/api/*");
    }
}