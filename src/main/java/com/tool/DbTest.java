package com.tool;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.entity.Article;

public class DbTest {
	public void testQuery(){
/*		DbUtils db = new DbUtils();
		String sql = "select * from article where 1=1";
		String paramValues[] = new String[]{"1","3"};
		List<?> list = db.query(sql,paramValues);
		for(int i=0;i<list.size();i++) {
			String str[] = (String[]) list.get(i);
			String data = "";
			for(int j=0;j<str.length;j++)
				data += str[j]+"   ";			
			System.out.println(data);
		}*/
	}
	
	@Test
	public void testJson(){
		Article article = new Article();
		article.setAuthor("abc");
		article.setTitle("title");
		article.setCreateTime(new Date());
		try {
			System.out.println(JsonUtil.getObjectMapper().writeValueAsString(article));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int abctestInsert(){
		DbUtils db = new DbUtils();
		String sql = " insert into tbl_article(title,text,author)values(?,?,?) ";
		String paramValues[] = new String[]{"中国河南下雨了","今日凌晨下中雨","海浪"};
		int i = db.save(sql, paramValues);
		return i;
	}
	
	public void testSaveOrUpdate(){
/*		DbUtils db = new DbUtils();
		String sql = "update LIKP_KC2 set KCM = ? where KCH=?";
		String paramValues[] = new String[]{"New_KCM","2"};
		boolean bl = db.saveOrUpdate(sql,paramValues);
		//this.assertEquals(bl, true);
		System.out.println(bl);*/
		
/*		DbUtils db1 = new DbUtils();
		String sql1 = "delete from LIKP_KC2 where KCH= ?";
		String paramValues1[] = new String[]{"4"};
		boolean bl1 = db1.saveOrUpdate(sql1,paramValues1);
		//this.assertEquals(bl, true);
		System.out.println(bl1); */
		
/*		DbUtils db2 = new DbUtils();
		String sql2 = "insert into LIKP_KC2 values(?,?)";
		String paramValues2[] = new String[]{"","cccdcc"};
		boolean bl2 = db2.saveOrUpdate(sql2,paramValues2);
		//this.assertEquals(bl, true);
		System.out.println(bl2);*/
	}
	
}
