package at.srfg.iot.aas.entity.broker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@ApiModel(value = "AssetType")
public class AssetType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Name of property")
    private String name;

    @ApiModelProperty(value = "shortID - Kürzel")
    private String shortID;

    @ApiModelProperty(value = "semanticID - eclass number")
    private String semanticID;

    @ApiModelProperty(value = "Description of property")
    private String description;

    @ApiModelProperty(value = "certificate")
    private String certificate;

    @ApiModelProperty(value = "properties")
    private Property[] properties;


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

    public String getShortID() {
        return shortID;
    }
    public void setShortID(String id) {
        this.shortID = id;
    }

    public String getSemanticID() {
        return semanticID;
    }
    public void setSemanticID(String id) {
        this.semanticID = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataSpecification() {
        return certificate;
    }
    public void setDataSpecification(String cert) {
        this.certificate = cert;
    }

    public Property[] getProperties() {
        return properties;
    }
    public void setProperties(Property[] props) {
        this.properties = props;
    }
}
