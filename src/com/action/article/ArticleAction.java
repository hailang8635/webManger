package com.action.article;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Article;
import com.service.ArticleService;
import com.tool.Constants;
import com.tool.PageUtil;

@Controller
public class ArticleAction {
	private static Logger log = Logger.getLogger(ArticleAction.class);
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 进入列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(ModelMap model) {
		model.addAttribute("type1Map",Constants.getType1Map());
		return "list";
	}
	
	/**
	 * 加载列表数据
	 * @param pageUtil
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@ModelAttribute Article article,
			@ModelAttribute(value="pageUtil") PageUtil pageUtil,
			ModelMap model) {
		List<?> lst = articleService.getArticleList(article, pageUtil);
		long count = lst.size();
		log.info("list加载数据数:"+count);
		//String aj = JsonUtil.getObjectMapper().writeValueAsString(lst);
		//JSONArray arr = JSONArray.fromObject(lst);
		model.addAttribute("Rows", lst);
		model.addAttribute("Total", pageUtil.getTotal());
		return "list";
	}

	/**
	 * 保存
	 * @param article
	 * @param tabId
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(
			@ModelAttribute Article article,
			@RequestParam(value="tabId",required=true) String tabId,
			ModelMap model) {
		
		String content = article.getContent();
		if(content.indexOf("<h")>-1 && content.indexOf("</h")>-1){
			article.setTitle(content.substring(content.indexOf("<h")+4, content.indexOf("</h")));
		}
		
		Article po = null;
		if(article!=null && article.getId()!=null){
			po = (Article)articleService.getObjectById(article.getId(), Article.class);
			po.setAuthor(article.getAuthor());
			po.setContent(article.getContent());
			po.setTitle(article.getTitle());
			po.setTodo(article.getTodo());
			po.setType1(article.getType1());
			po.setChangeTime(new Date());
		}else{
			article.setCreateTime(new Date());
		}
		articleService.saveOrUpdate(po!=null?po:article);
		model.addAttribute("msg", "成功保存文章");
		model.addAttribute("tabId", tabId);
		return "success";
		//return "redirect:/index.html";
	}
	
	/**
	 * 编辑
	 * @param article
	 * @param tabId
	 * @param model
	 * @return
	 */
	@RequestMapping("/editUI")
	public String editUI(@ModelAttribute Article article,
			@RequestParam(value="tabId",required=true) String tabId,
			ModelMap model) {
 		if(article!=null && article.getId()!=null){
			Article po = (Article)articleService.getObjectById(article.getId(), Article.class);
			model.addAttribute("article", po);
		}
 		model.addAttribute("type1Map",Constants.getType1Map());
		model.addAttribute("tabId",tabId);
		return "edit";
	}
	
	/**
	 * 编辑
	 * @param article
	 * @param tabId
	 * @param model
	 * @return
	 */
	@RequestMapping("/viewArticle")
	public String viewArticle(@ModelAttribute Article article,
			@RequestParam(value="tabId",required=true) String tabId,
			ModelMap model) {
		if(article!=null && article.getId()!=null){
			Article po = (Article)articleService.getObjectById(article.getId(), Article.class);
			model.addAttribute("article", po);
		}
		model.addAttribute("type1Map",Constants.getType1Map());
		model.addAttribute("tabId",tabId);
		return "view";
	}
	
	/**
	 * 删除
	 * @param article
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	public String detele(@ModelAttribute Article article,
			ModelMap model) {
		int i = articleService.delById(article.getId(), article);
		model.addAttribute("result", i>0?true:false);
		log.info("删除成功，id【"+article.getId()+"】");
		return "js/result";
	}
	public ArticleService getArticleService() {
		return articleService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
}