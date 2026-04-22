package pe.com.ibm.vault.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * OpenApiConfig - Configuración para documentación Swagger/OpenAPI
 **/
 @Configuration
 public class OpenApiConfig{
	
	    @Value( "${server.port:8080}" )
	    private String serverPort;
	
	    @Bean
	    public OpenAPI customOpenAPI() {
		       Server objServer = new Server();
		       objServer.setUrl( "http://localhost:" + serverPort );
		       objServer.setDescription( "Servidor de Desarrollo" );
		
		       Contact objContacto = new Contact();
		       objContacto.setName(  "Equipo IBM Vault Service" );
		       objContacto.setEmail( "support@ibm.com" );
		
		       License objLicensia = new License()
		               .name( "Apache 2.0" )
		               .url(  "https://www.apache.org/licenses/LICENSE-2.0.html" );
		
		       Info objInfo = new Info()
		               .title( "API de Servicio Vault" )
		               .version( "1.0.0" )
		               .description( "API para gestionar operaciones de HASHICORP VAULT incluyendo: Secretos KEY-VALUE, Credenciales de BASE DE DATOS y Encriptación TRANSIT" )
		               .contact( objContacto )
		               .license( objLicensia );
		
		       return new OpenAPI()
		               .info( objInfo )
		               .servers( List.of( objServer ) );
	    }
	    
 }
 
