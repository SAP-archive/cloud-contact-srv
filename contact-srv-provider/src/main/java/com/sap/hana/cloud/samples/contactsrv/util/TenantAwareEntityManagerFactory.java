package com.sap.hana.cloud.samples.contactsrv.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class TenantAwareEntityManagerFactory implements EntityManagerFactory
{
	private final EntityManagerFactory delegate;
    private final CurrentTenantResolver resolver;
 
    public TenantAwareEntityManagerFactory(EntityManagerFactory delegate, CurrentTenantResolver resolver) 
    {
        this.delegate = delegate;
        this.resolver = resolver;
    }
	
	@Override
    public EntityManager createEntityManager()
    {
		String tenantID = resolver.getCurrentTenantId();
	    Map<String, String> map = new HashMap<String, String>();
	    map.put(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantID);
	    return delegate.createEntityManager(map);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public EntityManager createEntityManager(Map map)
    {
		String tenantID = resolver.getCurrentTenantId();
	    map.put(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantID);
	    return delegate.createEntityManager(map);
    }

	@Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType)
    {
		String tenantID = resolver.getCurrentTenantId();
		Map<String, String> map = new HashMap<String, String>();
	    map.put(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantID);
	    return delegate.createEntityManager(synchronizationType, map);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map)
    {
		String tenantID = resolver.getCurrentTenantId();
	    map.put(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantID);
	    return delegate.createEntityManager(synchronizationType, map);
    }

	@Override
    public CriteriaBuilder getCriteriaBuilder()
    {
	    return delegate.getCriteriaBuilder();
    }

	@Override
    public Metamodel getMetamodel()
    {
	    return delegate.getMetamodel();
    }

	@Override
    public boolean isOpen()
    {
	   return delegate.isOpen();
    }

	@Override
    public void close()
    {
		delegate.close();
    }

	@Override
    public Map<String, Object> getProperties()
    {
	    return delegate.getProperties();
    }

	@Override
    public Cache getCache()
    {
	    return delegate.getCache();
    }

	@Override
    public PersistenceUnitUtil getPersistenceUnitUtil()
    {
	    return delegate.getPersistenceUnitUtil();
    }

	@Override
    public void addNamedQuery(String name, Query query)
    {
		delegate.addNamedQuery(name, query);
    }

	@Override
    public <T> T unwrap(Class<T> cls)
    {
	    return delegate.unwrap(cls);
    }

	@Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph)
    {
		delegate.addNamedEntityGraph(graphName, entityGraph);
	    
    }
}
