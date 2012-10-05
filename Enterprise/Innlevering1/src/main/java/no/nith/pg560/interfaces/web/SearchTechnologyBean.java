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
	private Technology tech;
	
	@Override
	public String toString(){
		return ReflectionToStringBuilder.reflectionToString(this);
	}
	
	public void setTechnology(Technology t) {
		this.tech = t;
	}
	
	public Technology getTechnology() {
		return tech;
	}
	public List<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnoligies(List<Technology> technologies) {
		this.technologies = technologies;
	}
}
