package no.nith.pg560.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import no.nith.pg560.common.CommonRepository;
import no.nith.pg560.domain.Technology;

import org.apache.log4j.Logger;

public class TechnologyJpaRepository  extends CommonRepository<Technology> {
	private Logger logger = Logger.getLogger(TechnologyJpaRepository.class);
	
	
	public TechnologyJpaRepository() {
		super(Technology.class);
	}

	public TechnologyJpaRepository(EntityManager em) {
		super(Technology.class, em);
	}

	@SuppressWarnings("unchecked")
	public List<Technology> getTechnologies() {
		Query query = getEntityManager().createQuery("select t from Technology t");
		
		List<Technology> technologies = null; 
		try {
			technologies = query.getResultList();
		} catch (NoResultException e) {
			logger.info("Søk etter alt i databasen gav ingen treff");
		}
		return technologies;
	}
	
	@SuppressWarnings("unchecked")
	public List<Technology> searchTechnologies(String acronyms, String version, String jsf, String description) {
		
		Query query = getEntityManager().createQuery("SELECT t FROM Technology t " +
				"WHERE upper(t.acronyms) LIKE upper(:acronyms) " +
				"AND upper(t.version) LIKE upper(:version) " +
				"AND upper(t.jsr) LIKE upper(:jsr) " +
				"AND upper(t.description) LIKE upper(:description)");
		
		query.setParameter("acronyms","%" + acronyms + "%");
		query.setParameter("version", "%" + version + "%");
		query.setParameter("jsr","%" + jsf + "%");
		query.setParameter("description","%" + description + "%");
		
		List<Technology> searchResults = null; 
		try {
			searchResults = query.getResultList();
		} catch (NoResultException e) {
			logger.info("Søk etter acronyms: \"" + acronyms 
					+ "\", version: \"" + version 
					+ "\", jsf: \"" + jsf
					+ "\", description: \"" + description
					+ "\", i databasen gav ingen treff");
		}
		return searchResults;
	}
}
