package at.srfg.iot.aas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.srfg.iot.aas.model.Identifier;
import at.srfg.iot.aas.model.submodel.Submodel;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElement;
import at.srfg.iot.aas.model.submodel.elements.SubmodelElementCollection;
import at.srfg.iot.aas.repository.IdentifiableRepository;
import at.srfg.iot.aas.repository.SubmodelElementRepository;

@Service
public class SubmodelService {
	@Autowired
	private SubmodelElementRepository elementRepo;
	
	@Autowired
	private IdentifiableRepository<Submodel> submodelRepo;
	
	public Optional<Submodel> getSubmodel(Identifier identification) {
		Submodel sub = submodelRepo.findByIdentification(identification);
		return Optional.ofNullable(sub);
	}
	/**
	 * Retrieve the (direct) child elements from a {@link SubmodelElementCollection} based 
	 * on their idShort
	 * @param <T>
	 * @param The submodel containing the requested element
	 * @param clazz The type of the {@link SubmodelElement}, e.g. DataElement, Property, Operation etc.
	 * @param idShort The idShort of the requested {@link SubmodelElement}
	 * @return
	 */
	public <T extends SubmodelElement> List<T> getChildElements(Submodel model, final Class<T> clazz, String idShort) {
		List<SubmodelElement> elemList = elementRepo.findByParentElementAndIdShort(model, idShort);
		// stream API only with filled lists
		if ( elemList.size() > 0) {
			return elemList.stream()
					// check for requested type
					.filter(new Predicate<SubmodelElement>() {

						@Override
						public boolean test(SubmodelElement t) {
							if ( clazz.isInstance(t)) {
								return true;
							}
							return false;
						}
					})
					// cast to requested type 
					.map(new Function<SubmodelElement, T>() {
						@Override
						public T apply(SubmodelElement t) {
							return clazz.cast(t);
						}
					})
					.collect(Collectors.toList());
		}
		// no result - return empty list
		return new ArrayList<T>();
		
	}
	/**
	 * Retrieve the (direct) child element from a {@link Submodel} based 
	 * on it's idShort
	 * @param <T>
	 * @param model The (parent) submodel
	 * @param clazz
	 * @param idShort
	 * @return
	 */
	public <T extends SubmodelElement> Optional<T> getChildElement(Submodel model, Class<T> clazz, String idShort) {
		List<SubmodelElement> elemList = elementRepo.findByParentElementAndIdShort(model, idShort);
		if ( elemList.size() > 0) {
			return elemList.stream()
					.filter(new Predicate<SubmodelElement>() {

						@Override
						public boolean test(SubmodelElement t) {
							if ( clazz.isInstance(t)) {
								return true;
							}
							return false;
						}
					})
					.map(new Function<SubmodelElement, T>() {

						@Override
						public T apply(SubmodelElement t) {
							return clazz.cast(t);
						}
					})
					.findFirst();
		}
		return Optional.empty();
	}
	/**
	 * Retrieve the (direct) child elements from a {@link SubmodelElementCollection} based 
	 * on their idShort
	 * @param <T>
	 * @param elementCollection
	 * @param clazz
	 * @param idShort
	 * @return
	 */
	public <T extends SubmodelElement> Optional<T> getChildElement(SubmodelElementCollection elementCollection, Class<T> clazz, String idShort) {
		List<SubmodelElement> elemList = elementRepo.findByParentElementAndIdShort(elementCollection, idShort);
		if ( elemList.size() > 0) {
			return elemList.stream()
					.filter(new Predicate<SubmodelElement>() {

						@Override
						public boolean test(SubmodelElement t) {
							if ( clazz.isInstance(t)) {
								return true;
							}
							return false;
						}
					})
					.map(new Function<SubmodelElement, T>() {

						@Override
						public T apply(SubmodelElement t) {
							return clazz.cast(t);
						}
					})
					.findFirst();
		}
		return Optional.empty();
	}
	public <T extends SubmodelElement> void save(T toSave) {
		elementRepo.save(toSave);
	}
	public <T extends SubmodelElement> T getOrCreate(Submodel submodel, Class<T> clazz, String idShort) {
		return getChildElement(submodel, clazz, idShort).orElse(createChildElement(submodel, clazz, idShort));
	}
	public <T extends SubmodelElement> T getOrCreate(SubmodelElementCollection submodel, Class<T> clazz, String idShort) {
		return getChildElement(submodel, clazz, idShort).orElse(createChildElement(submodel, clazz, idShort));
	}
	public <T extends SubmodelElement> T createChildElement(Submodel submodel, Class<T> clazz, String idShort) {
		try {
			T obj = clazz.newInstance();
			obj.setIdShort(idShort);
			obj.setSubmodel(submodel);
			obj.setParentElement(submodel);
//			submodel.addChildElement(obj);
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new IllegalStateException("Child creation failed");
	}
	public <T extends SubmodelElement> T createChildElement(SubmodelElementCollection collection, Class<T> clazz, String idShort) {
		try {
			T obj = clazz.newInstance();
			obj.setIdShort(idShort);
			obj.setSubmodel(collection.getSubmodel());
			obj.setParentElement(collection);
//			obj.setSubmodelElementCollection(collection);
//			collection.addChildElement(obj);
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new IllegalStateException("Child creation failed");
	}

}
