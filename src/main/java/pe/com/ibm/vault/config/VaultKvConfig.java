package pe.com.ibm.vault.config;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.VaultToken;
import org.springframework.web.client.RestTemplate;
import pe.com.ibm.vault.config.properties.VaultCoreProperties;

/**
 * VaultKvConfig - Configuración de Vault para el Engine KV. 
 * Utiliza VaultCoreProperties para obtener las propiedades de conexión. 
 **/
 @SuppressWarnings( "null" )
 @Configuration
 public class VaultKvConfig extends AbstractVaultConfiguration{ 
	
	    private VaultCoreProperties objVaultCoreProperties; 
	    
	   /**
	    * Constructor con inyección de dependencias. 
	    * @param vaultCoreProperties - Propiedades comunes de VAULT. 
	    **/
	    public VaultKvConfig( VaultCoreProperties objVaultCorePropertiesParam ){ 
	           this.objVaultCoreProperties = objVaultCorePropertiesParam; 
	    }
	    
		@Override
	    public @NonNull VaultEndpoint vaultEndpoint(){ 
	           return VaultEndpoint.from( URI.create( objVaultCoreProperties.getUri() ) ); 
	    }
       
	   /**
	    * clientAuthentication
	    * @param ClientAuthentication 
	    **/
		@SuppressWarnings( "rawtypes" )
		@Override
	    public ClientAuthentication clientAuthentication(){  
		       return () -> {
		            RestTemplate        objRestTemplate = new RestTemplate();
		            Map<String, String> objCredential   = new HashMap<>();
		            
		            objCredential.put( "password", objVaultCoreProperties.getPassword() );
		
		            String vURLLogin = ( objVaultCoreProperties.getUri()      + "/v1/auth/" +
		                                 objVaultCoreProperties.getAuthPath() + "/login/"   + 
		                                 objVaultCoreProperties.getUsername() );
				            
					ResponseEntity<Map> objResponse = objRestTemplate.postForEntity( vURLLogin, objCredential, Map.class );  
		            Map<?, ?>           objBody     = objResponse.getBody();
		            
		            if( objBody == null ){
		                throw new IllegalStateException( "VAULT respondió vacío al AUTENTICARSE" ); 
		            }
		
		            Object objAutentica = objBody.get( "auth" );
		            if( objAutentica == null || !(objAutentica instanceof Map) ){
		                throw new IllegalStateException( "VAULT no devolvió 'auth' en el LOGIN: " + objBody );
		            }
		
		            Map<?, ?> objMapAutentica = (Map<?, ?>)objAutentica; 
		
		            Object objClientToken = objMapAutentica.get( "client_token" ); 
		            if( objClientToken == null ){
		                throw new IllegalStateException( "VAULT no devolvió client_token: " + objBody ); 
		            }
		             
		            VaultToken objVaultToken = VaultToken.of( objClientToken + "" ); 
		            
		            return objVaultToken; 
		        };
	    }
 }

