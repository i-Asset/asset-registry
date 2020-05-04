package at.srfg.iot.aas.service.basys;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.service.basys.event.publisher.MappingEventPublisher;
import at.srfg.iot.aas.repository.basys.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.basys.IdentifiableRepository;

@Service
public class BasysService {
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	

	@Autowired
	private MappingEventPublisher submodelEvent;
	
	@Autowired
	private IdentifiableRepository<Submodel> submodelRepo;
	
	/**
	 * Process 
	 * @param map
	 */
	public void processAssetAdministrationShell(Map<String,Object> map) {
		Identifier identifier = MappingHelper.getIdentifier(map);
		// does it exist?
		Optional<AssetAdministrationShell> shell = aasRepo.findByIdentification(identifier);
		// when not yet registered, create the shell
		AssetAdministrationShell theShell = shell.orElse(new AssetAdministrationShell(identifier));
		// process the map
		
		submodelEvent.handleAssetAdministrationShell(map, theShell);
		// save the shell 
		aasRepo.save(theShell);

		
	}
	public void processSubmodel(Map<String,Object> map) {
		Identifier identifier = MappingHelper.getIdentifier(map);

		
		// does it exist?
		Optional<Submodel> shell = submodelRepo.findByIdentification(identifier);
		if ( shell.isPresent() ) {
			
			Submodel theShell = shell.get();
			// fill in the submodel
			submodelEvent.handleSubmodel(map, theShell);
			// 
			submodelRepo.save(theShell);
		}
		else {
			throw new IllegalArgumentException("Cannot store an unregistered submodel" );
		}
	}
	public Map<String,Object> processAssetAdministrationShell(AssetAdministrationShell aas) {
		Map<String,Object> result = submodelEvent.getFromAssetAdministrationShell(aas);
		
		return result;
	}
}