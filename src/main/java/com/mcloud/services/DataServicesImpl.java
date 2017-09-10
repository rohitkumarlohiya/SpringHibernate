package com.mcloud.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcloud.dao.DataDao;
import com.mcloud.entities.AbstractEntity;
import com.mcloud.models.commons.AbstractEntityList;

@Service(value = "dataServices")
public class DataServicesImpl implements DataServices<AbstractEntity> {

	@Autowired
	DataDao<AbstractEntity> dataDao;

	static final Logger logger = Logger.getLogger(DataServicesImpl.class);

	@Override
	public boolean addEntity(AbstractEntity entity) throws ConstraintViolationException, HibernateException, Exception {
		return dataDao.addEntity(entity);
	}

	@Override
	public AbstractEntity getEntity(long id, Class<? extends AbstractEntity> clazz) throws Exception {

		return dataDao.getEntity(id, clazz);
	}

	@Override
	public boolean deleteEntity(long id, Class<? extends AbstractEntity> clazz) throws Exception {
		return dataDao.deleteEntity(id, clazz);
	}

	@Override
	public List<AbstractEntity> getEntityList(Class<? extends AbstractEntity> clazz, Integer page, Integer perpage)
			throws HibernateException, Exception {
		return dataDao.getEntityList(clazz, page, perpage);
	}

	@Override
	public List<AbstractEntity> getEntityList(long id, String restrictionStr, Class<? extends AbstractEntity> clazz)
			throws Exception {
		return null;
	}

	/*
	 * @Override public Accounts findByUserName(String username) throws
	 * Exception { return null;// dataDao.findByUserName(username); }
	 */

	@Override
	public List<AbstractEntity> getEntityList(Class<? extends AbstractEntity> clazz, Integer page, Integer perpage,
			String queryType, String queryValue) throws HibernateException, Exception {
		return dataDao.getEntityList(clazz, page, perpage, queryType, queryValue);
	}

	@Override
	public AbstractEntityList getEntityListPagination(Class<? extends AbstractEntity> clazz, Integer page,
			Integer perpage, String queryType, String queryValue) throws HibernateException, Exception {
		return dataDao.getEntityListPagination(clazz, page, perpage, queryType, queryValue);
	}

	/*
	 * @Override public AbstractEntityList getEntityListMultipleFilter( Class<?
	 * extends AbstractEntity> clazz, Integer page, Integer perpage, Map<String,
	 * Object> queryMap) throws HibernateException, Exception { return
	 * dataDao.getEntityListMultipleFilter(clazz, page, perpage, queryMap); }
	 */

	@Override
	public boolean updateEntity(AbstractEntity entity) throws Exception {
		return dataDao.updateEntity(entity);
	}

	/*
	 * @Override public boolean disableEntity(long id, Class<? extends
	 * AbstractEntity> clazz) throws HibernateException, Exception { return
	 * dataDao.disableEntity(id, clazz); }
	 */

	@Override
	public AbstractEntity getEntityByIdByHQL(String tableName, long id) throws HibernateException, Exception {
		return dataDao.getEntityByIdByHQL(tableName, id);
	}

	@Override
	public boolean validateExistance(String columnName, Object columnValue, Class<? extends AbstractEntity> clazz) {
		return dataDao.validateExistance(columnName, columnValue, clazz);
	}

}
