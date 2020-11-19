package at.srfg.iot.aas.web.controller.basyx;

import java.util.List;

import javax.websocket.server.PathParam;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;

public interface BasyxAPI {
	@RequestMapping(
			method = RequestMethod.GET,
			path="/aasList")
	@ApiOperation("Retrieve all (allowed) Asset Administration Shells")
	List<AssetAdministrationShell> getAASList();
	@RequestMapping(
			method = RequestMethod.GET,
			path="/aasList/{aasId}")
	@ApiOperation("Retrieve all (allowed) Asset Administration Shells")
	AssetAdministrationShell getAAS(
			@PathParam("aasId")
			String aasId);
	@RequestMapping(
			method = RequestMethod.GET,
			path="/aasList/{aasId}/aas/submodels")
	@ApiOperation("Retrieve all Submodels of the Asset Administration Shells")
	List<SubModel> getSubmodels(
			@PathParam("aasId")
			String aasId);
	@RequestMapping(
			method = RequestMethod.GET,
			path="/aasList/{aasId}/aas/submodels/{submodelId}")
	@ApiOperation("Retrieve all Submodels of the Asset Administration Shells")
	SubModel getSubmodel(
			@PathParam("aasId")
			String aasId, 
			@PathParam("submodelId")
			String submodelId);
	@RequestMapping(
			method = RequestMethod.POST,
			path="/aasList")
	@ApiOperation("Store a new Asset Administration Shell")
	void createAAS(AssetAdministrationShell aas);
	@RequestMapping(
			method = RequestMethod.POST,
			path="/aasList/{aasId}/aas/submodels")
	@ApiOperation("Store a new Asset Administration Shell")
	void createSubmodel(String aasId, AssetAdministrationShell aas);

}
