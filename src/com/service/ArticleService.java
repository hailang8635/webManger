package com.service;

import java.io.Serializable;
import java.util.List;

import com.dao.ArticleDAO;
import com.entity.Article;
import com.entity.SaveDeleteInterface;
import com.tool.PageUtil;

public class ArticleService {

	private ArticleDAO articleDAO;
	
	public List<?> getArticleList(Article article, PageUtil pageUtil){
		List<?> lst = articleDAO.getArticleList(article, pageUtil);
		return lst;
	}
	
	public Object getObjectById(Serializable id, Class<?> clazz){
		return articleDAO.getObjectById(id, clazz);
	}
	public void saveOrUpdate(Object object){
		articleDAO.saveOrUpdate(object);
	}
	
	public int delById(Serializable id,SaveDeleteInterface object){
		return articleDAO.del(id, object);
	}

	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}
	
}
