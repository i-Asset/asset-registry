package at.srfg.iot.aas.service.registry.event.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.common.referencing.Key;
import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.common.referencing.Reference;
import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.event.SubmodelEvent;

@Component
public class SubmodelEventHandler {
	@Autowired
	
	private RegistryService registry;
	@EventListener
	public void onSubmodelEvent(SubmodelEvent event) {
		
		Submodel entity = event.getEntity();
		Submodel dto = event.getDTO();
		
		// be sure to find the enclosing AAS - each submodel must belong to an AAS
		if ( entity.getParentElement() == null) {
			// need to resolve the aas from the event object
			AssetAdministrationShell parent = event.getAssetAdministrationShell();
			if ( parent != null) {
				entity.setParent(parent);
			}
			else {
				throw new IllegalArgumentException("AAS identifier is missing, provide reference to the AAS (element \"parent\") ");
			}
		}else {
			// no id provided - the ID must be a Reference in the DTO
			if ( dto.getParentElement() instanceof Reference) {
				Reference r = (Reference) dto.getParentElement();
				Optional<Key> aasKey = r.getKey(KeyElementsEnum.AssetAdministrationShell);
				// 
				if ( aasKey.isPresent()) {
					Key theKey = aasKey.get();
					Optional<AssetAdministrationShell> aas = registry.getAssetAdministrationShell(theKey);
					if ( aas.isPresent() ) {
						// registers submodel as child of the shell
						entity.setParent(aas.get());
					}
					else {
						throw new IllegalArgumentException("Provided AAS identifier is not valid!");
					}
				}
				else {
					throw new IllegalArgumentException("No Key pointing to an AssetAdministrationShell found");
				}
			}
			else {
				throw new IllegalArgumentException("Reference to the AssetAdministrationShell is missing!");
			}
		}
	
		// other attributes are managed with the respective event handlers
		
	}
}
