package no.nith.pg560.interfaces.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import no.nith.pg560.domain.Technology;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Named
@ConversationScoped
public class SearchTechnologyBean implements Serializable {
	private static final long serialVersionUID = 7919694216675534863L;
	
	private List<Technology> technologies;
	private List<Technology> searchResults;
	private String acronyms;
	private String version;
	private String jsr;
	private String description;
	
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

	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}

	@Override
	public String toString(){
		return ReflectionToStringBuilder.reflectionToString(this);
	}
	
	public List<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnoligies(List<Technology> technologies) {
		this.technologies = technologies;
	}
	
	public void setSearchResults(List<Technology> searchResults) {
		this.searchResults = searchResults;
	}

	public List<Technology> getSearchResults() {
		return searchResults;
	}
}
