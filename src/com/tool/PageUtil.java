package com.tool;

public class PageUtil {
	private int page;// 当前页
	private Integer pagesize = 200;// 每页显示记录数 常量
	private int total;// 总记录数
	private String sortname;//
	private String sortorder;

	//private int totalPage;// 总页数
	//private int firstPage;// 第一页
	//private int lastPage;// 最后一页
	//private int prePage;// 上一页
	//private int nextPage;// 下一页
	//private int position;// 从第几条信息记录 开始查询
//	private Properties properties;

//	public void initPagesize() {
//		properties = new Properties();
//		InputStream loadFile = this.getClass().getResourceAsStream(
//				"/com/dada/config/conn.properties");
//		try {
//			properties.load(loadFile);
//
//			// 从配置文件读取 每页显示记录数 常量
//			pagesize = Integer.parseInt(properties.getProperty("pagesize")
//					.trim());
//			System.out.println("pagesize:" + pagesize);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public PageUtil() {
//		initPagesize();// 一定放在此构造方法的第一行
		//this.total = total;
	}

	public PageUtil(int page, int total) {
//		initPagesize();// 一定放在此构造方法的第一行
		this.total = total;
		this.page = page;
		// initPagesize();
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public int getPage() {
		if (this.page < 1)
			this.page = 1;
		if (this.page > this.getTotalPage())
			this.page = this.getTotalPage();

		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPage() {
		if (this.getTotal() % pagesize == 0)
			return this.getTotal() / pagesize;
		return this.getTotal() / pagesize + 1;
	}

	public Integer getFirstPage() {
		return 1;
	}

	public int getLastPage() {
		return this.getTotalPage();
	}

	public int getPrePage() {
		if (this.getPage() - 1 <= 0)
			return 1;
		return this.getPage() - 1;
	}

	public int getNextPage() {
		if (this.getPage() + 1 >= this.getTotalPage())
			return this.getTotalPage();
		return this.getPage() + 1;
	}


	public int getPosition() {
//		return (this.getPage() - 1) * pagesize + 1;//
		return (this.getPage() - 1) * pagesize ;//
	}
	public static void main(String args[]){
		PageUtil pg = new PageUtil();
		System.out.println(pg.getPage());
	}
}
