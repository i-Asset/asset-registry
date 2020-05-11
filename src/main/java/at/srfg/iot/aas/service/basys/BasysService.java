package at.srfg.iot.aas.service.basys;

import java.util.Map;
import java.util.Optional;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.basic.AssetAdministrationShell;
import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.basic.Submodel;
import at.srfg.iot.aas.repository.basys.AssetAdministrationShellRepository;
import at.srfg.iot.aas.repository.basys.IdentifiableRepository;
import at.srfg.iot.aas.service.basys.event.handler.util.MappingHelper;
import at.srfg.iot.aas.service.basys.event.publisher.MappingEventPublisher;

@Service
public class BasysService {
	@Autowired
	private AssetAdministrationShellRepository aasRepo;
	

	@Autowired
	private MappingEventPublisher mappingEvent;
	
	@Autowired
	private IdentifiableRepository<Submodel> submodelRepo;
	
	/**
	 * Process 
	 * @param map
	 */
	public AssetAdministrationShell processAssetAdministrationShell(Map<String,Object> map) {
		Optional<Identifier> optId = MappingHelper.getIdentifier(map);
		if ( optId.isPresent()) {
			Identifier identifier = optId.get();
			Optional<AssetAdministrationShell> shell = aasRepo.findByIdentification(identifier);
			// when not yet registered, create the shell
			AssetAdministrationShell theShell = shell.orElse(new AssetAdministrationShell(identifier));
			// process the map
			
			mappingEvent.handleAssetAdministrationShell(org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell.createAsFacade(map), theShell);
			// save the shell 
			aasRepo.save(theShell);
			return theShell;
		}
		// does it exist?
		return null;
		
	}
	public void processSubmodel(AssetAdministrationShell aas, SubModel map) {
		Optional<Submodel> sub = aas.getSubmodel(map.getIdShort());
		if ( sub.isPresent() ) {
			Submodel subModel = sub.get();
			mappingEvent.handleSubmodel(map, subModel);
		}
		
	}
	public void processSubmodel(ISubModel map) {
		IIdentifier iId = map.getIdentification();
		
		if ( iId != null && iId.getId().length()>0 ) {
			Identifier identifier = new Identifier(iId.getId());
			Optional<Submodel> shell = submodelRepo.findByIdentification(identifier);
			if ( shell.isPresent() ) {
				
				Submodel theShell = shell.get();
				// fill in the submodel
				mappingEvent.handleSubmodel(map, theShell);
				// 
				submodelRepo.save(theShell);
			}
			else {
				throw new IllegalArgumentException("Cannot store an unregistered submodel" );
			}
		}		
		// does it exist?
	}
	public Map<String,Object> processAssetAdministrationShell(AssetAdministrationShell aas) {
		Map<String,Object> result = mappingEvent.getFromAssetAdministrationShell(aas);
		
		return result;
	}
}
