package at.srfg.iot.aas.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="qualifiable_constraint")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "constraint_type")
public abstract class Constraint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long constraintId;
	
	@Column(name="created")
	private LocalDateTime created;
	@Column(name="modified")
	private LocalDateTime modified;
	/**
	 * Readonly attribute, stores the discriminator
	 */
	@Column(name="constraint_type", insertable = false, updatable = false)
	private String constraintType;
	/**
	 * @return the created
	 */
	public LocalDateTime getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	/**
	 * @return the modified
	 */
	public LocalDateTime getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}
	/**
	 * @return the constraintId
	 */
	public Long getConstraintId() {
		return constraintId;
	}
	/**
	 * @return the constraintType
	 */
	public String getConstraintType() {
		return constraintType;
	}

}
