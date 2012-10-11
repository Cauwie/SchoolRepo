package no.nith.pg560.application;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import no.nith.pg560.domain.Technology;
import no.nith.pg560.infrastructure.TechnologyJpaRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TechnologyServiceBeanTest {
    @Mock
    private TechnologyJpaRepository technologyJpaRepository;

    @InjectMocks
    private TechnologyServiceBean technologyServiceBean;

    @Before
    public void setup() {
    	technologyServiceBean = new TechnologyServiceBean();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllTechnologies() throws Exception {
    	when(technologyJpaRepository.getTechnologies()).thenReturn(buildTechnologies());
    	technologyServiceBean.init();
    	List<Technology> technologies = technologyServiceBean.getTechnologyList();

    	Technology technology = technologies.get(0);
    	Technology technology2 = buildTechnologies().get(0);
        
    	assertNotNull(technology);
        assertEquals(technology2.getAcronyms(), technology.getAcronyms());
        assertEquals(technology2.getDescription(), technology.getDescription());
        assertEquals(technology2.getJsr(), technology.getJsr());
        assertEquals(technology2.getVersion(), technology.getVersion());
        assertEquals(technology2.getId(), technology.getId());
    }
    
    @Test
    public void searchForTechnologies() throws Exception {
    	when(technologyJpaRepository.searchTechnologies("testAcro", "testVersion", "testJSR", "testDescription")).thenReturn(buildTechnologies());
    	List<Technology> technologies = technologyServiceBean.searchTechnologies("testAcro", "testVersion", "testJSR", "testDescription");
        
    	Technology technology = technologies.get(0);
    	Technology technology2 = buildTechnologies().get(0);
        
    	assertNotNull(technology);
        assertEquals(technology2.getAcronyms(), technology.getAcronyms());
        assertEquals(technology2.getDescription(), technology.getDescription());
        assertEquals(technology2.getId(), technology.getId());
    }
    
    private List<Technology> buildTechnologies() {
    	List<Technology> technologies = new ArrayList<Technology>();
    	
    	Technology technology = new Technology();
    	technology.setAcronyms("testAcro");
    	technology.setVersion("testVersion");
    	technology.setJsr("testJSR");
    	technology.setDescription("testDescription");
    	
    	Technology technology2 = new Technology();
    	technology2.setAcronyms("testAcro2");
    	technology2.setVersion("testVersion2");
    	technology2.setJsr("testJSR2");
    	technology2.setDescription("testDescription2");

    	technologies.add(technology);
    	technologies.add(technology2);
    	
    	return technologies;
    }
}
