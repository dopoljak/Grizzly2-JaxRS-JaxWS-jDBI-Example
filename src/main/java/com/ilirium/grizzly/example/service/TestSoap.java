package com.ilirium.grizzly.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "TestSoap")
public class TestSoap
{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestSoap.class);
    
    @WebMethod(operationName = "hello")
    public String getHello(@WebParam(name = "text")String text)
    {
        log.info(">> getHello()");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String message = "Hello, " + sdf.format(new Date());
        
        log.info("<< getHello('{}')", message);
        
        return message;
    }
}
