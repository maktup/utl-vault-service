package pe.com.ibm.vault.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

/**
 * VaultCertificateProperties - Propiedades específicas para el Engine PKI/Certificate de VAULT. 
 * Configuración para generación & gestión de certificados. 
 **/ 
 @Component
 @ConfigurationProperties( prefix = "vault.certificate" ) 
 @Validated
 public class VaultCertificateProperties{ 
	
	   /**
	    * Nombre del mount point del engine PKI en Vault
	    * Por defecto: "pki" 
	    **/
	    @NotBlank( message = "vault.certificate.mount es requerido" ) 
	    private String mount = "pki"; 
	
	   /**
	    * Rol por defecto para generar certificados
	    * Ejemplo: "ibm-web-server" 
	    **/
	    private String defaultRole; 
	
	   /**
	    * TTL por defecto para certificados generados
	    * Ejemplo: "720h" (30 días)
	    **/ 
	    private String defaultTtl = "720h"; 
	
	   /**
	    * TTL máximo permitido para certificados
	    * Ejemplo: "8760h" (1 año)
	    **/
	    private String maxTtl = "8760h"; 
	
	   /**
	    * Habilitar o deshabilitar el engine Certificate
	    **/
	    private boolean enabled = true;
	
	   /**
	    * Common Name por defecto para certificados
	    **/
	    private String defaultCommonName;
	
	   /**
	    * Organization por defecto
	    **/ 
	    private String organization;
	
	   /**
	    * Country por defecto
	    **/ 
	    private String country;
	
	    
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
	
	    public String getDefaultTtl(){
	        return defaultTtl;
	    }
	
	    public void setDefaultTtl( String defaultTtl ){ 
	           this.defaultTtl = defaultTtl;
	    }
	
	    public String getMaxTtl(){
	        return maxTtl;
	    }
	
	    public void setMaxTtl( String maxTtl ){
	           this.maxTtl = maxTtl;
	    }
	
	    public boolean isEnabled(){ 
	           return enabled;
	    }
	
	    public void setEnabled( boolean enabled ){
	           this.enabled = enabled;
	    }
	
	    public String getDefaultCommonName(){     
	           return defaultCommonName;
	    }
	
	    public void setDefaultCommonName( String defaultCommonName ){ 
	           this.defaultCommonName = defaultCommonName;
	    }
	
	    public String getOrganization(){
	           return organization;
	    }
	
	    public void setOrganization( String organization ){ 
	           this.organization = organization;
	    }
	
	    public String getCountry(){ 
	           return country;
	    }
	
	    public void setCountry( String country ){ 
	           this.country = country;
	    }
 
 }
 
