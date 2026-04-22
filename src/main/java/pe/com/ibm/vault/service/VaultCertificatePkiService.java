package pe.com.ibm.vault.service;

import java.util.Map;
 
/**
 * VaultCertificatePkiService
 **/
 public interface VaultCertificatePkiService extends VaultGenericService{
	 
	    public Map<String, Object> generarCertificado( String roleName, String commonName, String ttl );  
	    public Map<String, Object> consultarRol( String roleName ); 
 	    public void registrarRol( String roleName, Map<String, Object> roleConfig );
 	    public Map<String, Object> revocarCertificado( String serialNumber ); 
 	    public String consultarCertificado();  
 	    public String consultarCertificadosRevocados();  
 
 }
 