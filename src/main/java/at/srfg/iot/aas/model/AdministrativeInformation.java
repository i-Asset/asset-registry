package at.srfg.iot.aas.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AdministrativeInformation {

	@Column(name = "revision", length = 20)
	private String revision;
	@Column(name = "version", length = 20)
	private String version;

	public AdministrativeInformation() {
		super();
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}