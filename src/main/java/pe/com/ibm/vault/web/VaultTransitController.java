package pe.com.ibm.vault.web;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.ibm.vault.bean.TransitDecryptBean;
import pe.com.ibm.vault.bean.TransitEncryptBean;
import pe.com.ibm.vault.bean.TransitRewrapBean;
import pe.com.ibm.vault.service.VaultTransitService;
import pe.com.ibm.vault.factory.VaultServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * VaultTransitController - Controlador para operaciones del Motor TRANSIT de VAULT
 * Utiliza el patrón FACTORY para acceso a servicios
 **/
 @RestController
 @RequestMapping( "/api/vault-transit" )
 @Tag( name = "Vault Transit", description = "API para Motor TRANSIT de VAULT" ) 
 public class VaultTransitController{ 
	
	    private VaultServiceFactory objVaultServiceFactory; 
	    private static final String IDENTIFICADOR = "transit"; 
	
	    public VaultTransitController( VaultServiceFactory objVaultServiceFactoryParam ){ 
	           this.objVaultServiceFactory = objVaultServiceFactoryParam;
	    }
	
	   /**
	    * Obtener VaultTransitService desde el FACTORY
	    * @return VaultTransitService
	    **/
	    private VaultTransitService getTransitService(){ 
	            return (VaultTransitService)objVaultServiceFactory.getService( IDENTIFICADOR );
	    }
	   
	   /**
	    * createKey
	    * @param  vNombreKey
	    * @return ResponseEntity
	    **/
	    @Operation(
	        summary     = "CREAR Clave TRANSIT",
	        description = "CREA una nueva KEY de encriptación en el motor TRANSIT de VAULT" 
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "KEY creada exitosamente" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @PostMapping( "/keys/create/{vNombreKey}" )
	    public ResponseEntity<?> createKey( 
		       @Parameter( description = "Nombre de la KEY de encriptación", required = true, example = "my-key" )
		       @PathVariable String vNombreKey ){ 
		       try{ 
		           this.getTransitService().registrarKey( vNombreKey ); 
		           return ResponseEntity.ok( Map.of(
		                  "message", "KEY creada correctamente", 
		                  "keyName", vNombreKey
		           ));
		       } 
		       catch( Exception e ) {
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) ); 
		       }
	    }
	
	   /**
	    * readKey 
	    * @param  vNombreKey
	    * @return ResponseEntity<?>
	    **/
	    @Operation(
	        summary     = "CONSULTAR Clave TRANSIT",
	        description = "RECUPERAR información sobre una KEY de encriptación del motor TRANSIT de VAULT"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Información de KEY recuperada exitosamente" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    }) 
	    @GetMapping( "/keys/list/{vNombreKey}" ) 
	    public ResponseEntity<?> readKey(
		       @Parameter( description = "Nombre de la KEY de ENCRIPTACIÓN", required = true, example = "my-key" )
		       @PathVariable String vNombreKey ) { 
		       try{
		           return ResponseEntity.ok( this.getTransitService().consultarKey( vNombreKey ));
		       } 
		       catch( Exception e ) {
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) ); 
		       }
	    }
	
	   /**
	    * encrypt  
	    * @param  objRequest
	    * @return ResponseEntity<?>
	    **/
	    @Operation(
	        summary     = "ENCRIPTAR Datos",
	        description = "ENCRIPTA datos en TEXTO PLANO usando la clave TRANSIT especificada"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Datos ENCRIPTADOS exitosamente" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @PostMapping( "/keys/encrypt" )
	    public ResponseEntity<?> encrypt( @RequestBody TransitEncryptBean objRequest ){
		       try{
		           String vCadenaCipher = this.getTransitService().procesarEncriptacion(
				                          objRequest.getKeyName(),
				                          objRequest.getPlaintext() 
		           );
		
		           return ResponseEntity.ok( Map.of( "keyName",    objRequest.getKeyName(),
		                                             "ciphertext", vCadenaCipher
		           ));
		       } 
		       catch( Exception e ) {
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ));
		       }
	    }
	  
	   /**
	    * decrypt 
	    * @param  objRequest
	    * @return ResponseEntity<?>
	    **/
	    @Operation(
	        summary     = "DESENCRIPTAR Datos",
	        description = "DESENCRIPTA datos cifrados usando la KEY TRANSIT especificada"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Datos DESENCRIPTADOS exitosamente" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @PostMapping( "/keys/decrypt" )
	    public ResponseEntity<?> decrypt( @RequestBody TransitDecryptBean objRequest ){ 
		       try{
		           String vCadena = this.getTransitService().procesarDesencriptacion(
		                            objRequest.getKeyName(),
		                            objRequest.getCiphertext() 
		            );
		
		            return ResponseEntity.ok( Map.of(
		                    "keyName", objRequest.getKeyName(),
		                    "plaintext", vCadena
		            ));
		        } 
		        catch( Exception e ){ 
		               return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) ); 
		        }
	    }
	
	   /**
	    * rewrap
	    * @param  objRequest
	    * @return ResponseEntity<?> 
	    **/
	    @Operation(
	        summary     = "Re-ENCRIPTAR cadena ENCRIPTADA",
	        description = "RE-ENCRIPTA cadena ENCRIPTADA con la última versión de la KEY TRANSIT"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Cadena RE-ENCRIPTADA exitosamente" ), 
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @PostMapping( "/keys/rewrap" )
	    public ResponseEntity<?> rewrap( @RequestBody TransitRewrapBean objRequest ){ 
		       try{
		           String objCadenaCipher = this.getTransitService().procesarReEncriptacion( 
					                        objRequest.getKeyName(),
					                        objRequest.getCiphertext()
		           );
		
		           return ResponseEntity.ok( Map.of( "keyName", objRequest.getKeyName(),
		                                             "ciphertext", objCadenaCipher
		           ));
		       } 
		       catch( Exception e ) {
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) ); 
		       }
	    }
	    
 }

