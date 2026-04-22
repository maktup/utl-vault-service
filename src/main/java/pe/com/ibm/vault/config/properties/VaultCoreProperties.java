package pe.com.ibm.vault.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

/**
 * VaultCoreProperties - Propiedades comunes de Vault compartidas por todos los engines
 * Configuración base para conexión a Vault
 **/
 @Component
 @ConfigurationProperties( prefix = "vault.core" ) 
 @Validated
 public class VaultCoreProperties{ 
	
	    /**
	     * URI del servidor Vault
	     * Ejemplo: http://vault-ui-vault.apps.example.com
	     **/
	     @NotBlank( message = "vault.core.uri es requerido" ) 
	     private String uri; 
	
	    /**
	     * Método de autenticación: TOKEN, USERPASS, APPROLE, etc.
	     **/
	     @NotBlank( message = "vault.core.authentication es requerido" )
	     private String authentication = "USERPASS"; 
	
	    /**
	     * Token de Vault (usado cuando authentication = TOKEN)
	     **/
	     private String token;
	
	    /**
	     * Usuario para autenticación userpass
	     **/
	     private String username;
	
	    /**
	     * Contraseña para autenticación userpass
	     **/
	     private String password;
	
	    /**
	     * Path del método de autenticación en Vault
	     * Ejemplo: userpass, approle, etc.
	     **/
	     private String authPath = "userpass";
	
	     
	     //Getters & Setters: 
	     public String getUri(){
	            return uri;
	     }
	
	     public void setUri( String uri ){
	            this.uri = uri;
	     }
	
	     public String getAuthentication(){ 
	            return authentication;
	     }
	
	     public void setAuthentication( String authentication ){
	            this.authentication = authentication;
	     }
	
	     public String getToken(){ 
	            return token;
	     }
	
	     public void setToken( String token ){
	            this.token = token;
	     }
	
	     public String getUsername(){ 
	            return username;
	     }
	
	     public void setUsername( String username ){
	            this.username = username;
	     }
	
	     public String getPassword(){ 
	            return password;
	     }
	
	     public void setPassword( String password ){
	            this.password = password;
	     }
	
	     public String getAuthPath(){
	            return authPath;
	     }
	
	     public void setAuthPath( String authPath ){
	            this.authPath = authPath;
	     }
     
 }
 
