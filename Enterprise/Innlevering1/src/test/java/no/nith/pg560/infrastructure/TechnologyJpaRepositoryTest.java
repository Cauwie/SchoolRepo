package no.nith.pg560.infrastructure;

import java.util.ArrayList;
import java.util.List;

import no.nith.pg560.common.CommonInfrastructureIT;
import no.nith.pg560.domain.Technology;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TechnologyJpaRepositoryTest extends CommonInfrastructureIT {
	
	private TechnologyJpaRepository technologyRepo;
	
	@Before
	public void setup() {
		technologyRepo = new TechnologyJpaRepository(getEntityManager());
		buildTechnologyList();
	}

	@Test
	public void testGetTechnologies() {
		List<Technology> technologyResult = technologyRepo.getTechnologies();
		assertEquals("Java EE", technologyResult.get(0).getAcronyms());
		assertEquals("6", technologyResult.get(0).getVersion());
		assertEquals("316", technologyResult.get(0).getJsr());
		assertEquals("This JSR is to develop Java EE 6, a release of the Java Platform, Enterprise Edition targeted to ship in 2008.", technologyResult.get(0).getDescription());
	}
	
	@Test
	public void testSearchTechnologies() {
		List<Technology> technologyResult = technologyRepo.searchTechnologies("Java", "6", "316", "This JSR");
		assertEquals("Java EE", technologyResult.get(0).getAcronyms());
		assertEquals("6", technologyResult.get(0).getVersion());
		assertEquals("316", technologyResult.get(0).getJsr());
		assertEquals("This JSR is to develop Java EE 6, a release of the Java Platform, Enterprise Edition targeted to ship in 2008.", technologyResult.get(0).getDescription());
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
