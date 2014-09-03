package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.tool.Constants;
import com.tool.Date.TimeSerializer;

/**
 * 文章实体类
 * @author mei
 *
 */
@Entity
@Table(name="tbl_article")
public class Article implements Serializable,SaveDeleteInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5845013703743902743L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
	private Integer Id;
	
	@Column(name="title",length=255)
	private String title;
	
	@Column(name="content",length=65535)
	private String content;
	
	@Column(name="type1",length=255)
	private String type1;
	
	@Column(name="author",length=255)
	private String author;
	
	@Column(name="create_time",updatable=false)
	private Date createTime;
	
	@Column(name="change_time",insertable=false)
	private Date changeTime;
	
	@Column(name="view_count")
	private Integer viewCount;
	
	@Column(name="todo",length=10)
	private String todo;
	
	@Column(name="del")
	private Integer del;

	@Column(name="type1",insertable=false,updatable=false,length=255)
	private String type;
	
	@Column(name="create_time",insertable=false,updatable=false,length=255)
	private String createTimeEnd;
	
	public Article(){
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getTitle() {
		return title==null?"":title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content==null?"":content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	
	/**
	 * 栏目的键
	 * @return
	 */
	public String getType1() {
		return type1==null?"":type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	
	/**
	 * 根据栏目的键得到值
	 * @return
	 */
	public String getType() {
		return Constants.getType1Map(type1);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author==null?"":author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTodo() {
		return todo==null?"":todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	
	@JsonSerialize(using = TimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonSerialize(using =TimeSerializer.class)
	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
}
