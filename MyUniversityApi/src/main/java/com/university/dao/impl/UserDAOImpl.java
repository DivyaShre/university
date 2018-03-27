package com.university.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.dao.UserDAO;
import com.university.model.UserEntity;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public UserEntity getUserDetails(String emailId, String password) {
		Session session = sessionFactory.getCurrentSession();
		Criterion emailIdCri = Restrictions.eq("emailId", emailId);
		Criterion passwordCri = Restrictions.eq("password", password);
		Criteria cri = session.createCriteria(UserEntity.class).add(emailIdCri).add(passwordCri);
		UserEntity user = (UserEntity) cri.uniqueResult();
		return user;
	}
}
