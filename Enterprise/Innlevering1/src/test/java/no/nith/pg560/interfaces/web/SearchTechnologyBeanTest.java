package no.nith.pg560.interfaces.web;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchTechnologyBeanTest {
	SearchTechnologyBean searchTechnologyBean;
	
	@Before
	public void setUp() {
		searchTechnologyBean = new SearchTechnologyBean();
	}

	@Test
	public void testGetAndSetAcronyms() {
		searchTechnologyBean.setSearchAcronyms("a");
		assertEquals("a", searchTechnologyBean.getSearchAcronyms());
	}

	@Test
	public void testGetAndSetVersion() {
		searchTechnologyBean.setSearchVersion("1");
		assertEquals("1", searchTechnologyBean.getSearchVersion());
	}

	@Test
	public void testGetAndSetJsr() {
		searchTechnologyBean.setSearchJsr("1");
		assertEquals("1", searchTechnologyBean.getSearchJsr());
	}

	@Test
	public void testGetAndSetDescription() {
		searchTechnologyBean.setSearchDescription("desc");
		assertEquals("desc", searchTechnologyBean.getSearchDescription());
	}

}
