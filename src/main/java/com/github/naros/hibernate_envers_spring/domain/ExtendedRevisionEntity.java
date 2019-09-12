/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.github.naros.hibernate_envers_spring.domain;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 * @author Chris Cranford
 */
@Entity
@RevisionEntity(value = ExtendedRevisionListener.class)
public class ExtendedRevisionEntity extends DefaultRevisionEntity {
	// just marker entity to point to listener
	// can accomplish the same by explicitly setting up listener in bootstrap properties
}
