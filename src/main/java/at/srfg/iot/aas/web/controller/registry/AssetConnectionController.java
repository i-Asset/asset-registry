package at.srfg.iot.aas.web.controller.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.common.datamodel.asset.aas.basic.Endpoint;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.api.IAssetConnection;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;

@RestController
public class AssetConnectionController implements IAssetConnection {
	@Value("${iAsset.platformHost:}")
	private String platformHost;
	@Value("${server.port:}")
	private int port;
	
	@Autowired
	private RegistryService registry;


	private IAssetConnection getProxy(Endpoint ep) {
		return ConsumerFactory.createConsumer(ep.getAddress(),
				at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection.class);
	}
	private Optional<Endpoint> getEndpoint(Identifiable i) {
		if (i instanceof DirectoryEntry) {
			DirectoryEntry e = (DirectoryEntry)i;
			if ( e.getFirstEndpoint() != null) {
				return e.getFirstEndpoint();
			}
		}
		Endpoint ep = new Endpoint();
		ep.setAddress(platformHost);
		ep.setType("http");
		return Optional.of(ep);
	}
	@Override
	public Optional<Identifiable> getRoot(String identifier) {
		Identifier id = new Identifier(identifier);
		Optional<IdentifiableElement> identifiable =  registry.getIdentifiable(id);
		if (identifiable.isPresent()) {
			return Optional.of(identifiable.get());
		}
		// Todo: ADD EXCEPTION
		return Optional.empty();
	}

	@Override
	public List<Referable> getChildren(String identifier) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				return getProxy(ep.get()).getChildren(identifier);
			}
			// stay local
			return identifiable.getChildren();
		}
		// return empty list
		return new ArrayList<Referable>();
	}
	
	@Override
	public Optional<Referable> getModelElement(String identifier, Reference element) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			Identifiable identifiable = i.get();
			if (element.hasRoot(identifiable)) {
				Optional<Endpoint> ep = getEndpoint(identifiable);
				if ( ep.isPresent()) {
					return getProxy(ep.get()).getModelElement(identifier, element);
				}
				// stay local
				return registry.resolveReference(element);
			}
		}
		return Optional.empty();
	}

	@Override
	public void setModelElement(String identifier, Referable element) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				getProxy(ep.get()).setModelElement(identifier, element);
			}
			
		}
		// save in repo
	}

	@Override
	public void setModelElement(String identifier, String path, Referable element) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				getProxy(ep.get()).setModelElement(identifier, path, element);
			}
		}
		// save locally in repo
	}

	@Override
	public boolean removeModelElement(String identifier, Referable element) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			boolean remoteDelete = true;
			if ( ep.isPresent()) {
				remoteDelete = getProxy(ep.get()).removeModelElement(identifier, element);
			}
			if (remoteDelete) {
				return registry.deleteReferable(element);
			}
		}
		return false;
	}

	@Override
	public Optional<Referable> getElement(String identifier, String path) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();

			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				return getProxy(ep.get()).getElement(identifier, path);
			}
			return registry.resolveReference(identifier, path);
		}
		return Optional.empty();
	}

	@Override
	public List<Referable> getChildren(String identifier, String path) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				return getProxy(ep.get()).getChildren(identifier, path);
			}
			Optional<Referable> elem = registry.resolveReference(identifiable, path);
			if ( elem.isPresent()) {
				return elem.get().getChildren();
			}
		}
		return new ArrayList<Referable>();
	} 

	@Override
	public Object getValue(String identifier, String path) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				return getProxy(ep.get()).getValue(identifier, path);
			}
			Optional<Property> elem = registry.resolvePath(identifier, path, Property.class);
			if ( elem.isPresent()) {
				return elem.get().getValue();
			}
		}
		return null;
	}

	@Override
	public void setValue(String identifier, String path, Object value) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				getProxy(ep.get()).setValue(identifier, path, value);
			}
			// TODO: allow object processing as map
			Optional<Property> elem = registry.resolvePath(identifier, path, Property.class);
			if ( elem.isPresent()) {
				elem.get().setValue(value.toString());
			}
		}		
	}

	@Override
	public Map<String,Object> invokeOperation(String identifier, String path, Map<String, Object> parameterMap) {
		// check root elemment
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			
			Identifiable identifiable = i.get();
			Optional<Endpoint> ep = getEndpoint(identifiable);
			if ( ep.isPresent()) {
				return getProxy(ep.get()).invokeOperation(identifier, path, parameterMap);
			}
		}
		throw new IllegalStateException("Operation cannot be executed online");
	}
	@Override
	public Optional<Referable> getModelInstance(String identifier, Reference element) {
		// TODO Auto-generated method stub
		return null;
	}

}
