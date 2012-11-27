package no.nith.pg560.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import no.nith.pg560.common.CommonInfrastructureIT;
import no.nith.pg560.domain.Technology;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TechnologyJpaRepositoryTest extends CommonInfrastructureIT {


	private TechnologyJpaRepository technologyJpaRepository;
	
	@Mock
	private EntityManager em;
	
	@Mock
	private Query query;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		technologyJpaRepository = new TechnologyJpaRepository();
		technologyJpaRepository.setEntityManager(em);
		when(em.createQuery(Mockito.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(buildTechnologies());
	}
	
	@Test
	public void testSearchTechnologies() {
		Technology technology = technologyJpaRepository.searchTechnologies("Java", "6", "316", "This JSR").get(0);
		Technology technology2 = buildTechnologies().get(0);
		
		assertEquals(technology.getAcronyms(), technology2.getAcronyms());
		assertEquals(technology.getVersion(), technology2.getVersion());
		assertEquals(technology.getJsr(), technology2.getJsr());
		assertEquals(technology.getDescription(), technology2.getDescription());
	}
	
	@Test
	public void testNoResultWhenSearching() {
		when(query.getResultList()).thenThrow(new NoResultException());
		List<Technology> results = technologyJpaRepository.searchTechnologies(" ", " ", " ", " ");
		assertNull(results);
	}
}
