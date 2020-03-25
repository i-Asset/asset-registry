package at.srfg.iot.aas.basys.event;

import java.util.Map;

public abstract class SetElementEvent<T>  implements SetElement {
	
	private final T local;
	private final Map<String,Object> mapElement;


	public SetElementEvent(Map<String,Object> map, T localEntity) {
		this.mapElement = map;
		this.local = localEntity;
	}


	/**
	 * @return the local
	 */
	public T getLocal() {
		return local;
	}

	/**
	 * @return the mapElement
	 */
	public Map<String, Object> getBasyxMap() {
		return mapElement;
	}
	
	public boolean isRead() {
		return false;
	}

}
