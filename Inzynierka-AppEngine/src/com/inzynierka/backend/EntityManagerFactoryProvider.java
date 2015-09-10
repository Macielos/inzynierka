package com.inzynierka.backend;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EntityManagerFactoryProvider {
	private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");

	private EntityManagerFactoryProvider() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}