package at.srfg.iot.aas.model.submodel.elements;

import at.srfg.iot.aas.model.submodel.Submodel;
/**
 * A data element is a submodel element that is not 
 * further composed out of other submodel elements.
 * <p>
 * Subclasses specifiy the data type of the element</p> 
 *
 * @param <T> The DataType of the Data Element in the
 * database, e.g. <code>String</code> for {@link Property}, <code>byte[]</code> for {@link Blob} etc.
 */
public abstract class DataElement<T> extends SubmodelElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DataElement() {
		super();
	}
	public DataElement(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}
	public DataElement(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	public abstract T getValue();
	public abstract void setValue(T value);

}
