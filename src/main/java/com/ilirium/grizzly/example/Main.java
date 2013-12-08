package com.ilirium.grizzly.example;

import com.ilirium.grizzly.example.filters.LogFilter;
import com.ilirium.grizzly.example.helpers.DB;
import com.ilirium.grizzly.example.helpers.GrizzlyServer;
import com.ilirium.grizzly.example.service.TestResource;
import com.ilirium.grizzly.example.service.TestSoap;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.jaxws.JaxwsHandler;
import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main extends ResourceConfig 
{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);
    private static final URI    BASE_URI = UriBuilder.fromUri("http://localhost").port(8080).build();
    private static final String SOAP_URL = "/soap";
    private static final String REST_URL = "/rest";

    public Main()
    {
        register(TestResource.class);
    }
            
    public static void main( String[] args ) throws Exception
    {
        // initialize logger ...
        com.ilirium.grizzly.example.helpers.Logger.initialize();
        
        // initialize DB
        DB.initialize();
        
        // create & init server
        HttpServer httpServer = GrizzlyServer.createHttpServer(BASE_URI, false, null, true, 10, 10);
        
        WebappContext context = new WebappContext("WebappContext", REST_URL);

        // registar JAX-RS Jersey 2
        ServletRegistration registration = context.addServlet("ServletContainer", ServletContainer.class);
        registration.addMapping("/*");
        registration.setInitParameter("javax.ws.rs.Application", Main.class.getName()); 
        registration.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

        // add filter
        FilterRegistration fr = context.addFilter("FilterTest", new LogFilter());
        fr.setAsyncSupported(true);
        fr.addMappingForUrlPatterns(null, "/*");
        context.deploy(httpServer);
        
        // add JAX-WS 
        HttpHandler httpHandler = new JaxwsHandler(new TestSoap());
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, SOAP_URL);
        
        log.info("Get WADL for REST service: {}{}{}", BASE_URI, REST_URL, "/application.wadl" );
        log.info("Get WSDL for SOAP service: {}{}{}", BASE_URI, SOAP_URL, "?WSDL" );

        System.in.read();
        httpServer.shutdownNow();
    }
}
