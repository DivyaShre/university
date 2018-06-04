package com.university.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.dao.UserDAO;
import com.university.model.UserEntity;
import com.university.utility.Constants;

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
	
	@Override
	public UserEntity getUserDetailsByEmailId(String emailId) {
		Session session = sessionFactory.getCurrentSession();
		Criterion emailIdCri = Restrictions.eq("emailId", emailId);
		Criteria cri = session.createCriteria(UserEntity.class).add(emailIdCri);
		UserEntity user = (UserEntity) cri.uniqueResult();
		return user;
	}

	@Override
	public long saveUserDetails(UserEntity user) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(user);
	}
	
	@Override
	public long saveNewPassword(UserEntity user) {
		
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(user);
	}

	
	@Override
	public int updateUserDetails(long id,String fname,String mname,String lname, String mobileNo,String alternateMobile, String gender) {
		Session session = sessionFactory.getCurrentSession();
		Criterion idCri = Restrictions.eq("id", id);
		Criteria cri = session.createCriteria(UserEntity.class).add(idCri);
		UserEntity user = (UserEntity) cri.uniqueResult();
		if(fname != null)
		{
			user.setFname(fname);
		}
		if(mname != null)
		{
			user.setMname(mname);
		}
		if(lname != null)
		{
			user.setLname(lname);
		}
		
		if(mobileNo!= null)
		{
			user.setMobileNo(mobileNo);
		}
		
		if(alternateMobile!= null)
		{
			user.setAlternateMobile(alternateMobile);

		}
		if(gender!=null)
		{
			user.setGender(gender);
		}
		session.update(user);
		return 1;
		
	}
	@Override
	public UserEntity getUserDetailsById(long id)
	{
		Session session = sessionFactory.getCurrentSession();
		Criterion idCri = Restrictions.eq("id", id);
		Criteria cri = session.createCriteria(UserEntity.class).add(idCri);
		UserEntity user = (UserEntity) cri.uniqueResult();
		return user;
		
	}
	@Override
	public int deleteUserDetails(int status, long id)
	{
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("update UserEntity  set status= :s where id= :i") ;
		q.setParameter("s", status);
		q.setParameter("i", id);
		return q.executeUpdate();
	}
	
		
	@Override
	public List<UserEntity> gettingUserDetails(int status)
	{
		Session session = sessionFactory.getCurrentSession();
		// select * from user
		Criterion statusCri = Restrictions.eq("status", status); // to get only active users
		Criterion emailCri = Restrictions.ne("emailId", "admin@gmail.com");
		Criteria cri = session.createCriteria(UserEntity.class).add(statusCri).add(emailCri);
		return cri.list();
	}

	
	

	
}
