package at.srfg.iot.aas.web.controller;

import at.srfg.iot.aas.entity.AssetType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * REST Controller for managing asset registry
 *
 * @author Mathias Schmoigl
 */
@Controller
@RequestMapping(path = "/registry")
@Api("Asset Registry API")
public class RegistryController implements RegistryAPI {

    private static Logger logger = LoggerFactory.getLogger(RegistryController.class);

    //--------------------------------------------------------------------------------------
    // getRegisteredAssetTypes
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> getRegisteredAssetTypes(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(name = "Authorization", value = "OpenID Connect token containing identity of requester", required = true)
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // TODO
        logger.info("not yet implemented");
        return ResponseEntity.notFound().build();
    }

    //--------------------------------------------------------------------------------------
    // registerAssetType
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> registerAssetType(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "AssetType to be added", required = true) @RequestBody AssetType type,
            @ApiParam(name = "Authorization", value = "OpenID Connect token containing identity of requester", required = true)
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // TODO
        logger.info("not yet implemented");
        return ResponseEntity.notFound().build();
    }

    //--------------------------------------------------------------------------------------
    // unregisterAssetType
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> unregisterAssetType(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "TypeID to be removed", required = true) @PathVariable Long typeID,
            @ApiParam(name = "Authorization", value = "OpenID Connect token containing identity of requester", required = true)
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // TODO
        logger.info("not yet implemented");
        return ResponseEntity.notFound().build();
    }

}
