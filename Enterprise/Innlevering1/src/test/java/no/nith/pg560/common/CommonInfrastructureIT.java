package no.nith.pg560.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import no.nith.pg560.domain.Technology;

import org.junit.After;
import org.junit.Before;

/**
 * Common class to make it easier to implement integration tests. 
 */
public class CommonInfrastructureIT {

    private EntityManager entityManager;
    private EntityTransaction tr;

    @Before
    public void setUp() {
        entityManager = createEntityManager("pg560-test");
        tr = entityManager.getTransaction();
        tr.begin();
        buildTechnologies();
    }

    private EntityManager createEntityManager(String persistenceUnit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit, System.getProperties());
        return emf.createEntityManager();
    }

	protected List<Technology> buildTechnologies() {
		
		List<Technology> technologies = new ArrayList<Technology>();
    	
    	Technology technology = new Technology();
    	technology.setAcronyms("testAcro");
    	technology.setVersion("testVersion");
    	technology.setJsr("testJSR");
    	technology.setDescription("testDescription");
    	
    	Technology technology2 = new Technology();
    	technology2.setAcronyms("testAcro2");
    	technology2.setVersion("testVersion2");
    	technology2.setJsr("testJSR2");
    	technology2.setDescription("testDescription2");

    	technologies.add(technology);
    	technologies.add(technology2);
    	
//    	getEntityManager().createQuery("insert into PG560_JEE_TECHNOLOGY" 
//    								+ "(acronyms, version, jsr, description) "
//    								+ "values(" 
//    								+ " '" + technology.getAcronyms() + "', '" 
//    								+ technology.getVersion() + "', '" 
//    								+ technology.getJsr() + "', '" 
//    								+ technology.getDescription() + "');").executeUpdate();
//        tr.commit();
        
        return technologies;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @After
    public void cleanUp() {
        entityManager.flush();
        entityManager.clear();
        tr.rollback();
    }
}
