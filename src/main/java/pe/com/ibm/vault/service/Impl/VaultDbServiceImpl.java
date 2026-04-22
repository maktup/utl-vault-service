package pe.com.ibm.vault.service.Impl;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import pe.com.ibm.vault.service.VaultGenericService;

/**
 * VaultDbService
 **/
 @Service
 public class VaultDbServiceImpl implements VaultGenericService{
	
	    private VaultTemplate objVaultTemplate; 
	   
	   /**
	    * VaultDbService 
	    * @param objVaultTemplateParam
	    **/
	    public VaultDbServiceImpl( VaultTemplate objVaultTemplateParam ){ 
	           this.objVaultTemplate = objVaultTemplateParam;
	    }
	  
	   /**
	    * getDatabaseCredentials 
	    * @param  nombreRol
	    * @return Map<String, Object>
	    **/ 
	    public Map<String, Object> getDatabaseCredentials( String nombreRol ){ 
		       VaultResponse objResponse = objVaultTemplate.read( "database/creds/" + nombreRol );
		 
		       if( objResponse == null || objResponse.getData() == null ){
		           throw new IllegalStateException( "NO se pudieron obtener CREDENCIALES DINÁMICAS para el ROL: [" + nombreRol + "]" );
		       }
		        
		       return objResponse.getData(); 
	    }
	    
	   /**
	    * obtenerSecret - Retorna información del rol PKI especificado
	    * @param identificador - Nombre del rol PKI. 
	    * @return Map<String, Object> - Información del rol.
	    **/
		@Override
		public Map<String, Object>  obtenerSecret( String identificador ){  
			   return this.getDatabaseCredentials( identificador );	 	   
		}
 
 }
 