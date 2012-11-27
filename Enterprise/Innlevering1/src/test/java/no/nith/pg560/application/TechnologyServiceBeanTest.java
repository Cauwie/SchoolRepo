package no.nith.pg560.application;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import no.nith.pg560.domain.Technology;
import no.nith.pg560.infrastructure.TechnologyJpaRepository;
import no.nith.pg560.common.CommonInfrastructureIT;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TechnologyServiceBeanTest extends CommonInfrastructureIT {
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
    public void testSearchForTechnologies() throws Exception {
    	when(technologyJpaRepository.searchTechnologies("testAcro", "testVersion", "testJSR", "testDescription")).thenReturn(buildTechnologies());
    	List<Technology> technologies = technologyServiceBean.searchTechnologies("testAcro", "testVersion", "testJSR", "testDescription");
        
    	Technology technology = technologies.get(0);
    	Technology technology2 = buildTechnologies().get(0);
        
    	assertNotNull(technology);
        assertEquals(technology2.getAcronyms(), technology.getAcronyms());
        assertEquals(technology2.getDescription(), technology.getDescription());
        assertEquals(technology2.getId(), technology.getId());
    }
}
