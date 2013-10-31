package test.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import test.dao.GenericDao;

/**
 * 提供增删改查的默认实现
 * 
 * @author yuelenghan
 * 
 */
@Component("genericDao")
public abstract class GenericDaoHibernate<E> implements GenericDao<E> {

	private HibernateTemplate hibernateTemplate;

	public long create(Object o) {
		return (Long) hibernateTemplate.save(o);
	}

	public void delete(Object o) {
		hibernateTemplate.delete(o);
	}

	public void update(Object o) {
		hibernateTemplate.update(o);
	}

	@SuppressWarnings("unchecked")
	public List<E> query(String entityName) {
		return hibernateTemplate.find("from " + entityName);
	}

	@SuppressWarnings("unchecked")
	public List<E> query(final String entityName, final int start,
			final int limit) {
		List<E> resultList = hibernateTemplate
				.executeFind(new HibernateCallback<List<E>>() {
					public List<E> doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<E> result = session
								.createQuery("from " + entityName)
								.setFirstResult(start).setMaxResults(limit)
								.list();
						return result;
					}
				});
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<E> queryHql(String hql) {
		return hibernateTemplate.find(hql);
	}

	@SuppressWarnings("unchecked")
	public List<E> queryHql(final String hql, final int start, final int limit) {
		List<E> resultList = hibernateTemplate
				.executeFind(new HibernateCallback<List<E>>() {
					public List<E> doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<E> result = session.createQuery(hql)
								.setFirstResult(start).setMaxResults(limit)
								.list();
						return result;
					}
				});
		return resultList;
	}

	public long count(String entityName) {
		return this.query(entityName).size();
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
