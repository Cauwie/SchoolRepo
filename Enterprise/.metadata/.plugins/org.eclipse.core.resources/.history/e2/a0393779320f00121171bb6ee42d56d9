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
			logger.info("Fikk ingen treff");
		}
		return technologies;
	}
}
