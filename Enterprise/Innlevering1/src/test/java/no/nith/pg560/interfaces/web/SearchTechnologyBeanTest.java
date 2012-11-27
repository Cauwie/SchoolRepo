package no.nith.pg560.interfaces.web;

import java.util.ArrayList;

import no.nith.pg560.domain.Technology;

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
	
	@Test
	public void testGetAndSetSearchResults() {
		Technology t = new Technology();
		ArrayList<Technology> list = new ArrayList<Technology>();
		list.add(t);
		searchTechnologyBean.setSearchResults(list);
		
		assertEquals(list, searchTechnologyBean.getSearchResults());
	}
	
	@Test
	public void setGetSerialVersionUid() {
		long id = SearchTechnologyBean.getSerialversionuid();
		assertNotNull(id);
	}

	@Test
	public void testToString() {
		searchTechnologyBean.setSearchAcronyms("a");
		searchTechnologyBean.setSearchJsr("1");
		searchTechnologyBean.setSearchVersion("1");
		searchTechnologyBean.setSearchDescription("desc");
		
		assertEquals("SearchTechnologyBean [searchAcronyms=a, searchVersion=1, searchJsr=1, searchDescription=desc]", searchTechnologyBean.toString());
	}
}
