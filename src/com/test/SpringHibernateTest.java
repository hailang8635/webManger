package com.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.action.article.ArticleAction;
import com.dao.ArticleDAO;
import com.entity.Article;

import org.hibernate.internal.util.xml.DTDEntityResolver;
public class SpringHibernateTest {

	private SessionFactory sessionFactory;
	private ApplicationContext ctx;
	
	@Autowired
	private ArticleDAO articleDAO;
	
	@Before
	public void setUp() {
		String[] configLocations = new String[] { "spring-hibernate.xml" };
		ctx = new ClassPathXmlApplicationContext(configLocations);
		sessionFactory = ctx.getBean("sessionFactory", SessionFactory.class);
	}

	@Test
	public void testSessionFactory() {
//		ArticleDAO dao = (ArticleDAO) ctx.getBean("ArticleDAO");
		ArticleAction articleAction = (ArticleAction) ctx.getBean("articleAction");
		System.out.println(articleAction);
		//List list= articleDAO.getArticleList();
		//org.hibernate.util.DTDEntityResolver;
		/*System.out.println(sessionFactory);
		System.out.println(ctx.getBean("dataSource"));
		Session session = sessionFactory.openSession();
		Article article = new Article();
		article.setId(1110);
		article.setAuthor("ceshi");
		session.save(article);
		*/
	}
}
