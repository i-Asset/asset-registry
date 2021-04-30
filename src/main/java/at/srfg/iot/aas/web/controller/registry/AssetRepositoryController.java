package at.srfg.iot.aas.web.controller.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import at.srfg.iot.aas.service.registry.RegistryService;
import at.srfg.iot.aas.service.registry.RegistryWorker;
import at.srfg.iot.common.datamodel.asset.aas.basic.Asset;
import at.srfg.iot.common.datamodel.asset.aas.basic.AssetAdministrationShell;
import at.srfg.iot.common.datamodel.asset.aas.basic.Endpoint;
import at.srfg.iot.common.datamodel.asset.aas.basic.Identifier;
import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;
import at.srfg.iot.common.datamodel.asset.aas.common.DirectoryEntry;
import at.srfg.iot.common.datamodel.asset.aas.common.HasKind;
import at.srfg.iot.common.datamodel.asset.aas.common.Identifiable;
import at.srfg.iot.common.datamodel.asset.aas.common.Referable;
import at.srfg.iot.common.datamodel.asset.aas.common.SubmodelElementContainer;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.IdentifiableElement;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Kind;
import at.srfg.iot.common.datamodel.asset.aas.common.referencing.Reference;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.DataElement;
import at.srfg.iot.common.datamodel.asset.aas.modeling.submodelelement.Property;
import at.srfg.iot.common.datamodel.asset.api.IAssetConnection;
import at.srfg.iot.common.datamodel.asset.connectivity.rest.ConsumerFactory;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "repository")
public class AssetRepositoryController implements IAssetConnection{
	@Value("${iAsset.platformHost:}")
	private String platformHost;
	@Value("${server.port:}")
	private int port;
	
	@Autowired
	private RegistryService registry;

	@Autowired
	private RegistryWorker worker;
	

	private IAssetConnection getProxy(Endpoint ep) {
		return ConsumerFactory.createConsumer(ep.getAddress(),
				at.srfg.iot.common.datamodel.asset.connectivity.IAssetConnection.class);
	}
	/**
	 * Verify whether a given root element is of {@link Kind#Instance}.
	 * Only instances may have endpoints! 
	 * @param identifiable
	 * @return
	 */
	private boolean isAssetInstance(Identifiable identifiable) {
		switch(identifiable.getModelType()) {
		case AssetAdministrationShell:
			AssetAdministrationShell shell = (AssetAdministrationShell) identifiable;
			if ( shell.getAsset() != null) {
				return shell.getAsset().getKind().equals(Kind.Instance);
			}
			// when no direct asset assigned, then 
			return new Asset().getKind().equals(Kind.Instance);
		case Submodel:
			Submodel sub = (Submodel) identifiable;
			return sub.getKind().equals(Kind.Instance);
		default:
			return false;
		}
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
		Optional<IdentifiableElement> identifiable = registry.getIdentifiable(id);
		return Optional.ofNullable(identifiable.orElse(null));
//		if (identifiable.isPresent()) {
//			return Optional.
//		}
//		// Todo: ADD EXCEPTION
//		return null;
	}

	@Override
	public List<Referable> getChildren(String identifier) {
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			return i.get().getChildren();
		}
		return new ArrayList<Referable>();
	}
	
	@Override
	public Optional<Referable> getModelElement(String identifier, Reference element) {
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			if ( element.hasRoot(i.get())) {
				return registry.resolveReference(element);
			}
		}
		return Optional.empty();
	}
	@Override
	public Optional<Referable> getModelInstance(String identifier, Reference element) {
//		Optional<Identifiable> i = getRoot(identifier);
//		if (i.isPresent()) {
//			if ( element.hasRoot(i.get())) {
//				Optional<Referable> resolved = registry.resolveReference(element);
//				return registry.ensureInstance(resolved);
//			}
//		}
		return Optional.empty();
	}

	@Override
	public void setModelElement(String identifier, Referable element) {
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			worker.setElement(i.get(), element);
			if (isAssetInstance(i.get())) {
				// try to add the element to the remote instance
			}
		}
	}

	@Override
	public void setModelElement(String identifier, String path, Referable element) {
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			worker.setElement(i.get(), path, element);
			// save locally in repo
			if ( isAssetInstance(i.get())) {
				
			}
		}
	}

	@Override
	public boolean removeModelElement(String identifier, Referable element) {
		// perform the checks
		Optional<Identifiable> i = getRoot(identifier);
		if (i.isPresent()) {
			boolean remoteDelete = true;
			// it is already a a reference
			if ( Reference.class.isInstance(element)) {
				remoteDelete = registry.deleteReferable(Reference.class.cast(element));
			}
			else {
				// the element must hold the reference to it's parent!
				remoteDelete = registry.deleteReferable(element);
				
			}
			if ( isAssetInstance(i.get())) {
				// decide what to do with the remote stuff
			}
			if (remoteDelete) {
			}
			
		}
		return false;
	}
	@RequestMapping(
			method = RequestMethod.GET,
			path = "element/{path}/**"
			)
	public Optional<Referable> getElement(
			@ApiParam("The identifier of the Asset Administration Shell or Submodel!")
			@RequestHeader(name=ASSET_ID_HEADER)
			String identifier,
			@ApiParam("The path to the container")
			@PathVariable(required = false, name="path")
			String path,
			HttpServletRequest request) {
		String myPath = extractPathFromPattern(request);
		return getElement(identifier, myPath);
	}
	public static String extractPathFromPattern(final HttpServletRequest request){


	    String path = (String) request.getAttribute(
	            HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	    String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

	    AntPathMatcher apm = new AntPathMatcher();
	    String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

	    return finalPath;

	}
	@Override
	public Optional<Referable> getElement(String identifier, String path) {
		return registry.resolvePath(identifier, path);
	}

	@Override
	public List<Referable> getChildren(String identifier, String path) {
		Optional<Referable> element = getElement(identifier,path);
		if ( element.isPresent()) {
			return element.get().getChildren();
		}
		return new ArrayList<Referable>();
	} 

	@Override
	public Object getValue(String identifier, String path) {
		Optional<Referable> referable = registry.resolvePath(identifier, path);
		if ( referable.isPresent()) {
			if ( referable.isPresent()) {
				Referable ref = referable.get();
				if ( SubmodelElementContainer.class.isInstance(ref)) {
					return SubmodelElementContainer.class.cast(ref).getValue();
				}
				if ( DataElement.class.isInstance(ref)) {
					return DataElement.class.cast(ref).getValue();
				}
			}
		}
		Optional<Property> property = registry.resolvePath(identifier, path, Property.class);
		if ( property.isPresent()) {
			return  property.get().getValue();
		}
		return null;
	}

	@Override
	public void setValue(String identifier, String path, Object value) {
		Optional<Property> property = registry.resolvePath(identifier, path, Property.class);
		if ( property.isPresent()) {
			property.get().setValue(value.toString());
		}
	}

	@Override
	public Map<String,Object> invokeOperation(String identifier, String path, Map<String, Object> parameterMap) {
		Optional<Identifiable>  root = getRoot(identifier);
		if ( root.isPresent() ) {
			Optional<Endpoint> ep = getEndpoint(root.get());
			if ( ep.isPresent()) {
				//
				return getProxy(ep.get()).invokeOperation(identifier, path, parameterMap);
			}
			
		}
		throw new IllegalStateException("Operation cannot be executed online");
	}

}
