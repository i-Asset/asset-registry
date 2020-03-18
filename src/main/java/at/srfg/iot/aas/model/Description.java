package at.srfg.iot.aas.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the description database table.
 * 
 */
@Entity
public class Description implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private DescriptionPK id;

	private String description;
	

	public Description() {
	}
	protected Description(ReferableElement modelElement, String language, String description) {
		this.id = new DescriptionPK(modelElement, language);
		this.description = description;
	}


	public String getLanguage() {
		return id.getLanguage();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}