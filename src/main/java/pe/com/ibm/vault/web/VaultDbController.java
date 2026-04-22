package pe.com.ibm.vault.web;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.com.ibm.vault.service.VaultGenericService;
import pe.com.ibm.vault.factory.VaultServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content; 

/**
 * VaultDbController - Controlador para gestionar credenciales de BASE DE DATOS de VAULT
 * Utiliza el patrón FACTORY para acceso a servicios
 **/
 @RestController
 @RequestMapping( "/api/vault-db" )
 @Tag( name = "Vault Base de Datos", description = "API para gestionar credenciales dinámicas de BASE DE DATOS desde VAULT" )
 public class VaultDbController{ 
	
	    private VaultServiceFactory objVaultServiceFactory;
	    private static final String IDENTIFICADOR = "db";
	    
	   /**
	    * VaultDbController
	    * @param objVaultServiceFactoryParam Factory de servicios VAULT
	    **/
	    public VaultDbController( VaultServiceFactory objVaultServiceFactoryParam ){
	           this.objVaultServiceFactory = objVaultServiceFactoryParam;
	    }
	  
	   /**
	    * Obtener credenciales de base de datos desde VAULT
	    * @param  vRoleParam - Nombre del rol de BASE DE DATOS
	    * @return Map<String, Object> - Credenciales de base de datos incluyendo usuario & contraseña
	    **/
	    @Operation(
	        summary     = "Obtener CREDENCIALES de BASE DE DATOS",
	        description = "Recupera CREDENCIALES dinámicas de base de datos desde VAULT para el rol especificado"
	    )
	    @ApiResponses( value = {
	        @ApiResponse(
	            responseCode = "200",
	            description  = "CREDENCIALES de BASE DE DATOS recuperadas exitosamente",
	            content = @Content( mediaType = "application/json" )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description  = "Parámetro de rol inválido"
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description  = "Error interno del servidor o problema de conexión con VAULT"
	        )
	    })
	    @GetMapping( "/getsecret-db" )
	    public Map<String, Object> getDbSecret(
	           @Parameter( description = "Nombre del rol de BASE DE DATOS", required = true, example = "my-role" )
	           @RequestParam( "role" ) String vRoleParam ){ 
	           VaultGenericService objVaultService = objVaultServiceFactory.getService( IDENTIFICADOR );
	           Map<String, Object> objCredential   = objVaultService.obtenerSecret( vRoleParam );
	           return objCredential;
	    }
    
  }
 