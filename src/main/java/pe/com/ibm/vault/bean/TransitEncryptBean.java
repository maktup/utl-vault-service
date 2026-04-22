package pe.com.ibm.vault.bean;

/**
 * TransitEncryptBean - Bean para solicitud de ENCRIPTACIÓN de TRANSIT. 
 **/
 public class TransitEncryptBean{
	
	    private String keyName;
	    private String plaintext;
	
	    public String getKeyName(){
	           return keyName;
	    }
	
	    public void setKeyName( String keyName ){ 
	           this.keyName = keyName;
	    }
	
	    public String getPlaintext(){
	           return plaintext;
	    }
	
	    public void setPlaintext( String plaintext ){ 
	           this.plaintext = plaintext;
	    }
 
 }

