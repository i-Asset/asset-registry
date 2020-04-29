package at.srfg.iot.aas.entity.broker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@ApiModel(value = "BinaryObject")
public class BinaryObject implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "value")
    private String value;

    @NotNull
    @ApiModelProperty(value = "mimeCode")
    private String mimeCode;

    @NotNull
    @ApiModelProperty(value = "fileName")
    private String fileName;

    @NotNull
    @ApiModelProperty(value = "uri")
    private String uri;

    @NotNull
    @ApiModelProperty(value = "languageID")
    private String languageID;

    @NotNull
    @ApiModelProperty(value = "objectMetadata")
    private String objectMetadata;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getMimeCode() { return mimeCode; }
    public void setMimeCode(String mimeCode) { this.mimeCode = mimeCode; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    public String getLanguageID() { return languageID; }
    public void setLanguageID(String languageID) { this.languageID = languageID; }

    public String getObjectMetadata() { return objectMetadata; }
    public void setObjectMetadata(String objectMetadata) { this.objectMetadata = objectMetadata; }
}
