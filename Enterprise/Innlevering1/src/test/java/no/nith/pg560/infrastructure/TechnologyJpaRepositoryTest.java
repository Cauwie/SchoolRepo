package no.nith.pg560.infrastructure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
<<<<<<< HEAD
=======
import javax.persistence.Persistence;
>>>>>>> c8fac6d0af89ad7dea7db98b141768756ef39cb2
import javax.persistence.Query;

import no.nith.pg560.domain.Technology;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TechnologyJpaRepositoryTest {

	private TechnologyJpaRepository techRepo;
	
	@Mock
	private EntityManager em;
	
	@Mock
	private Query query;
<<<<<<< HEAD
=======
	
	@Mock
	private EntityManager em;
	
	@Mock
	private TechnologyJpaRepository technologyJpaRepository;
>>>>>>> c8fac6d0af89ad7dea7db98b141768756ef39cb2
	
	@Before
	public void setup() {
		em = Persistence.createEntityManagerFactory("pg560-test", System.getProperties()).createEntityManager();
		query = em.createQuery("SELECT t from Technology t");
		MockitoAnnotations.initMocks(this);
		techRepo = new TechnologyJpaRepository(em);
		when(em.createQuery(Mockito.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(buildTechnologyList());
	}
	
	@Test
<<<<<<< HEAD
	public void testGetTechnologies() {
		Technology technology = techRepo.getTechnologies().get(0);
		Technology technology2 = buildTechnologyList().get(0);
=======
	public void testSearchTechnologies() {
		
		technologyJpaRepository = new TechnologyJpaRepository(em);
		when(technologyJpaRepository.getEntityManager()).thenReturn(em);
		when(em.createQuery(null)).thenReturn(query);
		when(query.getResultList()).thenReturn(buildTechnologyList());
>>>>>>> c8fac6d0af89ad7dea7db98b141768756ef39cb2
		
		assertEquals(technology.getAcronyms(), technology2.getAcronyms());
		assertEquals(technology.getVersion(), technology2.getVersion());
		assertEquals(technology.getJsr(), technology2.getJsr());
		assertEquals(technology.getDescription(), technology2.getDescription());
	}
	
	@Test
	public void testSearchTechnologies() {
		Technology technology = techRepo.searchTechnologies("Java", "6", "316", "This JSR").get(0);
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
