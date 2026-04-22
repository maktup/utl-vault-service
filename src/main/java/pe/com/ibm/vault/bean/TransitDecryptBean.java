package pe.com.ibm.vault.bean;

/**
 * TransitDecryptBean - Bean para solicitud de DESENCRIPTACIÓN de TRANSIT. 
 **/
 public class TransitDecryptBean{ 
	
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
