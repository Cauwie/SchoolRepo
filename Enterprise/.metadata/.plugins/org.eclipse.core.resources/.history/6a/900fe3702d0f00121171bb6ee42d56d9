package no.nith.pg560.application;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

import no.nith.pg560.domain.Technology;
import no.nith.pg560.infrastructure.TechnologyJpaRepository;

@Singleton
public class TechnologyServiceBean {
	
	private List<Technology> technologies;
	
	@Inject
	private TechnologyJpaRepository repository;
	
	@PostConstruct
	public void init(){
		
		technologies = repository.getTechnologies();	
		System.out.println(technologies);
	}
	
	public List<Technology> getTechnologyList(){
		return technologies;
	}

}
