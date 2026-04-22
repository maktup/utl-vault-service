package pe.com.ibm.vault.factory;

import org.springframework.stereotype.Component;

import pe.com.ibm.vault.service.VaultGenericService;
import pe.com.ibm.vault.service.Impl.VaultDbServiceImpl;
import pe.com.ibm.vault.service.Impl.VaultKvServiceImpl;
import pe.com.ibm.vault.service.Impl.VaultTransitServiceImpl;
import pe.com.ibm.vault.service.Impl.VaultCertificatePkiServiceImpl;

/**
 * VaultServiceFactory - Implementación del patrón Factory para servicios Vault
 * Proporciona acceso unificado a los engines: DB, KV, Transit y PKI
 **/
 @Component
 public class VaultServiceFactory{

	    private final VaultDbServiceImpl             objVaultDbService;
	    private final VaultKvServiceImpl             objVaultKvService;
	    private final VaultTransitServiceImpl        objVaultTransitService; 
	    private final VaultCertificatePkiServiceImpl objVaultPkiService; 
	   
	   /**
	    * VaultServiceFactory 
	    * @param objVaultDbServiceParam
	    * @param objVaultKvServiceParam
	    * @param objVaultTransitServiceParam
	    * @param objVaultPkiServiceParam
	    **/
	    public VaultServiceFactory(
	           VaultDbServiceImpl             objVaultDbServiceParam,
	           VaultKvServiceImpl             objVaultKvServiceParam,
	           VaultTransitServiceImpl        objVaultTransitServiceParam, 
	           VaultCertificatePkiServiceImpl objVaultPkiServiceParam
	    ){
	           this.objVaultDbService      = objVaultDbServiceParam;
	           this.objVaultKvService      = objVaultKvServiceParam;
	           this.objVaultTransitService = objVaultTransitServiceParam;
	           this.objVaultPkiService     = objVaultPkiServiceParam; 
	    }
   
       /**
        * getService - Retorna el servicio VAULT apropiado basado en el identificador. 
        * @param  vIdentificadorParam - Identificador del servicio: "db", "kv", "transit" o "pki". 
        * @return VaultGenericService - La implementación del servicio solicitado. 
        **/
	    public VaultGenericService getService( String vIdentificadorParam ){ 
		       switch( vIdentificadorParam.toLowerCase() ){
			           case "db":
			                return objVaultDbService;
			           case "kv":
			                return objVaultKvService;
			           case "transit":
			                return objVaultTransitService;
			           case "pki":
			                return objVaultPkiService;
			           default:
			                throw new IllegalArgumentException( "IDENTIFICADOR no soportado: [" + vIdentificadorParam + "]. Valores válidos: db, kv, transit, pki" );  
		       }
	    }
 }

