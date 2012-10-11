package no.nith.pg560.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TechnologyTest {

	Technology technology;
	
	@Before
	public void setUp() throws Exception {
		technology = new Technology();
	}

	@Test
	public void testGetAndSetId() {
		technology.setId(10);
		assertEquals(10, technology.getId());
	}

	@Test
	public void testGetAndSetAcronyms() {
		technology.setAcronyms("a");
		assertEquals("a", technology.getAcronyms());
	}

	@Test
	public void testGetAndSetVersion() {
		technology.setVersion("1");
		assertEquals("1", technology.getVersion());
	}

	@Test
	public void testGetAndSetJsr() {
		technology.setJsr("1");
		assertEquals("1", technology.getJsr());
	}

	@Test
	public void testGetAndSetDescription() {
		technology.setDescription("desc");
		assertEquals("desc", technology.getDescription());
	}
}
