package org.hibernate.bugs;

import static org.junit.Assert.assertNotNull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
		initData();
	}

	private void initData() {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();

		Organization org = new Organization();
		org.setName("org1");
		em.persist(org);

		User user = new User();
		user.setName("Robert");
		user.setOrganization(org);
		em.persist(user);

		org.setPrimaryUser(user);

		User user1 = new User();
		user.setName("Jack");
		user.setOrganization(org);
		em.persist(user1);

		Address address = new Address();
		address.setName("addr1");
		em.persist(address);

		user.setAddress(address);
		org.setOrgOwner(user1);

		em.flush();
		em.getTransaction().commit();
		em.close();
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Organization org = entityManager.find(Organization.class, 1);
		assertNotNull(org);
		System.out.println("Organization name: " + org.getName());

		byte[] logo = org.getLogo();
		if (logo != null) {
			System.out.println("logo size:" + logo.length);
		} else {
			System.out.println("logo is null");
		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
