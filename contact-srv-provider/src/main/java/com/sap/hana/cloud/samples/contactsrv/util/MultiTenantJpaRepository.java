package com.sap.hana.cloud.samples.contactsrv.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class MultiTenantJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
{
	private final CurrentTenantResolver tenantResolver;
	private final EntityManager em;

	public MultiTenantJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager em, CurrentTenantResolver tenantResolver)
	{
		super(entityInformation, em);
		this.tenantResolver = tenantResolver;
		this.em = em;
	}

	public MultiTenantJpaRepository(Class<T> domainClass, EntityManager em, CurrentTenantResolver tenantResolver)
	{
		super(domainClass, em);
		this.tenantResolver = tenantResolver;
		this.em = em;
	}

	protected void setCurrentTenant()
	{
		em.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantResolver.getCurrentTenantId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
	 */
	@Transactional
	public void delete(ID id) 
	{
		this.setCurrentTenant();
		super.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Object)
	 */
	@Transactional
	public void delete(T entity) 
	{
		this.setCurrentTenant();
		super.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
	 */
	@Transactional
	public void delete(Iterable<? extends T> entities) 
	{
		this.setCurrentTenant();
		super.delete(entities);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#deleteInBatch(java.lang.Iterable)
	 */
	@Transactional
	public void deleteInBatch(Iterable<T> entities) 
	{
		this.setCurrentTenant();
		super.deleteInBatch(entities);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.Repository#deleteAll()
	 */
	@Transactional
	public void deleteAll() 
	{
		this.setCurrentTenant();
		super.deleteAll();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#deleteAllInBatch()
	 */
	@Transactional
	public void deleteAllInBatch() 
	{
		this.setCurrentTenant();
		super.deleteAllInBatch();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	public T findOne(ID id) 
	{

		this.setCurrentTenant();
		return super.findOne(id);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#getOne(java.io.Serializable)
	 */
	@Override
	public T getOne(ID id) 
	{
		this.setCurrentTenant();
		return super.getOne(id);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)
	 */
	public boolean exists(ID id) 
	{
		this.setCurrentTenant();
		return super.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	public List<T> findAll() 
	{
		this.setCurrentTenant();
		return super.findAll();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findAll(ID[])
	 */
	public List<T> findAll(Iterable<ID> ids) 
	{
		this.setCurrentTenant();
		return super.findAll(ids);	
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll(org.springframework.data.domain.Sort)
	 */
	public List<T> findAll(Sort sort) 
	{
		this.setCurrentTenant();
		return super.findAll(sort);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Pageable)
	 */
	public Page<T> findAll(Pageable pageable) 
	{
		this.setCurrentTenant();
		return super.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findOne(org.springframework.data.jpa.domain.Specification)
	 */
	public T findOne(Specification<T> spec) 
	{
		this.setCurrentTenant();
		return super.findOne(spec);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification)
	 */
	public List<T> findAll(Specification<T> spec) 
	{
		this.setCurrentTenant();
		return super.findAll(spec);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification, org.springframework.data.domain.Pageable)
	 */
	public Page<T> findAll(Specification<T> spec, Pageable pageable) 
	{
		this.setCurrentTenant();
		return super.findAll(spec, pageable);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification, org.springframework.data.domain.Sort)
	 */
	public List<T> findAll(Specification<T> spec, Sort sort) 
	{
		this.setCurrentTenant();
		return super.findAll(spec, sort);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#count()
	 */
	public long count() 
	{
		this.setCurrentTenant();
		return super.count();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#count(org.springframework.data.jpa.domain.Specification)
	 */
	public long count(Specification<T> spec) 
	{
		this.setCurrentTenant();
		return super.count(spec);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Transactional
	public <S extends T> S save(S entity) 
	{
		this.setCurrentTenant();
		return super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#saveAndFlush(java.lang.Object)
	 */
	@Transactional
	public <S extends T> S saveAndFlush(S entity) 
	{
		this.setCurrentTenant();
		return super.saveAndFlush(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#save(java.lang.Iterable)
	 */
	@Transactional
	public <S extends T> List<S> save(Iterable<S> entities) 
	{
		this.setCurrentTenant();
		return super.save(entities);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#flush()
	 */
	@Transactional
	public void flush() 
	{
		this.setCurrentTenant();
		super.flush();
	}

}
