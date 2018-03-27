package com.university.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.dao.RoleDAO;

@Repository
public class RoleDAOImpl implements RoleDAO {

	@Autowired
	SessionFactory sessionFactory;
}
