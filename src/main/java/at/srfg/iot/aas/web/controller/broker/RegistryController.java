package at.srfg.iot.aas.web.controller.broker;

//import eu.nimble.common.rest.identity.IdentityResolver;
import at.srfg.iot.aas.entity.broker.AssetType;
import at.srfg.iot.aas.entity.broker.AssetInstance;
import at.srfg.iot.aas.repository.broker.AssetInstanceRepo;
import at.srfg.iot.aas.repository.broker.AssetTypeRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

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

    //@Autowired
    //private IdentityResolver identityResolver;  // not yet needed

    @Autowired
    private AssetTypeRepo repoAssetType;

    @Autowired
    private AssetInstanceRepo repoAssetInstance;

    //--------------------------------------------------------------------------------------
    // getRegisteredAssetTypes
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> getRegisteredAssetTypes(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // check if request is authorized
        //String companyID = identityResolver.resolveCompanyId(bearer);
        //if (isAuthorized(channelConfiguration, companyID) == false) {
        //    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //}

        List<AssetType> allAssetTypes = repoAssetType.findAllByOrderByIdAsc();
        if (allAssetTypes == null) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Success: Get Types");
        return new ResponseEntity<>(allAssetTypes, HttpStatus.OK);
    }

    //--------------------------------------------------------------------------------------
    // registerAssetType
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> registerAssetType(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "AssetType to be added", required = true) @RequestBody AssetType type,
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // check if request is authorized
        //String companyID = identityResolver.resolveCompanyId(bearer);
        //if (isAuthorized(channelConfiguration, companyID) == false) {
        //    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //}

        repoAssetType.save(type);

        logger.info("Success: Register Type");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //--------------------------------------------------------------------------------------
    // unregisterAssetType
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> unregisterAssetType(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "TypeID to be removed", required = true) @PathVariable Long typeID,
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // check if request is authorized
        //String companyID = identityResolver.resolveCompanyId(bearer);
        //if (isAuthorized(channelConfiguration, companyID) == false) {
        //    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //}

        try {
            repoAssetType.deleteById(typeID);
        } catch (Exception ex) {
            //not able to delete due to double click for example, first delete ok, second give errors
            ex.printStackTrace();
        }

        logger.info("Success: Unregister Type");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //--------------------------------------------------------------------------------------
    // getRegisteredAssetInstances
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> getRegisteredAssetInstances(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // check if request is authorized
        //String companyID = identityResolver.resolveCompanyId(bearer);
        //if (isAuthorized(channelConfiguration, companyID) == false) {
        //    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //}

        List<AssetInstance> allAssetInstances = repoAssetInstance.findAllByOrderByIdAsc();
        if (allAssetInstances == null) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Success: Get Types");
        return new ResponseEntity<>(allAssetInstances, HttpStatus.OK);
    }

    //--------------------------------------------------------------------------------------
    // registerAssetInstance
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> registerAssetInstance(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "AssetInstance to be added", required = true) @RequestBody AssetInstance instance,
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // check if request is authorized
        //String companyID = identityResolver.resolveCompanyId(bearer);
        //if (isAuthorized(channelConfiguration, companyID) == false) {
        //    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //}

        repoAssetInstance.save(instance);

        logger.info("Success: Register Instance");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //--------------------------------------------------------------------------------------
    // unregisterAssetInstance
    //--------------------------------------------------------------------------------------
    public ResponseEntity<?> unregisterAssetInstance(
            @ApiParam(value = "registryID", required = true) @PathVariable String registryID,
            @ApiParam(value = "InstanceID to be removed", required = true) @PathVariable Long instanceID,
            @RequestHeader(value = "Authorization") String bearer) throws IOException, AuthenticationException
    {
        // check if request is authorized
        //String companyID = identityResolver.resolveCompanyId(bearer);
        //if (isAuthorized(channelConfiguration, companyID) == false) {
        //    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //}

        try {
            repoAssetInstance.deleteById(instanceID);
        } catch (Exception ex) {
            //not able to delete due to double click for example, first delete ok, second give errors
            ex.printStackTrace();
        }

        logger.info("Success: Unregister Instance");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
