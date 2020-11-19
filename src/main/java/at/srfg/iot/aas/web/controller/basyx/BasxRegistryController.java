package at.srfg.iot.aas.web.controller.basyx;

import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.srfg.iot.aas.common.referencing.IdType;

//@RestController
//@RequestMapping(path = "/b")
public class BasxRegistryController implements BasyxRegistryAPI {
	private Logger logger = LoggerFactory.getLogger(BasxRegistryController.class);

	@Override
	public void register(String aas, AASDescriptor deviceAASDescriptor) throws ProviderException {
		logger.debug("Registering a new AAS {}", aas);
		
	}

	@Override
	public void register(String aasId, String submodelId, SubmodelDescriptor smDescriptor) throws ProviderException {
		logger.debug("Registering a new Submodel {} within AAS {}", submodelId, aasId);
		
	}

	@Override
	public void delete(String aasId) throws ProviderException {
		logger.debug("Deleting AAS {}", aasId);
		
	}

	@Override
	public void delete(String aasId, String smId) throws ProviderException {
		logger.debug("Deleting Submodel {} of AAS {}", smId, aasId);
		
	}

	@Override
	public AASDescriptor lookupAAS(String aasId) throws ProviderException {
		logger.debug("Searching for AAS {}", aasId);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AASDescriptor> lookupAll() throws ProviderException {
		logger.debug("Searching for all AAS - Security settings must apply");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubmodelDescriptor> lookupSubmodels(String aasId) throws ProviderException {
		logger.debug("Searching Submodels within AAS {}", aasId);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubmodelDescriptor lookupSubmodel(String aasId, String smId) throws ProviderException {
		logger.debug("Searching Submodel {} within AAS {}", smId, aasId);
		IdType aasIdType = IdType.valueOf(aasId);
		IdType submodelIdType = IdType.valueOf(smId);
		IIdentifier aasIdentifier = new Identifier(IdentifierType.fromString(aasIdType.name()), aasId);
		IIdentifier submodelIdentifier = new Identifier(IdentifierType.fromString(submodelIdType.name()), smId);
		// TODO Auto-generated method stub
		return null;
	}

	private IIdentifier fromString(String provided) {
		IdType idType = IdType.getType(provided);
		switch (idType) {
		case IRDI: 
			return new Identifier(IdentifierType.IRDI, provided);
		case IRI:
			return new Identifier(IdentifierType.IRI, provided);
		case UUID:
		case IdShort:
		case Custom:
		default:
			return new Identifier(IdentifierType.CUSTOM, provided);
		}
	}
}
