package at.srfg.iot.aas.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import at.srfg.iot.aas.api.HasSemantics;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Constraint")
public class Qualifier extends Constraint implements HasSemantics {

	@Column(name="qualifier_type")
	private String qualifierType;

	@Column(name="qualifier_value")
	private String qualifierValue;
	
	@ManyToOne
	@JoinColumn(name="qualifier_value_id")
	private ReferableElement qualifierValueId;
	
	@ManyToOne
	@JoinColumn(name = "semantic_id")
	private ReferableElement semanticId;

	public ReferableElement getSemanticElement() {
		return semanticId;
	}

	public void setSemanticElement(ReferableElement semanticId) {
		this.semanticId = semanticId;
	}

	@Override
	public Identifier getSemanticIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setSemanticIdentifier(Identifier identifier) {
//		this.semanticIdentification = identifier;
		
	}

}
