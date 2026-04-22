package pe.com.ibm.vault.service.Impl;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import pe.com.ibm.vault.config.properties.VaultKvProperties;
import pe.com.ibm.vault.service.VaultGenericService;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;

/**
 * VaultKvServiceImpl - Implementación del servicio para el ENGINE KV
 * Utiliza VaultKvProperties para obtener las propiedades específicas del ENGINE KV
 **/ 
 @Service
 public class VaultKvServiceImpl implements VaultGenericService{
	
	    private VaultTemplate     objVaultTemplate;
	    private VaultKvProperties objVaultKvProperties; 
	   
	   /**
	    * Constructor con inyección de dependencias
	    * @param objVaultTemplateParam - Template de VAULT
	    * @param objVaultKvPropertiesParam - Propiedades del ENGINE KV
	    **/
	    public VaultKvServiceImpl( VaultTemplate objVaultTemplateParam, VaultKvProperties objVaultKvPropertiesParam ){ 
	           this.objVaultTemplate     = objVaultTemplateParam;
	           this.objVaultKvProperties = objVaultKvPropertiesParam; 
	    }
	
	   /**
	    * readSecret - Lee un secreto del ENGINE KV
	    * @param  vPathParam - Path del SECRET
	    * @return Map<String, Object> - Datos del SECRET
	    **/
	    @SuppressWarnings( "null" )
		public Map<String, Object> readSecret( String vPathParam ){ 
		       try{
		           //Determinar el BACKEND según la versión configurada
		           KeyValueBackend objBackEnd = "KV_1".equals( objVaultKvProperties.getVersion() )
								                ? KeyValueBackend.KV_1
								                : KeyValueBackend.KV_2;
		            
		           var vResponse = objVaultTemplate.opsForKeyValue( objVaultKvProperties.getMount(), objBackEnd ).get( vPathParam );  
		
	               if( vResponse == null || vResponse.getData() == null ){
	                   throw new IllegalStateException( "NO existe el SECRET o no se pudo leer: [" + vPathParam + "]" );
	               }
	
	               return vResponse.getData(); 
	        }
	        catch( Exception e ){
	               throw new RuntimeException( "ERROR leyendo SECRET '" + vPathParam + "': " + e.getMessage(), e );
	        }
	    }
	    
	   /** 
	    * obtenerSecret - Retorna información del rol PKI especificado
	    * @param  vIdentificadorParam - Nombre del rol PKI 
	    * @return Map<String, Object> - Información del rol 
	    **/
		@Override
		public Map<String, Object> obtenerSecret( String vIdentificadorParam ){ 
			   return this.readSecret( vIdentificadorParam ); 
		} 
 
 }
 