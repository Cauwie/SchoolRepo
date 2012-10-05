package no.nith.pg560.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Common class to be used in repository classes. Helps handling the PersistenceContext and the EntityManager. 
 */
public class CommonRepository<T> {
    @PersistenceContext(unitName = "pg560")
    private EntityManager entityManager;

    private final Class<T> domainType;

    protected CommonRepository(Class<T> domainType) {
        this.domainType = domainType;
    }

    protected CommonRepository(Class<T> domainType, EntityManager em) {
        this(domainType);
        entityManager = em;
    }

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
