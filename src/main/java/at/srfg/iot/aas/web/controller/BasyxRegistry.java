package at.srfg.iot.aas.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Annotated interface for Basyx Directory Operations
 * @author dglachs
 *
 */

@Api(value = "Property Indexing",
description = "Search API to perform Solr operations on indexed parties (organizations), items, item-properties, "
		+ "property-codes and classes (item categories)")

public interface BasyxRegistry {

	/**
	 * Register a new Asset
	 * @return 200 when registered
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.POST,
			path="/api/v1/registry")
	@ApiOperation("create a new AAS entry in the registry")
	public ResponseEntity<?> createRootValue(
//			@ApiParam("Identifier of the Identifiable Element")
//			@PathVariable(name = "path", required = true)
//			String path,
			@RequestBody Object value);

	/**
	 * Register a new Asset
	 * @return 200 when registered
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.POST,
			path="/api/v1/registry/{identifier:.+}")
	@ApiOperation("create a new AAS entry in the registry")
	public ResponseEntity<?> createValue(
			@ApiParam("Identifier of the Identifiable Element")
			@PathVariable(name = "identifier", required = true)
			String path,
			@RequestBody Object value);
	/**
	 * Delete an entry from the registry
	 * @return 200 when registered
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.DELETE,
			path="/api/v1/registry/{identifier:.+}")
	@ApiOperation("delete an existing AAS entry from the registry")
	public ResponseEntity<?> deleteValue(
			@ApiParam("Identifier of the Identifiable Element")
			@PathVariable(name = "identifier", required = true)
			String path);

//	@RequestMapping(
//			method = RequestMethod.GET,
//			path="/api/v1/registry/**",
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	default ResponseEntity<?> getValueSlash(HttpServletRequest request, 			
//			@ApiParam("Identifier of the Identifiable Element")
//			@PathVariable(name = "identifier", required = false)
//			String path) {
//				
//		String realPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
//		String matchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE); // /rest/**
//		realPath = new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
////		String path = request.getRequestURI().split(request.getContextPath() + "/api/v1/registry/")[1];
//		return getValue(request, path);
//	}
	/**
	 * Read a AAS descriptor from the registry
	 * @return 200 when registered
	 * @throws Exception
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			path="/api/v1/registry/{identifier:.+}")
	@ApiOperation("read an existing AAS/Submodel-Descriptor entry from the registry")
	public ResponseEntity<?> getValue(
			HttpServletRequest request,
			@ApiParam("Identifier of the Identifiable Element")
			@PathVariable(name = "identifier", required = true)
			String path);
	
}
