package at.srfg.iot.aas.service.indexing;

import at.srfg.iot.common.datamodel.asset.aas.basic.Submodel;

public class SubmodelTypeIndexingObject extends IndexingEvent<Submodel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubmodelTypeIndexingObject(Object source, Submodel identifiable) {
		super(source, identifiable);
	}

}
