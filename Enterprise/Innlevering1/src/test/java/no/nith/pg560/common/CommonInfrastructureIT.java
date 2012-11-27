package no.nith.pg560.common;

import java.util.ArrayList;
import java.util.List;

import no.nith.pg560.domain.Technology;


/**
 * Common class to make it easier to implement integration tests. 
 */
public class CommonInfrastructureIT {
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
        
        return technologies;
    }
}
