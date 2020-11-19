package at.srfg.iot.aas.web.controller.basyx;

import java.util.List;

import javax.websocket.server.PathParam;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public interface BasyxRegistryAPI {
	
	/**
	 * Register AAS descriptor in registry, delete old registration 
	 */
	@RequestMapping(
			method = RequestMethod.PUT,
			path="/api/v1/registry/{aasId}")
	@ApiOperation("Register a new AAS")
	public void register(
			@PathParam(value="aasId")
			String aas,
			@RequestBody
			AASDescriptor deviceAASDescriptor) throws ProviderException;

	/**
	 * Register SM descriptor in registry, delete old registration
	 */
	@RequestMapping(
			method = RequestMethod.PUT,
			path="/api/v1/registry/{aasId}/submodels/{submodelId}")
	@ApiOperation("Register a new AAS")
	public void register(
			@PathParam(value = "aasId")
			@ApiParam("The unique AAS identifier")
			String aasId,
			@PathParam(value = "submodelId")
			@ApiParam("The unique Submodel identifier")
			String submodelId,
			@RequestBody
			@ApiParam("The SubmodelDescriptor to add/update with the AAS")
			SubmodelDescriptor smDescriptor) throws ProviderException;

	/**
	 * Delete AAS descriptor from registry
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/api/v1/registry/{aasId}")
	@ApiOperation("Delete a complete AAS")
	public void delete(String aasId) throws ProviderException;
	
	/**
	 * Delete SM descriptor from registry
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/api/v1/registry/{aasId}/submodels/{submodelId}")
	@ApiOperation("Delete a Submodel from the provided AAS")
	public void delete(
			@PathParam("aasId")
			String aasId, 
			@PathParam("submodelId")
			String smId) throws ProviderException;
	
	/**
	 * Lookup AAS
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/api/v1/registry/{aasId}")
	@ApiOperation("Lookup an existing AAS")
	public AASDescriptor lookupAAS(
			@PathParam("aasId")
			String aasId) throws ProviderException;

	/**
	 * Retrieve all registered AAS
	 * 
	 * @return
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/api/v1/registry")
	@ApiOperation("Retrieve all registered AAS elements")
	public List<AASDescriptor> lookupAll() throws ProviderException;

	/**
	 * Retrieves all SubmodelDescriptors of submodels of an AAS
	 * 
	 * @param aasId
	 *            of the AAS
	 * @return list of SubmodelDescriptors
	 * @throws ProviderException
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/api/v1/registry/{aasId}/submodels")
	@ApiOperation("Retrieve all SubmodelDescriptors for a aas")
	public List<SubmodelDescriptor> lookupSubmodels(
			@PathParam("aasId")
			String aasId) throws ProviderException;

	/**
	 * Retrieves the SubmodelDescriptor of a specific submodel of an AAS
	 * 
	 * @param aasId
	 *            of the AAS
	 * @param smId
	 *            of the Submodel
	 * @return the SubmodelDescriptor
	 * @throws ProviderException
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/api/v1/registry/{aasId}/submodels/{submodelId}")
	@ApiOperation("Register a new AAS")
	public SubmodelDescriptor lookupSubmodel(
			@PathParam("aasId")
			String aasId, 
			@PathParam("submodelId")
			String smId) throws ProviderException;
	
	

}
