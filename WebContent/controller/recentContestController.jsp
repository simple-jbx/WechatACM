<%@ page language="java"
	import="java.util.*,service.recentContestService,bean.contest,utils.StringUtils,java.util.List,java.util.Map,utils.List2JsonUtils, java.math.BigDecimal,
	com.alibaba.fastjson.JSON"
	pageEncoding="UTF-8"%>
<%
	recentContestService ojService = new recentContestService();
	List<Map<String, Object> > list = ojService.getData();
	String json = List2JsonUtils.list2Json2String(list);
	out.println(json);	
%>