# Hystrix component #

When a service cannot response, Hystrix (Circuit breaker) can connect another fallback service to solve this problem. In this example, the client service needs to connect the regular service. But when the regular service does not response, the client service can connect to a fallback service.  

##  Installing  ##
Here are the steps to use this Hystrix component.


**Step 1.** create and run a eureka service

  1.1 create a springBoot application called Hello-spring-cloud-eureka 

  1.2 pom.xml

      <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    	<parent>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-parent</artifactId>
    		<version>2.1.9.RELEASE</version>
    		<relativePath/> <!-- lookup parent from repository -->
    	</parent>
    	<groupId>com.ibm</groupId>
    	<artifactId>eureka</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    	<name>eureka</name>
    	<description>Demo project for Spring Boot</description>
    
    	<properties>
    		<java.version>1.8</java.version>
    		<spring-cloud.version>Greenwich.SR3</spring-cloud.version>
    	</properties>
    
    	<dependencies>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    		</dependency>
    
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-test</artifactId>
    			<scope>test</scope>
    		</dependency>
    	</dependencies>
    
    	<dependencyManagement>
    		<dependencies>
    			<dependency>
    				<groupId>org.springframework.cloud</groupId>
    				<artifactId>spring-cloud-dependencies</artifactId>
    				<version>${spring-cloud.version}</version>
    				<type>pom</type>
    				<scope>import</scope>
    			</dependency>
    		</dependencies>
    	</dependencyManagement>
    
    	<build>
    		<plugins>
    			<plugin>
    				<groupId>org.springframework.boot</groupId>
    				<artifactId>spring-boot-maven-plugin</artifactId>
    				<configuration>
    					<mainClass>com.ibm.msdiscovery.MsDiscoveryApplication</mainClass>
    				</configuration>
    			</plugin>
    		</plugins>
    	</build>
    
    </project>

  1.3 create application.yml and bootstrap.yml under resources folder

  application.yml
    
      
    eureka:
    
      client:
    
        register-with-eureka: false
    
        fetch-registry: false
 
  bootstrap.yml
  
  
    server:
    
      port: ${port:8761}
    
    spring:
    
      application:
    
      name: hello-spring-cloud-eureka
    
  1.4 add @EnableEurekaServer above you entry class like this

    @EnableEurekaServer
    @SpringBootApplication
    public class EurekaApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(EurekaApplication.class, args);
    	}
    }

  1.5 run eureka application


**Step 2.**  **Create and run a service 1.**

  Hello-spring-cloud-service-admin is Service 1 which can be registered in Eureka. So under the normal condition, client service will connect to service 1. And the default port is 8762.

  2.1 create a springBoot application called Hello-spring-cloud-service-admin

  2.2 pom.xml
  
      <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    	<parent>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-parent</artifactId>
    		<version>2.1.9.RELEASE</version>
    		<relativePath/> <!-- lookup parent from repository -->
    	</parent>
    	<groupId>com.ibm</groupId>
    	<artifactId>service-admin</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    	<name>service-admin</name>
    	<description>Demo project for Spring Boot</description>
    
    	<properties>
    		<java.version>1.8</java.version>
    		<spring-cloud.version>Greenwich.SR3</spring-cloud.version>
    	</properties>
    
    	<dependencies>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-config-server</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-test</artifactId>
    			<scope>test</scope>
    		</dependency>
    	</dependencies>
    
    	<dependencyManagement>
    		<dependencies>
    			<dependency>
    				<groupId>org.springframework.cloud</groupId>
    				<artifactId>spring-cloud-dependencies</artifactId>
    				<version>${spring-cloud.version}</version>
    				<type>pom</type>
    				<scope>import</scope>
    			</dependency>
    		</dependencies>
    	</dependencyManagement>
    
    	<build>
    		<plugins>
    			<plugin>
    				<groupId>org.springframework.boot</groupId>
    				<artifactId>spring-boot-maven-plugin</artifactId>
    			</plugin>
    		</plugins>
    	</build>
    
    </project>

  2.3 create bootstrap.yml under resources folder

    server:
    
      port: 8762
    
    spring:
    
      application:
    
    name: hello-spring-cloud-service-admin
    
    eureka:
       client:
        serviceUrl:
          defaultZone: http://localhost:8761/eureka

2.4 add @EnableEurekaServer above you entry class like this above you entry class like this
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.cloud.config.server.EnableConfigServer;
    import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
    
    
    @EnableEurekaClient
    @SpringBootApplication
    public class ServiceAdminApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(ServiceAdminApplication.class, args);
    	}
    }
2.5 create controller package under the parent package and create a controller class called *AdminController*
    
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    @RestController
    public class AdminController {
    
    	@Value("${server.port}")
    	private String port;
    	
    	@GetMapping(value="hi")
    	public String sayHi(String message) {
    		return String.format("Hi you message is:%s port is : %s", message, port);
    	}
    }

**Step 3.**  **Create and run a fallback service**

  Hello-spring-cloud-service-fallback is fallback Service which can be registered in Eureka and the default port is 8772. So when service 1 does not work the client service can connect to fallback service.

3.1 create a springBoot application called Hello-spring-cloud-fallback-service

3.2 pom.xml

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    	<parent>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-parent</artifactId>
    		<version>2.1.9.RELEASE</version>
    		<relativePath/> <!-- lookup parent from repository -->
    	</parent>
    	<groupId>com.ibm</groupId>
    	<artifactId>service-fallback</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    	<name>service-fallback</name>
    	<description>Demo project for Spring Boot</description>
    
    	<properties>	
    		<java.version>1.8</java.version>
    		<spring-cloud.version>Greenwich.SR3</spring-cloud.version>
    	</properties>
    
    	<dependencies>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-config-server</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-test</artifactId>
    			<scope>test</scope>
    		</dependency>
    	</dependencies>
    
    	<dependencyManagement>
    		<dependencies>
    			<dependency>
    				<groupId>org.springframework.cloud</groupId>
    				<artifactId>spring-cloud-dependencies</artifactId>
    				<version>${spring-cloud.version}</version>
    				<type>pom</type>
    				<scope>import</scope>
    			</dependency>
    		</dependencies>
    	</dependencyManagement>
    
    	<build>
    		<plugins>
    			<plugin>
    				<groupId>org.springframework.boot</groupId>
    				<artifactId>spring-boot-maven-plugin</artifactId>
    			</plugin>
    		</plugins>
    	</build>
    
    </project>

3.3 create application.yml under resources folder

    server:
    
      port: 8772
    
    spring:
    
      application:
    
    name: hello-spring-cloud-service-fallback
    
    eureka:
       client:
      serviceUrl:
     defaultZone: http://localhost:8761/eureka

3.4 add *@EnableEurekaClient* above you entry class like this above you entry class like this
    
    @EnableEurekaClient
    @SpringBootApplication
    public class ServiceFallbackApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(ServiceFallbackApplication.class, args);
    	}
    
    }

3.5 create controller package under the parent package and create a controller class called *AdminController*
    
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    @RestController
    public class AdminController {
    
    	@Value("${server.port}")
    	private String port;
    	
    	@GetMapping(value="hi")
    	public String sayHi(String message) {
    		return String.format("Hi you message is:%s port is : %s", message, port);
    	}
    }


**Step 4.**  **Create and run a client service**

  Hello-spring-cloud-web-admin-ribbon is Client Service which is used to discover the two services above and the default port is 8780.

4.1 create a springBoot application called Hello-spring-cloud-web-admin-ribbon

4.2 pom.xml
    
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    	<parent>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-parent</artifactId>
    		<version>2.1.9.RELEASE</version>
    		<relativePath/> <!-- lookup parent from repository -->
    	</parent>
    	<groupId>com.ibm</groupId>
    	<artifactId>web-admin-ribbon</artifactId>
    	<version>0.0.1-SNAPSHOT</version>
    	<name>web-admin-ribbon</name>
    	<description>Demo project for Spring Boot</description>
    
    	<properties>
    		<java.version>1.8</java.version>
    		<spring-cloud.version>Greenwich.SR3</spring-cloud.version>
    	</properties>
    
    	<dependencies>
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-actuator</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    		</dependency>
    
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-test</artifactId>
    			<scope>test</scope>
    		</dependency>
    		
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    		</dependency>
    	</dependencies>
    
    	<dependencyManagement>
    		<dependencies>
    			<dependency>
    				<groupId>org.springframework.cloud</groupId>
    				<artifactId>spring-cloud-dependencies</artifactId>
    				<version>${spring-cloud.version}</version>
    				<type>pom</type>
    				<scope>import</scope>
    			</dependency>
    		</dependencies>
    	</dependencyManagement>
    
    	<build>
    		<plugins>
    			<plugin>
    				<groupId>org.springframework.boot</groupId>
    				<artifactId>spring-boot-maven-plugin</artifactId>
    			</plugin>
    		</plugins>
    	</build>
    
    </project>
   
4.3 create application.yml under resources folder

    server:
    
      port: 8780
    
    spring:
    
      application:
    
    name: hello-spring-cloud-web-admin-ribbon
    
    eureka:
       client:
      serviceUrl:
     defaultZone: http://localhost:8761/eureka
 
4.4 add *@EnableHystrix* and *@EnableDiscoveryClient* above you entry class like this above you entry class like this

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
    import org.springframework.cloud.netflix.hystrix.EnableHystrix;
    
    @EnableHystrix
    @EnableDiscoveryClient
    @SpringBootApplication
    public class WebAdminRibbonApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(WebAdminRibbonApplication.class, args);
    	}  
    }
    
4.5 create configuration package under the parent package and create a controller class called *RestTemplateConfiguration*

    import org.springframework.cloud.client.loadbalancer.LoadBalanced;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.client.RestTemplate;
    
    @Configuration
    public class RestTemplateConfiguration {
    	
    	@Bean
    	@LoadBalanced
    	public RestTemplate restTemplate() {
    		return new RestTemplate();
    	}
    }

4.6 create service package under the parent package and create a controller class called *AdminService*
    
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;
    
    import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
    
    
    
    @Service
    public class AdminService {
    	@Autowired
    	private RestTemplate restTemplate;
    	
    	@HystrixCommand(fallbackMethod = "hiError")
    	public String sayHi(String message) {
    		return restTemplate.getForObject("http://hello-spring-cloud-service-admin/hi?message= " + message, String.class);
    	}
    	
    	
    	public String hiError(String message) {
    		return restTemplate.getForObject("http://hello-spring-cloud-service-fallback/hi?message= " + message, String.class);
    	}
    }

4.7 create controller package under the parent package and create a controller class called *AdminController*
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    import com.funtl.hello.spring.cloud.web.admin.ribbon.service.AdminService;
    
    @RestController
    public class AdminController {
    	@Autowired
    	private AdminService adminService;
    	
    	@GetMapping("/")
    	public String home() {
    		return "home";
    	}
    	
    	@GetMapping(value="hi")
    	public String sayHi(String message) {
    		return adminService.sayHi(message);
    	}
    }


##  Running the tests  ##

Test cases in the TestWebApp class of the client service.

    
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


1.testService is used to test if client service can connected to service 1.

2.testFallBackService is used to test if client service connected to fallback Service.

3.testNoneService is used to test if none of the two services is connected

**notes:**

1. if you want to run test case 2, you need to shut down  Hello-spring-cloud-service-admin(service 1) manually.

2. if you want to run test case 3, you need to shut down both Hello-spring-cloud-service-admin(service 1) and Hello-spring-cloud-service-fallback(service 2) manually.


