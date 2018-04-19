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
	public int updateUserDetails(long id,String fname, String mobileNo,String alternateMobile) {
		Session session = sessionFactory.getCurrentSession();
		if(fname != null && mobileNo!= null && alternateMobile!= null)
		{
			Query query = session.createQuery("update UserEntity set fname= :f, mobileNO= :m, alternateMobile= :am where id= :i");
			query.setParameter("f",fname);
			query.setParameter("m",mobileNo);
			query.setParameter("am",alternateMobile);
			query.setParameter("i",id);
			return query.executeUpdate();
		}
		if(fname != null && mobileNo!= null && alternateMobile== null)
		{
			Query query = session.createQuery("update UserEntity set fname= :f, mobileNO= :m where id= :i");
			query.setParameter("f",fname);
			query.setParameter("m",mobileNo);
			query.setParameter("i",id);
			return query.executeUpdate();
		}
		if(fname != null && mobileNo== null && alternateMobile!= null)
		{
			Query query = session.createQuery("update UserEntity set fname= :f, alternateMobile= :am where id= :i");
			query.setParameter("f",fname);
			query.setParameter("am",alternateMobile);
			query.setParameter("i",id);
			return query.executeUpdate();
		}
		
		if(fname == null && mobileNo!= null && alternateMobile!= null)
		{
			Query query = session.createQuery("update UserEntity set mobileNO= :m, alternateMobile= :am where id= :i");
			query.setParameter("m",mobileNo);
			query.setParameter("am",alternateMobile);
			query.setParameter("i",id);
			return query.executeUpdate();
		}
		if(fname != null)
		{
			Query query = session.createQuery("update UserEntity set fname= :f where id= :i");
			query.setParameter("f",fname);
			query.setParameter("i",id);
			return query.executeUpdate();
		}
		if( mobileNo!= null )
		{
			Query query = session.createQuery("update UserEntity set mobileNO= :m where id= :i");
			query.setParameter("m",mobileNo);
			query.setParameter("i",id);
			return query.executeUpdate();
		}
		if(alternateMobile!= null)
		{
			Query query = session.createQuery("update UserEntity set alternateMobile= :am where id= :i");
			query.setParameter("am",alternateMobile);
			query.setParameter("i",id);
			return query.executeUpdate();
		}
		return 0;
		
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
	public List<UserEntity> gettingUserDetails()
	{
		Session session = sessionFactory.getCurrentSession();
		// select * from user
		Criteria cri = session.createCriteria(UserEntity.class);
		return cri.list();
	}

	
}
