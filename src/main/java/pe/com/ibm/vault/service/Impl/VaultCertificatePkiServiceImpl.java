package pe.com.ibm.vault.service.Impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import pe.com.ibm.vault.config.properties.VaultCertificateProperties;
import pe.com.ibm.vault.service.VaultCertificatePkiService;

/**
 * VaultCertificatePkiServiceImpl - Implementación de operaciones del Motor PKI de VAULT
 * Utiliza VaultCertificateProperties para obtener las propiedades específicas del ENGINE CERTIFICADO 
 **/
 @Service
 public class VaultCertificatePkiServiceImpl implements VaultCertificatePkiService{ 

    private VaultTemplate              objVaultTemplate; 
    private VaultCertificateProperties objVaultCertificateProperties; 

   /**
    * Constructor con inyección de dependencias
    * @param objVaultTemplateParam - Template de VAULT. 
    * @param objVaultCertificatePropertiesParam - Propiedades del ENGINE CERTIFICADO.
    **/ 
    public VaultCertificatePkiServiceImpl( VaultTemplate              objVaultTemplateParam, 
                                           VaultCertificateProperties objVaultCertificatePropertiesParam ){  
           this.objVaultTemplate              = objVaultTemplateParam;
           this.objVaultCertificateProperties = objVaultCertificatePropertiesParam; 
    }

   /**
    * obtenerSecret - Retorna información del rol PKI especificado
    * @param identificador - Nombre del rol PKI. 
    * @return Map<String, Object> - Información del rol.
    **/
    @Override
    public Map<String, Object> obtenerSecret( String vIdentificadorParam ){
           return this.consultarRol( vIdentificadorParam ); 
    }

   /**
    * generarCertificado 
    * @param  vNombreRol
    * @param  vNombreCommon
    * @param  vTtl
    * @return Map<String, Object> 
    **/
    @Override
    public Map<String, Object> generarCertificado( String vNombreRol, String vNombreCommon, String vTtl ){ 
	       Map<String, Object> objRequest = new HashMap<>();
	       objRequest.put( "common_name", vNombreCommon );
	        
	       if( vTtl != null && !vTtl.isEmpty() ){ 
	           objRequest.put( "ttl", vTtl ); 
	       }
	
	       String        vPkiPath    = objVaultCertificateProperties.getMount();
	       VaultResponse objResponse = objVaultTemplate.write( vPkiPath + "/issue/" + vNombreRol,
	                                                           objRequest ); 
	
           if( objResponse == null || objResponse.getData() == null ){
	           throw new RuntimeException( "No se pudo GENERAR el CERTIFICADO para el rol: " + vNombreRol ); 
	       }
	
	       return objResponse.getData(); 
    }

   /**
    * consultarRol  
    * @param  vNombreRol 
    * @return Map<String, Object> 
    **/    
    @Override
    public Map<String, Object> consultarRol( String vNombreRol ){     	
	       String        vPkiPath    = objVaultCertificateProperties.getMount(); 
	       VaultResponse objResponse = objVaultTemplate.read( vPkiPath + "/roles/" + vNombreRol );
	
	       if( objResponse == null || objResponse.getData() == null ){ 
	           throw new RuntimeException( "No se pudo CONSULTAR el rol PKI: " + vNombreRol );
	       }
	
	       return objResponse.getData();
    }
    
   /**
    * registrarRol  
    * @param  vNombreRol 
    * @param  vRolConfig  
    **/    
    @Override
    public void registrarRol( String vNombreRol, Map<String, Object> vRolConfig ){ 
	       String vPkiPath = objVaultCertificateProperties.getMount(); 
	       objVaultTemplate.write( vPkiPath + "/roles/" + vNombreRol, vRolConfig) ;
    }

   /**
    * revocarCertificado  
    * @param  vNumeroSerial  
    * @return Map<String, Object> 
    **/      
    @Override
    public Map<String, Object> revocarCertificado( String vNumeroSerial ){ 
	       Map<String, Object> vMapRequest = new HashMap<>(); 
	       vMapRequest.put( "serial_number", vNumeroSerial );
	
	       String        vPkiPath    = objVaultCertificateProperties.getMount();
	       VaultResponse objResponse = objVaultTemplate.write( vPkiPath + "/revoke",
	                                                           vMapRequest );
	
	       if( objResponse == null || objResponse.getData() == null ){ 
	           throw new RuntimeException( "No se pudo REVOCAR el certificado: " + vNumeroSerial );
	       }
	
	       return objResponse.getData();
    }

   /**
    * consultarCertificado   
    * @return String 
    **/        
    @SuppressWarnings( "null" )
	@Override
    public String consultarCertificado() {
	       String        vPkiPath    = objVaultCertificateProperties.getMount(); 
	       VaultResponse objResponse = objVaultTemplate.read( vPkiPath + "/cert/ca" ); 
	
	       if( objResponse == null || objResponse.getData() == null ){
	           throw new RuntimeException( "No se pudo CONSULTAR el certificado CA" );
	       }
	
	       Object objCertificado = objResponse.getData().get( "certificate" ); 
	       return objCertificado != null ? objCertificado.toString() : "";
    }

   /**
    * consultarCertificadosRevocados  
     * @return String 
    **/     
    @SuppressWarnings( "null" )
	@Override
    public String consultarCertificadosRevocados(){ 
	       String        vPkiPath    = objVaultCertificateProperties.getMount();
	       VaultResponse objResponse = objVaultTemplate.read( vPkiPath + "/cert/crl" ); 
	
	       if( objResponse == null || objResponse.getData() == null ){ 
	           throw new RuntimeException( "No se pudo CONSULTAR la LISTA de REVOCACION (CRL)" );
	       }
	
	       Object objCRL = objResponse.getData().get( "certificate" ); 
	       return objCRL != null ? objCRL.toString() : ""; 
    }
    
}
