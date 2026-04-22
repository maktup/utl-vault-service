package pe.com.ibm.vault.service.Impl;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultTransitContext;
import pe.com.ibm.vault.config.properties.VaultTransitProperties;
import pe.com.ibm.vault.service.VaultTransitService;

/**
 * VaultTransitServiceImpl - Implementación de operaciones del Engine Transit de Vault
 * Utiliza VaultTransitProperties para obtener las propiedades específicas del engine Transit
 */
 @SuppressWarnings( "null" )
 @Service
 public class VaultTransitServiceImpl implements VaultTransitService{
	
	    private final VaultTemplate          objVaultTemplate; 
	    private final VaultTransitOperations objVaultTransitOperations;     
		@SuppressWarnings( "unused" )
		private final VaultTransitProperties objVaultTransitProperties; 
	
	   /**
	    * Constructor con inyección de dependencias
	    * @param objVaultTemplateParam - Template de Vault
	    * @param objVaultTransitPropertiesParam - Propiedades del engine Transit
	    **/    
		public VaultTransitServiceImpl( VaultTemplate          objVaultTemplateParam, 
	                                    VaultTransitProperties objVaultTransitPropertiesParam ){
	           this.objVaultTemplate          = objVaultTemplateParam; 
	           this.objVaultTransitOperations = objVaultTemplateParam.opsForTransit( objVaultTransitPropertiesParam.getMount() ); 
	           this.objVaultTransitProperties = objVaultTransitPropertiesParam;
	    }
	
	   /**
	    * obtenerSecret - Retorna información del rol PKI especificado
	    * @param vIdentificadorParam - Nombre del rol PKI. 
	    * @return Map<String, Object> - Información del rol.
	    **/
	    @Override
	    public Map<String, Object> obtenerSecret( String vIdentificadorParam ){ 
	           return this.consultarKey( vIdentificadorParam ); 
	    } 
	   
	   /**
	    * registrarKey
	    * @param vNombreKey.  
	    **/
	    @Override
	    public void registrarKey( String vNombreKey ){   
	           this.objVaultTemplate.write( "transit/keys/" + vNombreKey , Map.of() );
	    }
	
	   /**
	    * consultarKey
	    * @param  vNombreKey  
	    * @return Map<String, Object>
	    **/
	    @Override
	    public Map<String, Object> consultarKey( String vNombreKey ){  
		       VaultResponse objResponse = this.objVaultTemplate.read( "transit/keys/" + vNombreKey );
		
		       if( objResponse == null || objResponse.getData() == null ){ 
		           throw new RuntimeException( "NO se pudo leer la KEY: " + vNombreKey ); 
		       }
		
		       return objResponse.getData();
	    }
	 
	   /**
	    * procesarEncriptacion
	    * @param  vNombreKey  
	    * @param  vCadena  
	    * @return String
	    **/    
		@Override
	    public String procesarEncriptacion( String vNombreKey, String vCadena ){   
		       return this.objVaultTransitOperations.encrypt( 
		              vNombreKey,
		              vCadena.getBytes( StandardCharsets.UTF_8 ),
		              VaultTransitContext.empty()
		       );	
	    }
	
	   /**
	    * procesarDesencriptacion
	    * @param  vNombreKey  
	    * @param  vCadena  
	    * @return String
	    **/ 
	    @Override
	    public String procesarDesencriptacion( String vNombreKey, String vCadena ){  
	        
			byte[] vArrayCadena = this.objVaultTransitOperations.decrypt( 
					              vNombreKey,
					              vCadena,
					              VaultTransitContext.empty()
	        );
	
	        return new String( vArrayCadena, StandardCharsets.UTF_8 ); 
	    }
	    
	   /**
	    * procesarReEncriptacion
	    * @param  vCadenaEncriptada  
	    * @param  vCadena  
	    * @return String
	    **/ 
	    @Override
	    public String procesarReEncriptacion( String vCadenaEncriptada, String vCadena ){ 
	    	
		       VaultResponse objVaultResponse = this.objVaultTemplate.write( "transit/rewrap/" + vCadenaEncriptada,
		                                                                     Map.of("ciphertext", vCadena ) 
		       );
		
		       if( objVaultResponse == null || objVaultResponse.getData() == null ){ 
		           throw new RuntimeException( "NO se pudo hacer REWRAP de la KEY: " + vCadenaEncriptada ); 
		       }
		
		       Object objCadenaCipher = objVaultResponse.getData().get( "ciphertext" ); 
		       if( objCadenaCipher == null ){
		           throw new RuntimeException( "VAULT no devolvió CADENA CIPHER al hacer REWRAP" );
		       }
		
		       return objCadenaCipher.toString();
	    }
    
 }
