package no.nith.pg560.interfaces.web;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import no.nith.pg560.application.TechnologyServiceBean;

/**
 * 
 * @author David Emanuelsen and Alexander Hill
 * Class to function as a back-end connection with html.
 * 
 */

@Named
@RequestScoped
public class TechnologySearchController {
	private SearchTechnologyBean searchTechnologyBean;
	private TechnologyServiceBean technologyServiceBean;
	
	public void searchTechnology() {
		searchTechnologyBean.setSearchResults(
			technologyServiceBean.searchTechnologies(
					searchTechnologyBean.getSearchAcronyms(),
					searchTechnologyBean.getSearchVersion(),
					searchTechnologyBean.getSearchJsr(),
					searchTechnologyBean.getSearchDescription()));
	}
	
	@Inject
	public void setSearchTechnologyBean(SearchTechnologyBean stb) {
		this.searchTechnologyBean = stb;
	}
	
	@Inject
	public void setTechnologyServiceBean(TechnologyServiceBean tsb) {
		this.technologyServiceBean = tsb;
	}
}
