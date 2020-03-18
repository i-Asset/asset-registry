package at.srfg.iot.aas.model.submodel.elements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="blob_element")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "model_element_id")
public class Blob extends DataElement<byte[]> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="blob_value")
	private byte[] byteValue;
	@Column(name="mime_type")
	private String mimeType;

	/**
	 * @return the value
	 */
	public byte[] getValue() {
		return byteValue;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(byte[] value) {
		this.byteValue = value;
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
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	//@TODO: add value Id Reference
	

}
