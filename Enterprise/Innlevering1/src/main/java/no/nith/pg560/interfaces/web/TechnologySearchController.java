package no.nith.pg560.interfaces.web;

import javax.enterprise.context.Conversation;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.nith.pg560.application.TechnologyServiceBean;
import no.nith.pg560.common.JeeTechnologyPageNavigation;
import no.nith.pg560.domain.Technology;


@Named
@RequestScoped
public class TechnologySearchController {
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private SearchTechnologyBean searchTechnologyBean;
	
	@Inject
	TechnologyServiceBean technologyServiceBean;
	
	public String searchTechnology(){
		conversation.begin();
		searchTechnologyBean.setTechnoligies(technologyServiceBean.getTechnologyList());
		return JeeTechnologyPageNavigation.SEARCH_PAGE;
	}
}
