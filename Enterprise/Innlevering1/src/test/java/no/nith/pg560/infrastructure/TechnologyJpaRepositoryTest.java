package no.nith.pg560.infrastructure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import no.nith.pg560.domain.Technology;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TechnologyJpaRepositoryTest {
	
	@Mock
	private Query query;
	
	@Mock
	private EntityManager em;
	
	@Mock
	private TechnologyJpaRepository technologyJpaRepository;
	
	@Before
	public void setup() {
		em = Persistence.createEntityManagerFactory("pg560-test", System.getProperties()).createEntityManager();
		query = em.createQuery("SELECT t from Technology t");
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSearchTechnologies() {
		
		technologyJpaRepository = new TechnologyJpaRepository(em);
		when(technologyJpaRepository.getEntityManager()).thenReturn(em);
		when(em.createQuery(null)).thenReturn(query);
		when(query.getResultList()).thenReturn(buildTechnologyList());
		
		Technology technology = technologyJpaRepository.searchTechnologies("Java", "6", "316", "This JSR").get(0);
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
