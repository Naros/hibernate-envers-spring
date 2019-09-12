/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.github.naros.hibernate_envers_spring.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import org.jboss.logging.Logger;

import com.github.naros.hibernate_envers_spring.domain.MyEntity;

/**
 * @author Chris Cranford
 */
@Service
public class TestServiceImpl implements TestService {

	private static final Logger LOGGER = Logger.getLogger(TestServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveEntity() {
		LOGGER.info("** Saving entity");
		final MyEntity entity = new MyEntity();
		entityManager.persist(entity);
	}
}
