package pe.com.ibm.vault.web;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * VaultKvController - Controlador para gestionar secretos KEY-VALUE de VAULT
 * Utiliza el patrón FACTORY para acceso a servicios
 **/
 @RestController
 @RequestMapping( "/api/vault-kv" )
 @Tag( name = "Vault Key-Value", description = "API para gestionar secretos KEY-VALUE almacenados en VAULT" )
 public class VaultKvController{
	
	    private VaultServiceFactory objVaultServiceFactory;
	    private static final String IDENTIFICADOR = "kv";
	   
	   /**
	    * VaultKvController
	    * @param objVaultServiceFactoryParam Factory de servicios VAULT
	    **/
	    public VaultKvController( VaultServiceFactory objVaultServiceFactoryParam ){ 
	           this.objVaultServiceFactory = objVaultServiceFactoryParam;
	    }
	   
	   /**
	    * Obtener secreto KEY-VALUE desde VAULT
	    * @param  path - Ruta al secreto en VAULT
	    * @return ResponseEntity<?> - Datos del secreto o mensaje de error
	    **/ 
	    @Operation(
	        summary     = "Obtener ENGINE de tipo: KEY-VALUE",
	        description = "Recupera un secreto KEY-VALUE desde VAULT usando la ruta especificada"
	    )
	    @ApiResponses( value = {
	        @ApiResponse(
	            responseCode = "200",
	            description  = "Secreto recuperado exitosamente", 
	            content = @Content( mediaType = "application/json" )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description  = "Parámetro de ruta inválido o solicitud incorrecta",
	            content = @Content( mediaType = "application/json" )
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description  = "Error interno del servidor o problema de conexión con VAULT", 
	            content = @Content( mediaType = "application/json" )
	        )
	    })
	    @GetMapping( "/getsecret-kv" )
	    public ResponseEntity<?> obtenerSecret(
		       @Parameter( description = "Ruta al secreto en VAULT", required = true, example = "secret/data/myapp" )
		       @RequestParam( "path" ) String path ){
		       try{
		           VaultGenericService objVaultService = objVaultServiceFactory.getService( IDENTIFICADOR );
		           Map<String, Object> objMapService   = objVaultService.obtenerSecret( path );
		             
		           return ResponseEntity.ok( objMapService );
		       }
		       catch( IllegalArgumentException e ){
		              return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( Map.of( "error", e.getMessage() ) );
		       }
		       catch( Exception e ){
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) );
		       }
	    }
 
 }
