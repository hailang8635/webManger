var manager, g, navtab, tabHeight;
$(function() {
	$("#layout1").ligerLayout({
		minLeftWidth : 80,
		minRightWidth : 80,
		leftWidth :200,
		topHeight:25,
		bottomHeight:25,
		isRightCollapse : true
	});
// 		$(".l-layout-center").find(".l-layout-header").hide();
	
	var layout1Height = $("#layout1").children()[0].style.height.replace("px","")*1-25;
	$("#accordion1").ligerAccordion({
		height : layout1Height,
		changeHeightOnResize:true
	});

	tabHeight = $("#centerDiv").height();//-2;
	$("#navtab1").ligerTab({
		changeHeightOnResize :true,
		dblClickToClose :true
	});
    
	//左边栏菜单事件
	navtab = $("#navtab1").ligerGetTabManager();

	//数据
    g = manager = $("#maingrid4").ligerGrid({
        columns: [ 
            { display: '主键', name: 'id', minWidth: 10 },
            { display: '标题', name: 'title', minWidth: 50 },
            { display: '栏目', name: 'type', width: 200, align: 'left'},
//             { display: '主键', name: 'id', align: 'left', width: 120 } ,
//             { display: '内容', name: 'text', width: 100, align: 'left', frozen: true },
            { display: '作者', name: 'author', width: 200, align: 'left' },
            { display: '创建时间', name: 'createTime', width: 200, align: 'left' }
        ],
        url:basePath+'/list.json',
        onToFirst: turnPage(this),                     //第一页，可以通过return false来阻止操作
        onToPrev: turnPage(-1),                      //上一页，可以通过return false来阻止操作
        onToNext: turnPage(+1),                      //下一页，可以通过return false来阻止操作
        onToLast: turnPage(),
        onDblClickRow:f_showDetail, 
        //data: data, 
        pageSize: 20,
        //where : f_getWhere(),
        sortName: 'id',
        //width: '100%', 
        height: '93%',//tabHeight-57,
        checkbox: true,rownumbers:true,
        fixedCellHeight:false,
        toolbar: { items: [
           { text: '增加', click: f_add, icon: 'add' },
           { line: true },
           { text: '修改', click: f_edit, icon: 'modify' },
           { line: true },
           { text: '删除', click: f_delete, img: basePath+'/js/ligerUI/skins/icons/delete.gif' }
        ]}
    });
    $("#pageloading").hide();
    //console.info(tabHeight);
    
});

//翻页
function turnPage(obj){
	//console.info(obj);
}
function reloadData(){
	//$.getJSON("index.json", function(json){
	//  alert("JSON Data: " + json.Rows+""+json.Total);
	  //data = {Rows:json.articleJson, Total:json.pageUtil.totalRecord};
	//  manager.reload(data);
	//});
	g._setData();
}

//公用alert样式
var alert = function (t){ $.ligerDialog.warn(t.toLocaleString()); }

//操作Tab页
function f_click(type,title,tabItemId,url)
{
    switch (type)
    {
        case "moveToPrevTabItem":
            navtab.moveToPrevTabItem();
            break;
        case "addTabItem":
        	if( navtab.isTabItemExist(tabItemId)){
        		navtab.selectTabItem(tabItemId);
        	}else{
        		navtab.addTabItem({ 
        			url   : url,
        			tabid :tabItemId,
        			height:tabHeight
     			});
        		var tab = liger.get("navtab1");
        		tab.setHeader(tabItemId, title);
        	}
            //$.ligerDialog.prompt('请输入网址', 'http://www.baidu.com', function (yes, value)
            //{
            //    if (yes) navtab.addTabItem({ url: value });
            //});
            break;
    }
}

function getSelected() { 
	if(manager){
		var row = manager.getSelectedRow();
		if (!row) { alert('请选择行'); return; }
		//console.debug("--------------getSelected"+JSON.stringify(row));
		return row;
	}
}

// 增删查改
function f_add(item){
	f_click('addTabItem','添加文章','articleAdd', basePath+"/editUI.html?tabId=articleAdd");
	//$("#tab1_div").html("");
	//$("#tab1_div").load(basePath+"/addUI.html");
}

function f_edit(item){
	f_click('addTabItem','编辑文章','articleEdit', basePath+"/editUI.html?tabId=articleEdit&id="+getSelected().id);
	//$("#tab1_div").html("");
	//$("#tab1_div").load(basePath+"/editUI.html?id="+getSelected().id);
}
function f_delete(item){
	var id = getSelected().id;
	$.ajax({
		url: basePath+'/delete.json',
		data:{id:id},
		async:false,
		dataType:'json',
		success:function(msg){
			if(msg.result){
    			//manager.deleteSelectedRow();
    			alert("删除成功！");
    			f_search();
			}else{
    			alert("删除失败！");
			}
		}
	});
}

function f_showDetail(data, rowindex, rowobj){
	f_click('addTabItem','查看文章','articleView', basePath+"/viewArticle.html?tabId=articleView&id="+data.id);
}

function f_search()
{
    //g.options.data = $.extend(true, {}, data);
    //g.loadData(f_getWhere());
    g.loadData(true);

}
