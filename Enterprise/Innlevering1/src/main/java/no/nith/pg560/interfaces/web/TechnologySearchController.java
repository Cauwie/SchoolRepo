package no.nith.pg560.interfaces.web;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.nith.pg560.application.TechnologyServiceBean;


@Named
@RequestScoped
public class TechnologySearchController {
	@Inject
	private SearchTechnologyBean searchTechnologyBean;
	
	@Inject
	private TechnologyServiceBean technologyServiceBean;
	
	public void searchTechnology(){
		searchTechnologyBean.setSearchResults(
				technologyServiceBean.searchTechnologies(
						searchTechnologyBean.getSearchAcronyms(),
						searchTechnologyBean.getSearchVersion(),
						searchTechnologyBean.getSearchJsr(),
						searchTechnologyBean.getSearchDescription()));
	}
}
