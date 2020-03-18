package at.srfg.iot.aas.model.submodel.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.srfg.iot.aas.model.ReferableElement;
import at.srfg.iot.aas.model.submodel.Submodel;

@Entity
@Table(name="submodel_element_collection")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="model_element_id")
public class SubmodelElementCollection extends SubmodelElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="ordered")
	private Boolean ordered = false;
	@Column(name="allow_duplicates")
	private Boolean allowDuplicates = false;
	/**
	 * Default constructor
	 */
	public SubmodelElementCollection() {
		
	}
	/**
	 * Create a {@link SubmodelElementCollection} with a {@link Submodel}
	 * as it's parent element.
	 * @param idShort The idShort identifier
	 * @param submodel The parent element
	 */
	public SubmodelElementCollection(String idShort, Submodel submodel) {
		super(idShort, submodel);
	}
	/**
	 * Create a {@link SubmodelElementCollection} with another {@link SubmodelElementCollection}
	 * as it's parent element.
	 * @param idShort The idShort identifier
	 * @param submodel The parent element
	 */
	public SubmodelElementCollection(String idShort, SubmodelElementCollection collection) {
		super(idShort, collection);
	}
	/**
	 * @return the submodelElements
	 */
	public Collection<SubmodelElement> getSubmodelElements() {
		if ( getChildren().size()>0) {
			return getChildren().stream()
					.filter(new Predicate<ReferableElement>() {
	
						@Override
						public boolean test(ReferableElement t) {
							return t instanceof SubmodelElement;
						}
					})
					.map(new Function<ReferableElement, SubmodelElement>() {
	
						@Override
						public SubmodelElement apply(ReferableElement t) {
							return SubmodelElement.class.cast(t);
						}
						
					})
					.collect(Collectors.toList());
		}
		return new ArrayList<SubmodelElement>();
	}

}
