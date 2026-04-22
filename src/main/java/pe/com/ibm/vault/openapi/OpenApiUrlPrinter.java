package pe.com.ibm.vault.openapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface; 

/**
 * SwaggerUrlPrinter - Prints Swagger/OpenAPI URLs when application starts
 **/
 @Component
 public class OpenApiUrlPrinter{ 
	
	    private static final Logger logger = LoggerFactory.getLogger( OpenApiUrlPrinter.class );
	
	    @Value( "${springdoc.swagger-ui.path:/swagger-ui.html}" )
	    private String vSwaggerUiPath;
	
	    @Value( "${springdoc.api-docs.path:/api-docs}" )
	    private String vApiDocsPath;
	
	    private final WebServerApplicationContext objContext; 
	
	    public OpenApiUrlPrinter( WebServerApplicationContext objContextParam ){ 
	           this.objContext = objContextParam;
	    }
	  
	   /**
	    *  printSwaggerUrls 
	    **/
	    @EventListener( ApplicationReadyEvent.class ) 
	    public void printSwaggerUrls(){
		    	
		       int    vPort         = objContext.getWebServer().getPort(); 	
		       String vHostAddress  = getHostAddress(); 	
		       String vBaseUrl      = "http://" + vHostAddress + ":" + vPort;
		       String vLocalhostUrl = "http://localhost:" + vPort; 
		
		       logger.info( "\n" +
		                "================================================================================\n" +
		                "  Application '{}' is running!\n" +
		                "================================================================================\n" +
		                "  LOCAL URLs:\n" +
		                "  - Swagger UI:   {}{}\n" +
		                "  - OpenAPI JSON: {}{}\n" +
		                "  - OpenAPI YAML: {}{}.yaml\n" +
		                "================================================================================\n" +
		                "  EXTERNAL URLs:\n" +
		                "  - Swagger UI:   {}{}\n" +
		                "  - OpenAPI JSON: {}{}\n" +
		                "  - OpenAPI YAML: {}{}.yaml\n" +
		                "================================================================================",
		                "Vault Service API",
		                vLocalhostUrl, vSwaggerUiPath,
		                vLocalhostUrl, vApiDocsPath,
		                vLocalhostUrl, vApiDocsPath,
		                vBaseUrl,      vSwaggerUiPath,
		                vBaseUrl,      vApiDocsPath,
		                vBaseUrl,      vApiDocsPath
		       );
		
		       System.out.println( "\n╔════════════════════════════════════════════════════════════════════════════╗" );
		       System.out.println( "║                    SWAGGER/OPENAPI DOCUMENTATION URLs                      ║"   );
		       System.out.println( "╠════════════════════════════════════════════════════════════════════════════╣"   );
		       System.out.println( "║  * SWAGGER UI:   " + vLocalhostUrl + vSwaggerUiPath + "                     ║"   );
		       System.out.println( "║  * OPENAPI JSON: " + vLocalhostUrl + vApiDocsPath   + "                            ║"   );
		       System.out.println( "║  * OPENAPI YAML: " + vLocalhostUrl + vApiDocsPath + ".yaml" + "                       ║"   );
		       System.out.println( "╚════════════════════════════════════════════════════════════════════════════╝\n" );
	    }
	
	   /**
	    * getHostAddress 
	    * @return String
	    **/
	    private String getHostAddress(){
		        try{
		            return NetworkInterface.networkInterfaces()
		                   .flatMap( NetworkInterface::inetAddresses )
		                   .filter( addr -> !addr.isLoopbackAddress() )
		                   .filter( addr -> addr instanceof Inet4Address ) 
		                   .map( InetAddress::getHostAddress)
		                   .findFirst()
		                   .orElse( "localhost" );
		        } 
		        catch( Exception e ){
		               return "localhost"; 
		        }
	    }
    
 }
 