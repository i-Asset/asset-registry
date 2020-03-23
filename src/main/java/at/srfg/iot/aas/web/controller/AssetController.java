package at.srfg.iot.aas.web.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.srfg.iot.aas.model.AssetAdministrationShell;
import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.service.AssetService;
import at.srfg.iot.aas.service.BoschRexRoth;
import at.srfg.iot.aas.service.SubmodelService;

@RestController
@Api(value = "Asset Controller",
		description = "API to perform Asset registry operations")
public class AssetController {
	@Autowired
	BoschRexRoth rexRoth;
	@Autowired
	AssetService assetService;
	@Autowired
	SubmodelService submodelService;
	/**
	 * Retrieve a full Asset Administration Shell 
	 * @param uri
	 * @return
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GetMapping("/asset")
	public ResponseEntity<?> getAsset(@RequestParam String uri) {
		return ResponseEntity.of(assetService.getAssetAdministrationShell(new Identifier(uri)));
	}
	
	@Produces(MediaType.APPLICATION_XML)
	@GetMapping("/aas")
	public ResponseEntity<?> getAAS(@RequestParam String uri) {
		return ResponseEntity.of(rexRoth.createAsset());
	}
	@Consumes(MediaType.APPLICATION_JSON)
	@PostMapping("/aas")
	public ResponseEntity<?> postAAS(@RequestBody AssetAdministrationShell aas) {
		return null;
	}
	@Produces(MediaType.APPLICATION_JSON)
	@GetMapping("/aas/submodel")
	public ResponseEntity<?> getSubmodel(@RequestParam String id) {
		return ResponseEntity.of(submodelService.getSubmodel(new Identifier(id)));
		
	}
	@Consumes(MediaType.APPLICATION_JSON)
	@PostMapping("/aas/submodel")
	public ResponseEntity<?> postSubmodel(@RequestBody Submodel submodel) {
		return null;
	}
}
