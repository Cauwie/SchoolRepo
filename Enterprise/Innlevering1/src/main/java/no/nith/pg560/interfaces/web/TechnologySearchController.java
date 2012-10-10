package no.nith.pg560.interfaces.web;

import javax.enterprise.context.Conversation;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.nith.pg560.application.TechnologyServiceBean;
import no.nith.pg560.common.JeeTechnologyPageNavigation;


@Named
@RequestScoped
public class TechnologySearchController {
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private SearchTechnologyBean searchTechnologyBean;
	
	@Inject
	private TechnologyServiceBean technologyServiceBean;
	
	@Inject
	private Export export;
	
	public String searchTechnology(){
		if (conversation.isTransient()) {
		    conversation.begin();
		}
			
		searchTechnologyBean.setSearchResults(technologyServiceBean.searchTechnologies(searchTechnologyBean.getAcronyms(), searchTechnologyBean.getVersion(), searchTechnologyBean.getJsr(), searchTechnologyBean.getDescription()));

		//conversation.end();
		return JeeTechnologyPageNavigation.INDEX_PAGE;
	}
}
