package at.srfg.iot.aas.entity.broker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@ApiModel(value = "Property")
public class Property {

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

    @ApiModelProperty(value = "dataSpecification - Einheit")
    private String dataSpecification;


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
        return dataSpecification;
    }
    public void setDataSpecification(String unit) {
        this.dataSpecification = unit;
    }

}