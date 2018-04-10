<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<!-- 在岗时间系数规则-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<!-- <link href="../static/js/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css"> -->
<link href="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.css" rel="stylesheet">
<!-- <link href="../static/js/bootstrap/css/bootstrap-table.min.css"
	rel="stylesheet" type="text/css"> -->

<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<!-- <script src="../static/js/jQuery.js"></script>-->
<script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>
<!-- <script src="../static/js/bootstrap/js/bootstrap-table.min.js"></script> -->
<script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/locale/bootstrap-table-zh-CN.js"></script>
<!-- <script src="../static/js/bootstrap/js/bootstrap-table-zh-CN.js"></script>
 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- <script src="../static/js/bootstrap/js/bootstrap.min.js"></script>-->
<title>近期比赛数据</title>
</head>
<body>
		
	<table id="table">
	</table>
	<script>
	//操作栏的格式化 
	$(function(){
		$('#table').bootstrapTable({
		ajax : function (request) {
	        $.ajax({
	            type : "GET",
	            url : "../controller/recentContestController.jsp",
				contentType: "application/json;charset=utf-8",
				dataType:"json",
				data:'',
				jsonp:'callback',
	            success : function (msg) {	
					request.success({
	                    row : msg
	                });
	                $('#table').bootstrapTable('load', msg);
	            },
				error:function(){
					alert("载入出错");
				}
	        });
		},
	
			
			toolbar:'#toolbar',
			singleSelect:true,
			clickToSelect:true,	
			sortable: true, 
			sortName: 'id',
			sortOrder: "asc",
			pageSize: 10,
			pageNumber: 1,
			pageList: "[10, 15, 20, 50]",
			showRefresh: true,
			search: true, //搜索框
			pagination:true, //底部显示分页条
			strictSearch: false, //模糊搜索
			uniqueId: "id", //每一行的唯一标识，一般为主键列
			striped: true,  
            cache: false,
            buttonsAlign:"right",  //按钮位置  
			columns: [{
				field: "state",
				checkbox:true,
				visible: false
			},{
				field: 'id',
				visible: false
			},{
				field: 'rowid',
				title: '序号',
				formatter: function(value, row, index) {
					row.rowid = index;
					return index+1;
				}
			},{
				field: 'oj',
				title: 'OJ名称',
				switchable: true,
				sortable: true
			},{
				field: 'name',
				title: '比赛名称',
				switchable: true,
				formatter: function(value, row, index) {
					return "<a href="+row.url+">"+row.name+"</a>";
				}
			},{
				field: 'time',
				title: '比赛时间',
				switchable: true,
				sortable: true
			}, {
				field: 'week',
				title: '星期',
				switchable: true,
			},{
				field: 'acess',
				title: '比赛属性',
				switchable: true,
			}
		]
	});
})
	</script>
</body>
</html>