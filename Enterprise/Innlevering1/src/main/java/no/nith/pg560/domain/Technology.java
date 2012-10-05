package no.nith.pg560.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Domain object exposing the PG560_JEE_TECHNOLOGY database. 
 */
@Entity
@Table(name = "PG560_JEE_TECHNOLOGY")
public class Technology {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String acronyms;
	private String version;
	private String jsr;
	private String description;
	
	public Technology() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAcronyms() {
		return acronyms;
	}

	public void setAcronyms(String acronyms) {
		this.acronyms = acronyms;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}