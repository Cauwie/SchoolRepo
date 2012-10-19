package no.nith.pg560.interfaces.web;

import no.nith.pg560.application.TechnologyServiceBean;
import no.nith.pg560.common.CommonInfrastructureIT;
import no.nith.pg560.domain.Technology;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TechnologySearchControllerTest extends CommonInfrastructureIT {
	
	private TechnologySearchController technologySearchController;
	
	@Mock
	private TechnologyServiceBean technologyServiceBean;
	
	
	private SearchTechnologyBean searchTechnologyBean;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		technologySearchController = new TechnologySearchController();
		searchTechnologyBean = new SearchTechnologyBean();
		technologySearchController.setSearchTechnologyBean(searchTechnologyBean);
		technologySearchController.setTechnologyServiceBean(technologyServiceBean);
		when(technologyServiceBean.searchTechnologies(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(buildTechnologies());
	}
	
	@Test
	public void testSearchTechnologyController() {
		technologySearchController.searchTechnology();
		Technology technology = searchTechnologyBean.getSearchResults().get(0);
		Technology technology2 = buildTechnologies().get(0);
		
		assertEquals(technology.getAcronyms(), technology2.getAcronyms());
		assertEquals(technology.getVersion(), technology2.getVersion());
		assertEquals(technology.getJsr(), technology2.getJsr());
		assertEquals(technology.getDescription(), technology2.getDescription());
	}
}
