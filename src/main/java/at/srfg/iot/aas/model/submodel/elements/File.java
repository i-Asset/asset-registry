package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
	private String mime_type;

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
	 * @return the mime_type
	 */
	public String getMime_type() {
		return mime_type;
	}
	/**
	 * @param mime_type the mime_type to set
	 */
	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}
	
	//@TODO: add value Id Reference
	

}
