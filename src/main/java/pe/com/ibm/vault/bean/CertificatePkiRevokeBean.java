package pe.com.ibm.vault.bean;

/**
 * PkiRevokeCertificateBean - Bean para solicitud de REVOCACIÓN de CERTIFICADO PKI. 
 **/
 public class CertificatePkiRevokeBean{

	    private String serialNumber;
	
	    public String getSerialNumber(){
	           return serialNumber;
	    }
	
	    public void setSerialNumber( String serialNumber ){
	           this.serialNumber = serialNumber;
	    }
	    
 }
 