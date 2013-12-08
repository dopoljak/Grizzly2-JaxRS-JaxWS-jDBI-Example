package com.ilirium.grizzly.example.service;

import com.ilirium.grizzly.example.db.dao.UserDAO;
import com.ilirium.grizzly.example.db.entity.User;
import com.ilirium.grizzly.example.helpers.DB;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class TestResource
{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestResource.class);
    
    @GET @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() throws InterruptedException 
    {
        log.info(">> getHello()");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String message = "HelloWorld, " + sdf.format(new Date());

        log.info("<< getHello('{}')", message);
        
        return message;
    }
    
    @POST
    @Path("/hello")
    public String postHello(String hello) throws InterruptedException
    {
        log.info(">> postHello(hello = '{}')", hello);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String message = "HelloWorld, " + sdf.format(new Date());

        // simulate long running test (e.g. DB operation ...)
        Random r = new Random();
        Thread.sleep(r.nextInt(300));
         
        log.info("<< postHello('{}')", message);
        
        return message;
    }
    
    @GET @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUsers(@PathParam("id") int id) throws InterruptedException 
    {
        log.info(">> getUsers(id = '{}')", id);

        UserDAO dao = DB.getDBI().open(UserDAO.class);        

        User user = dao.findUserById(id);
        
        dao.close();
        
        log.info("<< getUsers(user = '{}')", user);
        
        return user;
    }
}
