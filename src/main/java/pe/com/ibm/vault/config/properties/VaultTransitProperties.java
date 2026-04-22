package pe.com.ibm.vault.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

/**
 * VaultTransitProperties - Propiedades específicas para el Engine Transit de Vault
 * Configuración para operaciones de cifrado/descifrado
 **/
 @Component
 @ConfigurationProperties( prefix = "vault.transit" ) 
 @Validated
 public class VaultTransitProperties{ 
	
	   /**
	    * Nombre del mount point del engine Transit en Vault
	    * Por defecto: "transit"
	    **/
	    @NotBlank( message = "vault.transit.mount es requerido" ) 
	    private String mount = "transit"; 
	
	   /**
	    * Nombre de la key por defecto para operaciones de cifrado
	    * Ejemplo: "my-encryption-key"
	    **/ 
	    private String defaultKey;
	
	   /**
	    * Habilitar o deshabilitar el engine Transit 
	    **/
	    private boolean enabled = true;
	
	   /**
	    * Tipo de key por defecto: aes256-gcm96, chacha20-poly1305, etc. 
	    **/
	    private String keyType = "aes256-gcm96"; 
	
	   /**
	    * Permitir exportación de keys
	    **/
	    private boolean exportable = false;
	
	    
	    //Getters & Setters: 	
	    public String getMount(){ 
	           return mount;
	    }
	
	    public void setMount( String mount ){ 
	           this.mount = mount;
	    }
	
	    public String getDefaultKey(){ 
	           return defaultKey;
	    }
	
	    public void setDefaultKey( String defaultKey ){ 
	           this.defaultKey = defaultKey;
	    }
	
	    public boolean isEnabled(){ 
	           return enabled;
	    }
	
	    public void setEnabled( boolean enabled ){
	           this.enabled = enabled;
	    }
	
	    public String getKeyType(){ 
	           return keyType;
	    }
	
	    public void setKeyType( String keyType ){ 
	           this.keyType = keyType;
	    }
	
	    public boolean isExportable(){ 
	           return exportable;
	    }
	
	    public void setExportable( boolean exportable ){ 
	           this.exportable = exportable;
	    }
 
 }

 