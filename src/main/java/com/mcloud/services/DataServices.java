package com.mcloud.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.mcloud.entities.AbstractEntity;
import com.mcloud.models.commons.AbstractEntityList;

public interface DataServices<T extends AbstractEntity> {

	/**
	 * @param entity
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addEntity(T entity) throws ConstraintViolationException, HibernateException, Exception;

	/**
	 * @param entity
	 * @return boolean
	 * @throws Exception
	 */

	public T getEntity(long id, Class<? extends T> clazz) throws Exception;

	/**
	 * @param id
	 * @param clazz
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteEntity(long id, Class<? extends T> clazz) throws Exception;

	/**
	 * @param clazz
	 * @param page
	 * @param perpage
	 * @return List<T>
	 * @throws HibernateException
	 * @throws Exception
	 */

	public List<T> getEntityList(Class<? extends T> clazz, Integer page, Integer perpage)
			throws HibernateException, Exception;

	public List<T> getEntityList(Class<? extends T> clazz, Integer page, Integer perpage, String queryType,
			String queryValue) throws HibernateException, Exception;

	/**
	 * @param id
	 * @param restrictionStr
	 * @param clazz
	 * @return List<T>
	 * @throws Exception
	 */
	public List<T> getEntityList(long id, String restrictionStr, Class<? extends T> clazz) throws Exception;

	/* public Accounts findByUserName(String username) throws Exception; */

	public AbstractEntityList getEntityListPagination(Class<? extends T> clazz, Integer page, Integer perpage,
			String queryType, String queryValue) throws HibernateException, Exception;

	/*
	 * public AbstractEntityList getEntityListMultipleFilter( Class<? extends T>
	 * clazz, Integer page, Integer perpage, Map<String, Object> queryMap)
	 * throws HibernateException, Exception;
	 */

	boolean updateEntity(AbstractEntity entity) throws Exception;

	/*
	 * public boolean disableEntity(long id, Class<? extends AbstractEntity>
	 * clazz) throws HibernateException, Exception;
	 */

	public AbstractEntity getEntityByIdByHQL(String tableName, long id) throws HibernateException, Exception;

	public boolean validateExistance(String columnName, Object columnValue, Class<? extends T> clazz);

}
