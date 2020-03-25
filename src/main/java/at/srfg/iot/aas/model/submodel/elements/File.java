package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="file_element")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class File extends DataElement<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="file_path")
	private String filePath;
	@Column(name="mime_type")
	private String mimeType;
	
	public File() {
		//
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link File} as
	 * a direct child element to the provided {@link Submodel}.
	 * @param idShort
	 * @param submodel
	 */
	public File(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	/**
	 * Convenience constructor. Creates and assigns the {@link File} as
	 * a direct child element to the provided {@link SubmodelElementCollection}.
	 * @param idShort
	 * @param collection
	 */
	public File(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return filePath;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.filePath = value;
	}
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}
	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mime_type) {
		this.mimeType = mime_type;
	}
	
	//@TODO: add value Id Reference
	

}
