package at.srfg.iot.aas.web.controller.broker;

import at.srfg.iot.aas.entity.broker.AssetType;
import at.srfg.iot.aas.entity.broker.AssetInstance;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * REST API definition for managing asset registry
 *
 * @author Mathias Schmoigl
 */
@Api(value = "registry", description = "the registry API")
public interface RegistryAPI {

    /**
     * See API documentation
     *
     * @param registryID Identifier of requested registry.
     * @param bearer    OpenID Connect token storing requesting identity
     * @return See API documentation
     * @throws AuthenticationException Error while communication with the Identity Service
     */
    @ApiOperation(value = "Get registered AssetTypes.", response = AssetType.class,
            notes = "Returns list of registered AssetTypes sorted by ID", nickname = "getRegisteredAssetTypes", responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryID found", response = AssetType.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Error while fetching RegistryID"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "RegistryID not found") })
    @RequestMapping(value = "/{registryID}/types", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<?> getRegisteredAssetTypes(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @RequestHeader(value = "Authorization") String bearer)
            throws IOException, AuthenticationException;


    /**
     * See API documentation
     *
     * @param registryID Identifier of requested registry.
     * @param type AssetType to be added
     * @param bearer OpenID Connect token storing requesting identity
     * @return See API documentation
     * @throws AuthenticationException Error while communication with the Identity Service
     */
    @ApiOperation(value = "Register a new AssetType", response = AssetType.class,
            notes = "Register a new AssetType", nickname = "registerAssetType")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AssetType added", response = AssetType.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Error while fetching RegistryID"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "RegistryID not found"),
            @ApiResponse(code = 409, message = "AssetType already exists") })
    @RequestMapping(value = "/{registryID}/type", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<?> registerAssetType(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "AssetType to be added", required = true) @RequestBody AssetType type,
            @RequestHeader(value = "Authorization") String bearer)
            throws IOException, AuthenticationException;


    /**
     * See API documentation
     *
     * @param registryID Identifier of requested registry.
     * @param typeID  ID of AssetType to be removed
     * @param bearer    OpenID Connect token storing requesting identity
     * @return See API documentation
     * @throws AuthenticationException Error while communication with the Identity Service
     */
    @ApiOperation(value = "Unregister existing AssetType",
            notes = "Unregister existing AssetType", nickname = "unregisterAssetType")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AssetType removed", response = Object.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Error while fetching RegistryID"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "AssetType or RegistryID not found") })
    @RequestMapping(value = "/{registryID}/types/{typeID}", method = RequestMethod.DELETE)
    ResponseEntity<?> unregisterAssetType(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "TypeID to be removed", required = true)
            @PathVariable Long typeID,
            @RequestHeader(value = "Authorization") String bearer)
            throws IOException, AuthenticationException;


    /**
     * See API documentation
     *
     * @param registryID Identifier of requested registry.
     * @param bearer    OpenID Connect token storing requesting identity
     * @return See API documentation
     * @throws AuthenticationException Error while communication with the Identity Service
     */
    @ApiOperation(value = "Get registered AssetInstances.", response = AssetInstance.class,
            notes = "Returns list of registered AssetInstances sorted by ID", nickname = "getRegisteredAssetInstances", responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryID found", response = AssetInstance.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Error while fetching RegistryID"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "RegistryID not found") })
    @RequestMapping(value = "/{registryID}/instances", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<?> getRegisteredAssetInstances(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @RequestHeader(value = "Authorization") String bearer)
            throws IOException, AuthenticationException;


    /**
     * See API documentation
     *
     * @param registryID Identifier of requested registry.
     * @param instance AssetInstance to be added
     * @param bearer OpenID Connect token storing requesting identity
     * @return See API documentation
     * @throws AuthenticationException Error while communication with the Identity Service
     */
    @ApiOperation(value = "Register a new AssetInstance", response = AssetInstance.class,
            notes = "Register a new AssetInstance", nickname = "registerAssetInstance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AssetInstance added", response = AssetInstance.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Error while fetching RegistryID"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "RegistryID not found"),
            @ApiResponse(code = 409, message = "AssetInstance already exists") })
    @RequestMapping(value = "/{registryID}/instance", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<?> registerAssetInstance(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "AssetInstance to be added", required = true)
            @RequestBody AssetInstance instance,
            @RequestHeader(value = "Authorization") String bearer)
            throws IOException, AuthenticationException;


    /**
     * See API documentation
     *
     * @param registryID Identifier of requested registry.
     * @param InstanceID  ID of AssetInstance to be removed
     * @param bearer    OpenID Connect token storing requesting identity
     * @return See API documentation
     * @throws AuthenticationException Error while communication with the Identity Service
     */
    @ApiOperation(value = "Unregister existing AssetInstance",
            notes = "Unregister existing AssetInstance", nickname = "unregisterAssetInstance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AssetInstance removed", response = Object.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Error while fetching RegistryID"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "AssetInstance or RegistryID not found") })
    @RequestMapping(value = "/{registryID}/instances/{instanceID}", method = RequestMethod.DELETE)
    ResponseEntity<?> unregisterAssetInstance(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "InstanceID to be removed", required = true)
            @PathVariable Long instanceID,
            @RequestHeader(value = "Authorization") String bearer)
            throws IOException, AuthenticationException;
}
