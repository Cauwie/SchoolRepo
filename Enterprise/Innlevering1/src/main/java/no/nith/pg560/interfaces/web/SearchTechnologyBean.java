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
	
	private List<Technology> searchResults;
	private String searchAcronyms;
	private String searchVersion;
	private String searchJsr;
	private String searchDescription;
	
	public String getSearchAcronyms() {
		return searchAcronyms;
	}

	public void setSearchAcronyms(String searchAcronyms) {
		this.searchAcronyms = searchAcronyms;
	}

	public String getSearchVersion() {
		return searchVersion;
	}

	public void setSearchVersion(String searchVersion) {
		this.searchVersion = searchVersion;
	}

	public String getSearchJsr() {
		return searchJsr;
	}

	public void setSearchJsr(String searchJsr) {
		this.searchJsr = searchJsr;
	}

	public String getSearchDescription() {
		return searchDescription;
	}

	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setSearchResults(List<Technology> searchResults) {
		this.searchResults = searchResults;
	}

	public List<Technology> getSearchResults() {
		return searchResults;
	}

	@Override
	public String toString(){
		return ReflectionToStringBuilder.reflectionToString(this);
	}
}
