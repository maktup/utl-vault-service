package pe.com.ibm.vault.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

/**
 * VaultKvProperties - Propiedades específicas para el Engine KV (Key-Value) de Vault
 * Configuración para operaciones de secretos estáticos
 */
 @Component
 @ConfigurationProperties( prefix = "vault.kv" )
 @Validated
 public class VaultKvProperties{ 
	
	   /**
	    * Nombre del mount point del engine KV en Vault
	    * Por defecto: "secret"
	    **/
	    @NotBlank( message = "vault.kv.mount es requerido" )
	    private String mount = "secret"; 
	
	   /**
	    * Versión del engine KV: KV_1 o KV_2
	    * Por defecto: KV_2 (recomendado)
	    **/
	    private String version = "KV_2";
	    
	   /**
	    * Path base para los secretos dentro del mount
	    * Ejemplo: "app/config" 
	    **/ 
	    private String basePath = "";
	
	   /**
	    * Habilitar o deshabilitar el engine KV 
	    **/
	    private boolean enabled = true;
	
	    
	    //Getters & Setters: 	
	    public String getMount(){
	           return mount;
	    }
	
	    public void setMount( String mount ){
	           this.mount = mount;
	    }
	
	    public String getVersion(){
	           return version;
	    }
	
	    public void setVersion( String version ){
	           this.version = version;
	    }
	
	    public String getBasePath(){ 
	           return basePath;
	    }
	
	    public void setBasePath( String basePath ){
	           this.basePath = basePath;
	    }
	
	    public boolean isEnabled(){
	           return enabled;
	    }
	
	    public void setEnabled( boolean enabled ){
	           this.enabled = enabled;
	    }
}

