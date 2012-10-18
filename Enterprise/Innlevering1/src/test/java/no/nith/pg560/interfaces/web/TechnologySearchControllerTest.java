package no.nith.pg560.interfaces.web;

import java.util.ArrayList;
import java.util.List;

import no.nith.pg560.application.TechnologyServiceBean;
import no.nith.pg560.domain.Technology;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TechnologySearchControllerTest {
	
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
		when(technologyServiceBean.searchTechnologies(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(buildTechnologyList());
	}
	
	@Test
	public void testSearchTechnologyController() {
		technologySearchController.searchTechnology();
		Technology technology = searchTechnologyBean.getSearchResults().get(0);
		Technology technology2 = buildTechnologyList().get(0);
		
		assertEquals(technology.getAcronyms(), technology2.getAcronyms());
		assertEquals(technology.getVersion(), technology2.getVersion());
		assertEquals(technology.getJsr(), technology2.getJsr());
		assertEquals(technology.getDescription(), technology2.getDescription());
	}
	
	private List<Technology> buildTechnologyList() {
		List<Technology> technologyList = new ArrayList<Technology>();
		
		Technology technology = new Technology();
    	technology.setAcronyms("Java EE");
    	technology.setVersion("6");
    	technology.setJsr("316");
    	technology.setDescription("This JSR is to develop Java EE 6, a release of the Java Platform, Enterprise Edition targeted to ship in 2008.");
    	
    	technologyList.add(technology);
    	
    	return technologyList;
	}
	
}
