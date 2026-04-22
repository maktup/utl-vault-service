package pe.com.ibm.vault.bean;

/**
 * PkiGenerateCertificateBean - Bean para solicitud de GENERACIÓN de CERTIFICADO PKI.
 **/
 public class CertificatePkiGenerateBean{
	
	    private String roleName;
	    private String commonName;
	    private String ttl;
	    private String altNames; 
	    private String ipSans;
	
	    public String getRoleName(){
	           return roleName;
	    }
	
	    public void setRoleName( String roleName ){
	           this.roleName = roleName;
	    }
	
	    public String getCommonName(){
	           return commonName;
	    }
	
	    public void setCommonName( String commonName ){
	           this.commonName = commonName;
	    }
	
	    public String getTtl(){
	           return ttl;
	    }
	
	    public void setTtl( String ttl ){
	           this.ttl = ttl;
	    }
	
	    public String getAltNames(){
	           return altNames;
	    }
	
	    public void setAltNames( String altNames ){
	           this.altNames = altNames;
	    }
	
	    public String getIpSans() {
	           return ipSans;
	    }
	
	    public void setIpSans( String ipSans ){ 
	           this.ipSans = ipSans;
	    }
    
  }
 