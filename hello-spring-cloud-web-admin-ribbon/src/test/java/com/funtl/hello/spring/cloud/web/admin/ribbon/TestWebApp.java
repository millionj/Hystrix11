package com.funtl.hello.spring.cloud.web.admin.ribbon;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import junit.framework.Assert;

public class TestWebApp{
	
	@Autowired
	private RestTemplate restTemplate = null;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
    }
    
    @Test
    public void testService() throws Exception {
    	String url = "http://localhost:8780/hi?message=hellospringcloud";
    	String act_res = restTemplate.getForObject(url, String.class);
    	String res = "Hi you message is: hellospringcloud port is : 8762";
    	assertEquals(res, act_res);
    }
    @Test
    public void testFallBackService() throws Exception {
    	String url = "http://localhost:8780/hi?message=hellospringcloud";
    	String act_res = restTemplate.getForObject(url, String.class);
    	String res = "Hi this is fallback service and you message is: hellospringcloud port is : 8772";
    	assertEquals(res, act_res);
    }
    @Test(expected = HttpServerErrorException.class)
    public void testNoneService() throws Exception {
    	String url = "http://localhost:8780/hi?message=hellospringcloud";
    	String act_res = restTemplate.getForObject(url, String.class);
    	String res = null;
    	assertEquals(res, act_res);
    }   
}

