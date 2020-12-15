package at.srfg.iot.aas.entity.broker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "TMP_AssetInstance")
@ApiModel(value = "AssetInstance")
public class AssetInstance implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Name of asset instance")
    private String name;

    @ApiModelProperty(value = "type of asset instance")
    private String assetType;

    @ApiModelProperty(value = "serialNumber")
    private String serialNumber;

    @ApiModelProperty(value = "currentLocation")
    private String currentLocation;

    @ApiModelProperty(value = "originalLocation")
    private String originalLocation;

    @ApiModelProperty(value = "listMaintenance")
    private Maintenance[] listMaintenance;

    @ApiModelProperty(value = "dataEndpoint")
    private String dataEndpoint;

    @ApiModelProperty(value = "ownerProperty")
    private String ownerProperty;

    @ApiModelProperty(value = "assetImages")
    private BinaryObject[] assetImages;


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

    public Maintenance[] getListMaintenance() {
        return listMaintenance;
    }
    public void setListMaintenance(Maintenance[] list) {
        this.listMaintenance = list;
    }

    public String getDataEndpoint() {
        return dataEndpoint;
    }
    public void setDataEndpoint(String endpoint) {
        this.dataEndpoint = endpoint;
    }

    public String getOwnerProperty() {
        return ownerProperty;
    }
    public void setOwnerProperty(String owner) {
        this.ownerProperty = owner;
    }

    public BinaryObject[] getAssetImages() {
        return assetImages;
    }
    public void setAssetImages(BinaryObject[] images) {
        this.assetImages = images;
    }
}
