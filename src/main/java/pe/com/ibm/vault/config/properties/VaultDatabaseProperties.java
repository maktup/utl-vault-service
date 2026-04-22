package pe.com.ibm.vault.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

/**
 * VaultDatabaseProperties - Propiedades específicas para el Engine Database de Vault
 * Configuración para credenciales dinámicas de bases de datos
 */
 @Component
 @ConfigurationProperties( prefix = "vault.database" ) 
 @Validated
 public class VaultDatabaseProperties{ 
	
	   /**
	    * Nombre del mount point del engine Database en VAULT 
	    * Por defecto: "database"
	    **/
	    @NotBlank( message = "vault.database.mount es requerido" )
	    private String mount = "database";
	
	   /**
	    * Rol por defecto para obtener credenciales
	    * Ejemplo: "mariadb-app"
	    **/
	    private String defaultRole = "mariadb-app"; 
	
	   /**
	    * URL del datasource externo (no gestionado por Vault)
	    * Ejemplo: jdbc:mariadb://mariadb.dummy-database.svc:3306/mariadb 
	    **/
	    @NotBlank( message = "vault.database.datasource-url es requerido" )
	    private String datasourceUrl;
	
	   /**
	    * Driver JDBC a utilizar
	    **/
	    private String driverClassName = "org.mariadb.jdbc.Driver"; 
	    
	   /**
	    * Habilitar o deshabilitar el engine Database 
	    **/
	    private boolean enabled = true;
	
	    
	    //Getters & Setters: 	
	    public String getMount(){ 
	           return mount;
	    }
	
	    public void setMount( String mount ){
	           this.mount = mount;
	    }
	
	    public String getDefaultRole(){
	           return defaultRole; 
	    }
	
	    public void setDefaultRole( String defaultRole ){
	           this.defaultRole = defaultRole;
	    }
	
	    public String getDatasourceUrl(){
	           return datasourceUrl;
	    }
	
	    public void setDatasourceUrl( String datasourceUrl ){
	           this.datasourceUrl = datasourceUrl;
	    }
	
	    public String getDriverClassName(){ 
	           return driverClassName;
	    }
	
	    public void setDriverClassName( String driverClassName ){ 
	           this.driverClassName = driverClassName;
	    }
	
	    public boolean isEnabled(){ 
	           return enabled;
	    }
	
	    public void setEnabled( boolean enabled ){ 
	           this.enabled = enabled;
	    }
	    
 }
 
