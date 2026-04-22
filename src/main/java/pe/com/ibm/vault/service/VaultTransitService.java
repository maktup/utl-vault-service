package pe.com.ibm.vault.service;

import java.util.Map;

/**
 * VaultTransitService
 **/
 public interface VaultTransitService extends VaultGenericService{

	    public void registrarKey( String vNombreKey ); 	 
	    public Map<String, Object> consultarKey( String vNombreKey );  	
	    public String procesarEncriptacion( String vNombreKey, String vCadena );	
	    public String procesarDesencriptacion( String vNombreKey, String vCadena );	
	    public String procesarReEncriptacion( String vCadenaEncriptada, String vCadena );	    
	    
 }
