package pe.com.ibm.vault.service;

import java.util.Map;

/**
 * VaultGenericService 
 **/
 public interface VaultGenericService{
       
	    public Map<String, Object> obtenerSecret( String identificador );  
	    
 }

