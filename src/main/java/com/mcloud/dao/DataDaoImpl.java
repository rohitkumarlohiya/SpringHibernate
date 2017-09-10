package com.mcloud.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.mcloud.constants.ApplicationConstants;
import com.mcloud.entities.AbstractEntity;
import com.mcloud.entities.CustomUser;
import com.mcloud.models.commons.AbstractEntityList;
import com.mcloud.utils.FilterUtils;

@Repository
public class DataDaoImpl implements DataDao<AbstractEntity> {

	static final Logger logger = Logger.getLogger(DataDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean addEntity(AbstractEntity entity) throws ConstraintViolationException, HibernateException, Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			addCreatedDateAndCreatedBy(entity);
			session.save(entity);
			doTxnCommit(tx);
		} finally {
			session.close();
		}
		return true;
	}

	@Override
	public boolean updateEntity(AbstractEntity entity) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			addUpdatedDateAndUpdatedBy(entity);
			session.saveOrUpdate(entity);
			doTxnCommit(tx);
		} finally {

			session.close();
		}
		return true;
	}

	private void addCreatedDateAndCreatedBy(AbstractEntity entity) {
		if (BooleanUtils.isFalse(entity.getEnabled())) {
			entity.setEnabled(true);
		}
		entity.setCd(new Date());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			CustomUser customUser = (CustomUser) authentication.getPrincipal();
			entity.setCb(customUser.getId());
		} else {
			entity.setCb(-1L);
		}
	}

	private void addUpdatedDateAndUpdatedBy(AbstractEntity entity) {
		entity.setEnabled(entity.getEnabled());
		entity.setUd(new Date());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			CustomUser customUser = (CustomUser) authentication.getPrincipal();
			entity.setUb(customUser.getId());
		} else {
			entity.setUb(-1L);
		}
	}

	@Override
	public AbstractEntity getEntity(long id, Class<? extends AbstractEntity> clazz) throws Exception {
		AbstractEntity abstractEntity = null;

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		session.beginTransaction();
		try {
			abstractEntity = (AbstractEntity) session.get(clazz, new Long(id));
			doTxnCommit(tx);
		} finally {

			session.close();
		}
		return abstractEntity;
	}

	@Override
	public boolean deleteEntity(long id, Class<? extends AbstractEntity> clazz) throws Exception {

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		session.beginTransaction();
		try {
			AbstractEntity abstractEntity = (AbstractEntity) session.get(clazz, new Long(id));
			session.delete(abstractEntity);
			doTxnCommit(tx);
		} finally {

			session.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AbstractEntity> getEntityList(Class<? extends AbstractEntity> clazz, Integer page, Integer perpage)
			throws HibernateException, Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AbstractEntity> list = null;
		try {

			Criteria criteria = session.createCriteria(clazz);
			FilterUtils.applyPageAndPerPageFilter(page, perpage, criteria);
			list = criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).setCacheable(true).list();
			doTxnCommit(tx);
		} finally {

			session.close();
		}
		return list;

	}

	@Override
	public List<AbstractEntity> getEntityList(long id, String restrictionStr, Class<? extends AbstractEntity> clazz)
			throws Exception {

		return null;

	}

	@Override
	public CustomUser findByUserName(String username) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		CustomUser accounts = null;
		try {
			String hql = "FROM CustomUser A WHERE A.firstName = :firstName";

			Query query = session.createQuery(hql);
			query.setParameter("firstName", username);

			@SuppressWarnings("unchecked")
			List<CustomUser> results = query.list();

			accounts = results.get(0);
			doTxnCommit(tx);
		} finally {
			session.close();
		}
		return accounts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AbstractEntity> getEntityList(Class<? extends AbstractEntity> clazz, Integer page, Integer perpage,
			String queryType, String queryValue) throws HibernateException, Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AbstractEntity> list = null;
		try {
			Criteria criteria = session.createCriteria(clazz);

			if (queryType != null)
				criteria.add(Restrictions.like(queryType, queryValue));

			FilterUtils.applyPageAndPerPageFilter(page, perpage, criteria);
			list = criteria.setCacheable(true).list();
			doTxnCommit(tx);
		} finally {
			session.close();
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractEntityList getEntityListPagination(Class<? extends AbstractEntity> clazz, Integer page,
			Integer perpage, String queryType, String queryValue) throws HibernateException, Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Long resultCount = null;
		List<AbstractEntity> list = null;
		try {
			Criteria criteria = session.createCriteria(clazz);
			Criteria criteriaCount = session.createCriteria(clazz);

			if (queryType != null) {
				criteria.add(Restrictions.like(queryType, queryValue));
				criteriaCount.add(Restrictions.like(queryType, queryValue));
			}
			resultCount = (Long) criteriaCount.setProjection(Projections.rowCount()).uniqueResult();

			FilterUtils.applyPageAndPerPageFilter(page, perpage, criteria);

			list = criteria.setCacheable(true).list();
			doTxnCommit(tx);
		} finally {
			session.close();
		}
		return new AbstractEntityList(resultCount, list);
	}

	
	 

	@SuppressWarnings("unchecked")
	@Override
	public AbstractEntity getEntityByIdByHQL(String tableName, long id) throws HibernateException, Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AbstractEntity> list = null;
		try {
			Query query = session.createQuery("from " + tableName.trim() + " where id = :id ");
			query.setParameter(ApplicationConstants.ID, id);
			list = query.list();
			doTxnCommit(tx);
		} finally {
			session.close();
		}
		if (list != null && list.size() != 0)
			return list.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateExistance(String columnName, Object columnValue, Class<? extends AbstractEntity> clazz) {
		boolean flag = false;

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		session.beginTransaction();
		try {
			Criteria absCriteria = session.createCriteria(clazz);
			List<AbstractEntity> list = absCriteria.add(Restrictions.eq(columnName, columnValue)).list();

			if (list != null && list.size() > 0)
				flag = true;

			doTxnCommit(tx);
		} finally {
			session.close();
		}
		return flag;
	}

	private void doTxnCommit(Transaction tx) {
		if (!tx.wasCommitted())
			tx.commit();
	}

}
