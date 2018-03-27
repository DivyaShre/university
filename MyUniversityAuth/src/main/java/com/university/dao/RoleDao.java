package com.university.dao;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.model.RoleEntity;

@Repository
@Transactional
public class RoleDao {

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
	 * This method is used to save the endUser
	 * @param user
	 */
	public void save(RoleEntity role) {
		getSession().save(role);
		return;
	}

	/**
	 * This method is used to delete the endUser
	 * @param user
	 */
	public void delete(RoleEntity user) {
		getSession().delete(user);
		return;
	}

	/**
	 * This method is used to get endUser by accessing userEmailId
	 * @param userEmailID
	 * @return
	 */
	public RoleEntity getById(long id) {
		Session session = _sessionFactory.getCurrentSession();
		Criterion roleIdCri = Restrictions.eq("id", id);
		Criteria cri = session.createCriteria(RoleEntity.class).add(roleIdCri);
		RoleEntity role = (RoleEntity) cri.uniqueResult();
		return role;
	}
}
