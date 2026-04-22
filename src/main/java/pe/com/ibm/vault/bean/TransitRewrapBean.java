package pe.com.ibm.vault.bean;

/**
 * TransitRewrapBean - Bean para volver a ENCRIPTAR DATOS ya ENCRIPTADOS de TRANSIT.  
 **/
 public class TransitRewrapBean{
	
	    private String keyName;
	    private String ciphertext;
	
	    public String getKeyName(){
	           return keyName;
	    }
	
	    public void setKeyName( String keyName ){
	           this.keyName = keyName;
	    }
	
	    public String getCiphertext(){
	           return ciphertext;
	    }
	
	    public void setCiphertext( String ciphertext ){ 
	           this.ciphertext = ciphertext;
	    }
	    
 }
