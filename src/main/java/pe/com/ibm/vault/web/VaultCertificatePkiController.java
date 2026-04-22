package pe.com.ibm.vault.web;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.ibm.vault.bean.CertificatePkiGenerateBean;
import pe.com.ibm.vault.bean.CertificatePkiRevokeBean;
import pe.com.ibm.vault.service.VaultCertificatePkiService;
import pe.com.ibm.vault.factory.VaultServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag; 

/**
 * VaultPkiController - Controlador para operaciones del Motor PKI de VAULT 
 * Utiliza el patrón FACTORY para acceso a servicios
 **/
 @RestController
 @RequestMapping( "/api/vault-pki" )
 @Tag( name = "Vault PKI", description = "API para Motor PKI de VAULT - gestión de certificados & autoridad certificadora" ) 
 public class VaultCertificatePkiController{
	
	    private VaultServiceFactory objVaultServiceFactory; 
	    private static final String IDENTIFICADOR = "pki";
	   
	   /**
	    * VaultCertificatePkiController  
	    * @param objVaultServiceFactoryParam
	    **/
	    public VaultCertificatePkiController( VaultServiceFactory objVaultServiceFactoryParam ){ 
	           this.objVaultServiceFactory = objVaultServiceFactoryParam; 
	    }
	
	   /**
	    * Obtener VaultPkiService desde el FACTORY 
	    * @return VaultPkiService 
	    **/
	    private VaultCertificatePkiService getPkiService(){
	            return (VaultCertificatePkiService)this.objVaultServiceFactory.getService( IDENTIFICADOR ); 
	    }
	
	   /**
	    * generateCertificate  
	    * @param  objRequest
	    * @return ResponseEntity
	    **/
	    @Operation(
	        summary     = "Generar CERTIFICADO",
	        description = "Genera un nuevo CERTIFICADO X.509 usando el rol PKI especificado"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Certificado generado exitosamente" ),
	        @ApiResponse( responseCode = "400", description = "Parámetros inválidos" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @PostMapping( "/certificates/generate" )
	    public ResponseEntity<?> generateCertificate( @RequestBody CertificatePkiGenerateBean objRequest ){ 
		       try{
		           Map<String, Object> objCertificado = this.getPkiService().generarCertificado( 
										                objRequest.getRoleName(),
										                objRequest.getCommonName(),
										                objRequest.getTtl() 
		           );
		           return ResponseEntity.ok( objCertificado );
		       } 
		       catch( Exception e ){
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) );  
		       }
	    }
	
	   /**
	    * readRole  
	    * @param  vNombreRol
	    * @return ResponseEntity<?>
	    **/ 
	    @Operation(
	        summary     = "Leer Rol PKI",
	        description = "Recupera la configuración de un rol PKI específico"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Rol recuperado exitosamente" ),
	        @ApiResponse( responseCode = "404", description = "Rol no encontrado" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @GetMapping( "/certificates/roles/{vNombreRol}" )
	    public ResponseEntity<?> readRole(
		       @Parameter( description = "Nombre del rol PKI", required = true, example = "my-role" )
		       @PathVariable String vNombreRol ){ 
		       try{ 
		           Map<String, Object> vMapaRole = this.getPkiService().consultarRol( vNombreRol );
		           return ResponseEntity.ok( vMapaRole ); 
		       } 
		       catch( Exception e ){
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) );  
		       }
	    }
	 
	   /**
	    * createRole  
	    * @param  vNombreRol
	    * @param  vMapRolConfig
	    * @return ResponseEntity<?>
	    **/
	    @Operation(
	        summary     = "Crear Rol PKI",
	        description = "Crea un nuevo rol PKI con la configuración especificada"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Rol creado exitosamente" ),
	        @ApiResponse( responseCode = "400", description = "Configuración inválida"  ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @PostMapping( "/certificates/roles/{vNombreRol}" )
	    public ResponseEntity<?> createRole( 
		       @Parameter( description = "Nombre del rol PKI", required = true, example = "my-role" )
		       @PathVariable String vNombreRol,
		       @RequestBody  Map<String, Object> vMapRolConfig ){ 
		       try{ 
		           this.getPkiService().registrarRol( vNombreRol, vMapRolConfig ); 
		           return ResponseEntity.ok( Map.of( "message", "Rol PKI creado exitosamente",
		                                             "roleName", 
		                                             vNombreRol ) );
		       } 
		       catch( Exception e ){
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) );  
		       }
	    }
	
	   /**
	    * revokeCertificate  
	    * @param  objRequest
	    * @return ResponseEntity<?>
	    **/
	    @Operation(
	        summary     = "Revocar CERTIFICADO",
	        description = "Revoca un CERTIFICADO usando su número de serie"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Certificado revocado exitosamente" ),
	        @ApiResponse( responseCode = "400", description = "Número de serie inválido" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @PostMapping( "/certificates/revoke" )
	    public ResponseEntity<?> revokeCertificate( @RequestBody CertificatePkiRevokeBean objRequest ){ 
		       try{
		           Map<String, Object> vMapaResultado = this.getPkiService().revocarCertificado( objRequest.getSerialNumber() ); 
		           return ResponseEntity.ok( vMapaResultado );
		       } 
		       catch( Exception e ){
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) );  
		       }
	    } 
	 
	   /**
	    * readCaCertificate 
	    * @return ResponseEntity<?> 
	    **/
	    @Operation(
	        summary     = "Leer CERTIFICADO CA",
	        description = "Recupera el CERTIFICADO de la Autoridad Certificadora (CA) en formato PEM"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "Certificado CA recuperado exitosamente" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" )
	    })
	    @GetMapping( "/certificates/ca" )
	    public ResponseEntity<?> readCaCertificate(){
		       try{
		           String objCertificadoCA = this.getPkiService().consultarCertificado(); 
		           return ResponseEntity.ok( Map.of( "certificate", objCertificadoCA ) );
		       } 
		       catch( Exception e ){
		            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) );  
		       }
	    }
	   
	   /**
	    * readCrl  
	    * @return ResponseEntity<?>
	    **/
	    @Operation(
	        summary     = "Leer Lista de RENOVACIÓN (CRL)",
	        description = "Recupera la lista de CERTIFICADOS REVOCADOS (Certificate Revocation List)"
	    )
	    @ApiResponses( value = {
	        @ApiResponse( responseCode = "200", description = "CRL recuperada exitosamente" ),
	        @ApiResponse( responseCode = "500", description = "Error interno del servidor" ) 
	    })
	    @GetMapping( "/certificates/crl" )
	    public ResponseEntity<?> readCrl(){
		       try{
		           String vCadenaCRL = this.getPkiService().consultarCertificadosRevocados();
		           return ResponseEntity.ok( Map.of( "crl", vCadenaCRL ) ); 
		       } 
		       catch( Exception e ){ 
		              return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( Map.of( "error", e.getMessage() ) ); 
		       }
	    }
	    
 }


