package com.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.entity.Article;
import com.entity.SaveDeleteInterface;
import com.tool.PageUtil;

public class ArticleDAO{
   /* @Autowired
    @Qualifier("sessionFactory")
 */
	
	private SessionFactory sessionFactory;
	
    public Session getSession() {
        //事务必须是开启的，否则获取不到
        return sessionFactory.getCurrentSession();
    }
	public List<?> getArticleList(Article article, PageUtil pageUtil){
//		List list = jdbcTemplate.queryForList("");
		Session session = getSession();
		Criteria query = session.createCriteria(Article.class)
				.add(Restrictions.or( Restrictions.ne("del", 1), Restrictions.isNull("del")));
		// 标题 作者 栏目 时间
		if(article!=null){
			if(StringUtils.isNotBlank(article.getTitle())){
				query.add(Restrictions.like("title", article.getTitle(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(article.getAuthor())){
				query.add(Restrictions.like("author", article.getAuthor(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(article.getType1())){
				query.add(Restrictions.like("type1", article.getType1(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(article.getTitle())){
				query.add(Restrictions.between("createTime", article.getCreateTime(), article.getCreateTimeEnd()));
			}
		}
		
		//查询记录数，赋值给pageUtil
		Long total=(Long)query.setProjection(Projections.rowCount()).uniqueResult();
		pageUtil.setTotal(total.intValue());
		query.setProjection(null);
		
		query.setFirstResult(pageUtil.getPosition())
			.setMaxResults(pageUtil.getPagesize())
			.addOrder(Order.desc("createTime"));
		List<?> list= query.list();
		return list;
	}
	
	public Object getObjectById(Serializable id, Class<?> clazz){
		return getSession().get(clazz, id);
	}
	public void saveOrUpdate(Object object){
		this.getSession().saveOrUpdate(object);
	}
	
	public int del(Serializable id, SaveDeleteInterface object){
		int i=0;
		//for(Object object : obj){
			SaveDeleteInterface po = (SaveDeleteInterface)getSession().load(object.getClass(),id);
			po.setDel(1);
			getSession().update(po);
			i++;
		//}
		return i;
		//return getSession().createSQLQuery("update tbl_article set del = 1 where id in (:id)")
				//.setParameter("tbName", tbName)
				//.setParameterList("id", id)
				//.executeUpdate();
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
