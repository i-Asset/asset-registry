package at.srfg.iot.aas.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import at.srfg.iot.aas.api.Referable;
/**
 * Abstract base class for all model elements
 * @author dglachs
 *
 */
@JsonInclude(value = Include.NON_EMPTY)
@Entity
@Table(name="referable_element")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="model_type")
public abstract class ReferableElement implements Referable, Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * The Primary Key for each {@link ReferableElement}
	 */
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="model_element_id")
	private Long elementId;
	/**
	 * Optional categorization for each {@link ReferableElement}
	 */
	@Column(name="category", length=100)
	private String category;
	/**
	 * Local Identifier - see {@link Referable#getIdShort()} 
	 */
	@Column(name="id_short", length = 100)
	private String idShort;
	/**
	 * Element storing the creation date of the element
	 */
	@JsonIgnore
	@Column(name="created")
	private LocalDateTime created;
	/**
	 * Element holding the last modification date
	 */
	@JsonIgnore
	@Column(name="modified")
	private LocalDateTime modified;
	/**
	 * Element storing the foreign key to the parent element
	 */
	@JsonIgnore
	@XmlTransient
	@ManyToOne
	@JoinColumn(name="parent_element_id", referencedColumnName = "model_element_id")
	private ReferableElement parentElement;
	/**
	 * Navigable collection, returns all children of the current element
	 */
	@JsonIgnore
	@XmlTransient
	@OneToMany(mappedBy = "parentElement", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<ReferableElement> childElements;
	/**
	 * Readonly attribute, stores the {@link KeyElementsEnum}
	 * @see #getModelType()
	 */
	@Column(name="model_type", insertable = false, updatable = false)
	private String modelType;
	/**
	 * Map of {@link Description} elements, one entry per language
	 */
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "id.modelElement", fetch=FetchType.EAGER)
	@MapKey(name="id.language")
	private Map<String, Description> descriptionMap = new HashMap<String, Description>(3);
	/**
	 * Getter for the element identifier
	 * @return
	 */
	public Long getElementId() {
		return elementId;
	}
	/**
	 * Setter for the element identifier
	 * @param elementId
	 */
	protected void setElementId(Long elementId) {
		this.elementId = elementId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIdShort() {
		return idShort;
	}
	public void setIdShort(String idShort) {
		this.idShort = idShort;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getModified() {
		return modified;
	}
	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}
	@JsonIgnore
	/**
	 * Getter for the parent element - used to navigate in the model
	 */
	public ReferableElement getParentElement() {
		return parentElement;
	}
	/**
	 * Setter for the parent element
	 * @param parent The parent {@link ReferableElement} element
	 */
	public void setParentElement(ReferableElement parent) {
		// keep track of parent/child relationship
		this.parentElement = parent;
		//parent.addChild(this);
	}
	public final KeyElementsEnum getModelType() {
		try {
			return KeyElementsEnum.valueOf(modelType);
		} catch (Exception e) {
			return KeyElementsEnum.GlobalReference;
		}
	}
	/**
	 * Helper method storing a language dependent description or
	 * label for the element
	 * @param language The ISO language code 
	 * @param description
	 */
	public void setDescription(String language, String description) {
		if ( language != null) {
			Description desc = descriptionMap.get(language);
			if ( desc != null ) {
				desc.setDescription(description);
			}
			else {
				this.descriptionMap.put(language, new Description(this, language, description));
			}
		}
	}
	/**
	 * Helper method providing access to the element's {@link Description} by language
	 * @param language the ISO Language code, e.g. de, en 
	 * @return the description
	 */
	public Description getDescription(String language) {
		return this.descriptionMap.get(language);
	}
	/**
	 * Getter for the description
	 * @return
	 */
	public Collection<Description> getDescription() {
		if (this.descriptionMap == null) {
			return null;
		}
		return this.descriptionMap.values();
	}
	/**
	 * Helper method to maintain the 
	 * @param child
	 */
	protected void addChild(ReferableElement child) {
		// keep track of the parent/child relationship
		child.setParentElement(this);
		// maintain the childElements collection
		if (this.childElements == null) {
			this.childElements = new ArrayList<ReferableElement>();
		}
		this.childElements.add(child);
	}
	/**
	 * 
	 * @return the list of direct children
	 */
	protected List<ReferableElement> getChildren() {
		if (this.childElements == null) {
			this.childElements = new ArrayList<ReferableElement>();
		}
		return this.childElements;
	}
	/**
	 * set created and modified date on creation
	 */
	@PrePersist
	private void prePersist() {
		setCreated(LocalDateTime.now());
		setModified(LocalDateTime.now());
	}
	/**
	 * set modified date to new value on update
	 */
	@PreUpdate
	private void preUpdate() {
//		setCreated(LocalDateTime.now());
		setModified(LocalDateTime.now());
	}
	
	public String toString() {
		if (this instanceof IdentifiableElement) {
			return getModelType().name() + ": " + ((IdentifiableElement)this).getId();
		}
		return getModelType().name() + ": " + idShort; 
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + ((idShort == null) ? 0 : idShort.hashCode());
		result = prime * result + ((modelType == null) ? 0 : modelType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReferableElement other = (ReferableElement) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		if (idShort == null) {
			if (other.idShort != null)
				return false;
		} else if (!idShort.equals(other.idShort))
			return false;
		if (modelType == null) {
			if (other.modelType != null)
				return false;
		} else if (!modelType.equals(other.modelType))
			return false;
		return true;
	}

}
