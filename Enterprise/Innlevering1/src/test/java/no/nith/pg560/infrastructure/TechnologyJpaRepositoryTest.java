package no.nith.pg560.infrastructure;

import java.util.List;

import no.nith.pg560.common.CommonInfrastructureIT;
import no.nith.pg560.domain.Technology;

import org.junit.Test;

import static org.junit.Assert.*;

public class TechnologyJpaRepositoryTest extends CommonInfrastructureIT {

//	@Test
//	public void testGetTechnologies() {
//		TechnologyJpaRepository technologyRepo = new TechnologyJpaRepository(getEntityManager());
//		List<Technology> technologyResult = technologyRepo.getTechnologies();
//		assertEquals("Java EE", technologyResult.get(0).getAcronyms());
//		assertEquals("6", technologyResult.get(0).getVersion());
//		assertEquals("316", technologyResult.get(0).getJsr());
//		assertEquals("This JSR is to develop Java EE 6, a release of the Java Platform, Enterprise Edition targeted to ship in 2008.", technologyResult.get(0).getDescription());
//	}
	
//	@Test
//	public void testGetUser() {
//		UserJpaRepository userRepo = new UserJpaRepository(getEntityManager());
//		User userResult = userRepo.getUser("tonnyg");
//		assertEquals(userResult.getName(), "Tonny Gundersen");
//		assertEquals(userResult.getCity(), "Oslo");
//		assertEquals(userResult.getCountry(), "Norway");
//
//		userResult = userRepo.getUser("Finnes ikke");
//		assertNull(userResult);
//	}

}
