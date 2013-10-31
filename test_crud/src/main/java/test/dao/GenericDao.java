package test.dao;

import java.util.List;

public interface GenericDao<E> {

	public long create(Object o);
	public void delete(Object o);
	public void update(Object o);
	public List<E> query(String entityName);
	public List<E> query(String entityName, int start, int limit);
	public List<E> queryHql(String hql);
	public List<E> queryHql(String hql, int start, int limit);
	public long count(String entityName);
}
