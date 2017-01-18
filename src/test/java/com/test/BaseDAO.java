package com.test;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BaseDAO<T> {
	protected HibernateTemplate hibernateTemplate;
	
	//获取某一个类的所有数据
	//泛型的问题，为什么这里不能使用T.getClass?,可能就跟不能直接new T()是一样的道理,
	//需要运行时候才知道
	
	@SuppressWarnings("unchecked")
	public List<T> listAll(Class<T> clazz){
		List<T> lists = (List<T>)hibernateTemplate.find("from " + clazz.getClass().getName());
		return lists;
	}
	
	//保存指定实体类
	public void save(T t){
		this.getHibernateTemplate().save(t);
	}
	
	//删除指定实体类
	public void delete(T t){
		this.getHibernateTemplate().delete(t);
	}
	
	//更新或者保存指定实体
	public void saveOrUpdate(T t){
		this.getHibernateTemplate().saveOrUpdate(t);
	}
	
	//根据id查找指定实体对象
	@SuppressWarnings("unchecked")
	public T findById(Class<T> clazz, int id){
		return (T)this.getHibernateTemplate().get(clazz.getClass(), id);
	}
	
	//查询指定hql并返回集合
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object... values){
		return (List<T>)this.getHibernateTemplate().find(hql, values);
	}
	
	//查询指定实体的总记录数
	public int getCount(Class<T> clazz){
		int count =  DataAccessUtils.intResult(hibernateTemplate.find("select count(*) from " + clazz.getName()));
		return count;
	}
	
	//根据传入的偏移量和步长来查询数据
	@SuppressWarnings("unchecked")
	public List<T> getListForPage(final String hql, final int offset, final int length){
		List<T> list = (List<T>)this.hibernateTemplate.executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				List list = query.list();
				return list;
			}
		});
		return list;
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
}
