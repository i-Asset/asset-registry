package at.srfg.iot.aas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

import at.srfg.iot.aas.dependency.AssetIndexer;
import at.srfg.iot.aas.dependency.SemanticLookup;
import at.srfg.iot.aas.dependency.SubmodelIndexer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { 
		SecurityAutoConfiguration.class , 
		ManagementWebSecurityAutoConfiguration.class
		})
// 
@EntityScan({
	// TODO: to be removed
	"at.srfg.iot.aas.entity.broker",
	// model for the assset registry
	"at.srfg.iot.common.datamodel.asset", 
	// model for the classification system (this creates the respective tables in the assetdb!)
	"at.srfg.iot.common.datamodel.semanticlookup"})
@ComponentScan({
	// asset-registry components
	"at.srfg.iot.aas"})
@EnableDiscoveryClient
@EnableAsync
@RestController
@EnableSwagger2
@EnableFeignClients(clients = {
		// used for taxonomy integration
		SemanticLookup.class,
		// indexing submodels
		SubmodelIndexer.class,
		// indexing assets
		AssetIndexer.class})
public class AssetRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetRegistryApplication.class, args);
	}

}
