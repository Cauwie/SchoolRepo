package no.nith.pg560.application;

import static org.junit.Assert.assertNotNull;

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
//
//    @Test
//    public void readUsers() throws Exception {
//    	when(technologyJpaRepository.getTechnologies()).thenReturn(buildTechnologies());
//        Technology technology = technologyServiceBean.getUser("Ola");
//        assertNotNull(technology);
//    }	
//    
//    private Technology buildTechnologies() {
//    	List<Technology> technologies;
//    	Technology technology = new Technology();
//    	technology.setDescription("test");
//    	
//    	return technology;
//    }

}
