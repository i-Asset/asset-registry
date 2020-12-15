package at.srfg.iot.aas.entity.broker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Maintenance")
@ApiModel(value = "Maintenance")
public class Maintenance implements Serializable {

    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "maintenanceDate")
    private String maintenanceDate;

    @NotNull
    @ApiModelProperty(value = "maintenanceDuration")
    private String maintenanceDuration;

    @NotNull
    @ApiModelProperty(value = "maintenanceReason")
    private String maintenanceReason;

    @NotNull
    @ApiModelProperty(value = "maintenanceCostPlan")
    private String maintenanceCostPlan;

    @ApiModelProperty(value = "descriptionPyhsicalChanges")
    private String descriptionPyhsicalChanges;

    @ApiModelProperty(value = "descriptionSoftwareChanges")
    private String descriptionSoftwareChanges;

    @ApiModelProperty(value = "listOfInvolvedSuppliers")
    private String listOfInvolvedSuppliers;

    @ApiModelProperty(value = "additionalText")
    private String additionalText;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getMaintenanceDate() { return maintenanceDate; }
    public void setMaintenanceDate(String maintenanceDate) { this.maintenanceDate = maintenanceDate; }

    public String getMaintenanceDuration() { return maintenanceDuration; }
    public void setMaintenanceDuration(String maintenanceDuration) { this.maintenanceDuration = maintenanceDuration; }

    public String getMaintenanceReason() { return maintenanceReason; }
    public void setMaintenanceReason(String maintenanceReason) { this.maintenanceReason = maintenanceReason; }

    public String getMaintenanceCostPlan() { return maintenanceCostPlan; }
    public void setMaintenanceCostPlan(String maintenanceCostPlan) { this.maintenanceCostPlan = maintenanceCostPlan; }

    public String getDescriptionPyhsicalChanges() { return descriptionPyhsicalChanges; }
    public void setDescriptionPyhsicalChanges(String descriptionPyhsicalChanges) { this.descriptionPyhsicalChanges = descriptionPyhsicalChanges; }

    public String getDescriptionSoftwareChanges() { return descriptionSoftwareChanges; }
    public void setDescriptionSoftwareChanges(String descriptionSoftwareChanges) { this.descriptionSoftwareChanges = descriptionSoftwareChanges; }

    public String getListOfInvolvedSuppliers() { return listOfInvolvedSuppliers; }
    public void setListOfInvolvedSuppliers(String listOfInvolvedSuppliers) { this.listOfInvolvedSuppliers = listOfInvolvedSuppliers; }

    public String getAdditionalText() { return additionalText; }
    public void setAdditionalText(String additionalText) { this.additionalText = additionalText; }
}