package pe.com.ibm.vault.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.ibm.vault.config.properties.VaultDatabaseProperties;
import pe.com.ibm.vault.service.Impl.VaultDbServiceImpl;

/**
 * VaultDbConfig - Configuración de DataSource con credenciales dinámicas de VAULT. 
 * Utiliza VaultDatabaseProperties para obtener las propiedades específicas del ENGINE DATABASE.
 **/ 
 @Configuration
 public class VaultDbConfig{
	
	    private VaultDatabaseProperties objVaultDatabaseProperties;  
	    private VaultDbServiceImpl      objVaultDatabaseService; 
	    
	   /**
	    * Constructor con inyección de dependencias
	    * @param objVaultDatabasePropertiesParam - Propiedades del engine Database.
	    * @param objVaultDatabaseServiceParam    - Servicio para obtener credenciales dinámicas.
	    **/
	    public VaultDbConfig( VaultDatabaseProperties objVaultDatabasePropertiesParam,
	                          VaultDbServiceImpl      objVaultDatabaseServiceParam ){
	           this.objVaultDatabaseProperties = objVaultDatabasePropertiesParam;
	           this.objVaultDatabaseService    = objVaultDatabaseServiceParam; 
	    }
	    
	   /**
	    * dataSource - Crea un DataSource con credenciales dinámicas obtenidas de VAULT. 
	    * @return DataSource configurado. 
	    **/
	    @Bean
	    public DataSource dataSource(){ 
		       //Obtener CREDENCIALES dinámicas de VAULT usando el rol configurado:  
		       Map<String, Object> objCredencial = objVaultDatabaseService.getDatabaseCredentials(
		                           objVaultDatabaseProperties.getDefaultRole() 
		       );
		       
		       String vUsername = String.valueOf( objCredencial.get( "username" ) ); 
		       String vPassword = String.valueOf( objCredencial.get( "password" ) );
		       
		       //Crear DataSource con las CREDENCIALES dinámicas: 
		       DataSource objDS = DataSourceBuilder.create() 
						                           .url( objVaultDatabaseProperties.getDatasourceUrl() )
						                           .username( vUsername )
						                           .password( vPassword )
						                           .driverClassName( objVaultDatabaseProperties.getDriverClassName() )  
						                           .build();		       
		       return objDS;
	    }
 }
 