package com.microsvc.static1;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class MyDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	String dbversion() {
		return entityManager.createNativeQuery("Select version();").getResultList().get(0).toString();
		//dont expose version
		//return json of true false
	}
	
	
}
	