package com.university.dao;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.model.UserEntity;

@Repository
@Transactional
public class UserDao {

	@Autowired
	private SessionFactory _sessionFactory;

	/**
	 * This method is used to get session
	 * @return
	 */
	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	/**
	 * This method is used to get user by accessing kpId
	 * @param kpId
	 * @return
	 */
	public UserEntity getById(String id) {
		Session session = _sessionFactory.getCurrentSession();
		Criterion userEmailIdCri = Restrictions.eq("emailId", id);
		Criteria cri = session.createCriteria(UserEntity.class).add(userEmailIdCri);
		UserEntity user = (UserEntity) cri.uniqueResult();
		return user;
	}
}
