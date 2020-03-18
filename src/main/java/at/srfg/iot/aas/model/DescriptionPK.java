package at.srfg.iot.aas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The primary key class for the description database table.
 * 
 */
@Embeddable
public class DescriptionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="model_element_id", referencedColumnName = "model_element_id")
	private ReferableElement modelElement;
	
	@Column(name="lang", length = 2)
	private String language;

	public DescriptionPK() {
	}
	public DescriptionPK(ReferableElement element_id, String language) {
		this.modelElement = element_id;
		this.language = language;
	}

	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}


}