package com.sap.hana.cloud.samples.contactsrv.util;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

public class MultiTenantJpaRepositoryFactory extends JpaRepositoryFactory
{
	private final CurrentTenantResolver currentTenantResolver;

	public MultiTenantJpaRepositoryFactory(EntityManager entityManager, CurrentTenantResolver currentTenantResolver)
	{
		super(entityManager);
		this.currentTenantResolver = currentTenantResolver;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected MultiTenantJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager)
	{
	
		final JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
		final MultiTenantJpaRepository<?, ?> repo = new MultiTenantJpaRepository(entityInformation, entityManager, currentTenantResolver);

		return repo;
	}

}