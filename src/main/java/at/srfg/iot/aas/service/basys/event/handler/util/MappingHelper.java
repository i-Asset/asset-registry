package at.srfg.iot.aas.service.basys.event.handler.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;

import at.srfg.iot.aas.basic.Identifier;
import at.srfg.iot.aas.common.referencing.KeyElementsEnum;
import at.srfg.iot.aas.common.referencing.Kind;

public abstract class MappingHelper {
	// make the constants available
	public static final String IDSHORT = Referable.IDSHORT;
	public static final String MODELTYPE = ModelType.MODELTYPE;
	public static final String MODELTYPE_NAME = ModelType.NAME;
	public static final String KIND = HasKind.KIND;
	public static final String IDENTIFICATION = Identifiable.IDENTIFICATION;
	public static final String ADMINISTRATION = Identifiable.ADMINISTRATION;
	public static final String ID = org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier.ID;
	
	public static AssetAdministrationShell newAssetAdministrationShell() {
		return new AssetAdministrationShell();
	}
	public static Map<String,Object> getElementAsMap(Map<String,Object> map, String key) {
		try {
			@SuppressWarnings("unchecked")
			Map<String,Object> element = (Map<String, Object>) map.getOrDefault(key, new HashMap<>());
			return element;
		} catch (ClassCastException e) {
			return new HashMap<String, Object>();
		}
	}
	

	public static Collection<Map<String,Object>> getElementAsCollection(Map<String,Object> map, String ...keys) {
		if ( keys == null ) {
			throw new IllegalArgumentException("At least one key element must be provided");
		}
		if ( keys.length > 1 ) {
			String[] sub = Arrays.copyOfRange(keys, 1, keys.length);
			@SuppressWarnings("unchecked")
			Map<String,Object> sMap = (Map<String, Object>) map.get(keys[0]);
			if ( sMap != null ) {
				return getElementAsCollection(sMap, sub);
			}
			return new ArrayList<Map<String,Object>>();
		}
		else {
			return getAsCollection(map, keys[0]);

		}
	}
	public static <T> T getElementValue(Map<String,Object> map, Class<T> clazz, String ...keys) {
		if ( keys == null ) {
			throw new IllegalArgumentException("At least one key element must be provided");
		}
		if ( keys.length > 1 ) {
			String[] sub = Arrays.copyOfRange(keys, 1, keys.length);
			@SuppressWarnings("unchecked")
			Map<String,Object> sMap = (Map<String, Object>) map.get(keys[0]);
			if ( sMap != null ) {
				return getElementValue(sMap, clazz, sub);
			}
			return null;
		}
		else {
			try {
				Object element = map.getOrDefault(keys[0], new HashMap<>());
				if ( clazz.isInstance(element) ) {
					return clazz.cast(element);
				}
			} catch (ClassCastException e) {
			}
			return null;

		}
	}
	public static Set<Map.Entry<String, Object>> getEntrySet(Map<String,Object> map, String key) {
		try {
		Object o = map.get(key);
		if (o != null) {
			@SuppressWarnings("unchecked")
			Set<Map.Entry<String,Object>> o3 = ((Map<String,Object>)o).entrySet();
			return o3;
		}
		} catch (ClassCastException e) {
			// not
		}
		return new HashSet<Map.Entry<String,Object>>();

	}
	/**
	 * The element denoted by <code>key</code> is expected to be a set or list.
	 * @param map
	 * @param key
	 * @return
	 */
	public static Collection<Map<String,Object>> getAsCollection(Map<String,Object> map, String key) {
		try {
			Object o = map.get(key);
			if ( o instanceof Map) {
				Collection<Map<String,Object>> coll = new ArrayList<Map<String,Object>>();
				@SuppressWarnings("unchecked")
				Set<Map.Entry<String,Object>> o3 = ((Map<String,Object>)o).entrySet();
				Iterator<Entry<String,Object> > iter = o3.iterator();
				while (iter.hasNext()) {
					Entry<String,Object> entry = iter.next();
					if ( entry.getValue() instanceof Map) {
						@SuppressWarnings("unchecked")
						Map<String,Object> value = (Map<String,Object>)entry.getValue();
						coll.add(value);
					}
				}
				return coll;
			}
			if ( o instanceof Collection) {
				@SuppressWarnings("unchecked")
				Collection<Map<String,Object>> coll = (Collection<Map<String,Object>>)o;
				return coll;
			}
		} catch (ClassCastException e) {
			System.out.println("not a set");
		}
		// not accessible
		return new ArrayList<Map<String,Object>>();
	}
	public static <T> List<T> getAsCollection(Map<String,Object> map, Function<Map<String,Object>, T> func, String ...keys) {
		Collection<Map<String,Object>> input = getElementAsCollection(map, keys);
		if ( input != null && !input.isEmpty()) {
			return input.stream()
					.map(func)
					.collect(Collectors.toList());
		}
		return new ArrayList<T>();
	}
	/**
	 * Check whether the element is a map and wrap it in a collection.
	 * @param map
	 * @param key
	 * @return
	 */
	public static Collection<Map<String,Object>> getElementAsCollection(Map<String,Object> map, String key) {
		Collection<Map<String,Object>> elements = new ArrayList<Map<String,Object>>();
		String type = getElementValue(map, String.class, "_basyxTypes", key);
		if (type != null ) {
			switch(type) {
			case "list":
			case "set":
			default:
			}
		}
		Object o = map.get(key);
		if  (o != null && o instanceof List) {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> setOfElements = (List<Map<String,Object>>) o;
			for (Map<String,Object> descElem : setOfElements ) {
				elements.add(descElem);
			}
		}
		
		if (o != null && o instanceof Set) {
			@SuppressWarnings("unchecked")
			Set<Map<String,Object>> setOfElements = (Set<Map<String,Object>>) o;
			for (Map<String,Object> descElem : setOfElements ) {
				elements.add(descElem);
			}
			
		}
		if (o != null && o instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String,Object> singleElement = (Map<String,Object>) o;
			elements.add(singleElement);
		}
		return elements;
	}
	/**
	 * extract the ModelType element
	 * 
	 * @param map
	 * @return the {@link KeyElementsEnum} denoting the type of element
	 */
	public static KeyElementsEnum getModelType(Map<String,Object> map) {
		KeyElementsEnum modelType = KeyElementsEnum.GlobalReference;
		Map<String, Object> modelTypeMap = getElementAsMap(map, MODELTYPE);
		String name = getElementValue(modelTypeMap, String.class, MODELTYPE_NAME);
		
		try {
			modelType = KeyElementsEnum.valueOf(name);
		} catch (Exception e) {
			// Science of Stupid in Basyx
			switch (name) {
			case "blob":
				modelType = KeyElementsEnum.Blob;
				break;

			default:
				modelType = KeyElementsEnum.GlobalReference;
				break;
			}
		}
		return modelType;
	}
	
	public static String getIdShort(Map<String,Object> map) {
		return getElementValue(map, String.class, IDSHORT);
	}
	public static Kind getKind(Map<String,Object> map) {
		return null;
	}
	public static Identifier getIdentifier(Map<String,Object> map) {
		String id = getElementValue(map, String.class, IDENTIFICATION, "id");
		return new Identifier(id);
	}
	
}
