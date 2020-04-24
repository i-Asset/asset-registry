package at.srfg.iot.aas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@ApiModel(value = "AssetInstance")
public class AssetInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Name of property")
    private String name;

    @ApiModelProperty(value = "type of asset instance")
    private String assetType; // TODO: change datatype to "AssetType"

    @ApiModelProperty(value = "serialNumber")
    private String serialNumber;

    @ApiModelProperty(value = "currentLocation")
    private String currentLocation;

    @ApiModelProperty(value = "originalLocation")
    private String originalLocation;

    @ApiModelProperty(value = "listMaintenance")
    private String listMaintenance;

    @ApiModelProperty(value = "listAvailableProperties")
    private String listAvailableProperties;

    @ApiModelProperty(value = "ownerProperty")
    private String ownerProperty;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAssetType() {
        return assetType;
    }
    public void setAssetType(String type) {
        this.assetType = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String number) {
        this.serialNumber = number;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(String location) {
        this.currentLocation = location;
    }

    public String getOriginalLocation() {
        return originalLocation;
    }
    public void setOriginalLocation(String location) {
        this.originalLocation = location;
    }

    public String getListMaintenance() {
        return listMaintenance;
    }
    public void setListMaintenance(String list) {
        this.listMaintenance = list;
    }

    public String getListAvailableProperties() {
        return listAvailableProperties;
    }
    public void setListAvailableProperties(String list) {
        this.listAvailableProperties = list;
    }

    public String getOwnerProperty() {
        return ownerProperty;
    }
    public void setOwnerProperty(String owner) {
        this.ownerProperty = owner;
    }
}
