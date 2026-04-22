package pe.com.ibm.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * MainVaultApp
 **/
 @SpringBootApplication
 public class MainVaultApp extends SpringBootServletInitializer{

	    @Override
	    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ){
	              return application.sources( MainVaultApp.class );
	    }
	
	    public static void main( String[] argumentos ) {
	           SpringApplication.run( MainVaultApp.class, argumentos );
	    }
	    
 }
