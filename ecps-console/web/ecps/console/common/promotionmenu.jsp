<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="taglibs.jsp"%>
<head>
<title>促销活动</title>
<meta name="heading" content="<fmt:message key="mainMenu.heading" />" />
<meta name="menu" content="GrouponMenu"/>
</head>

	<h2><samp class="t03"></samp>团购</h2>
	<ul class="ul">
		<li><a href="${base}/groupon/listGroupon.form"><samp class="t05"></samp>团购列表</a></li>
		<li><a href="${base}/groupon/preAddGroupon.form"><samp class="t05"></samp>添加团购</a></li>
		<li><a href="${base}/groupon/heraldGroupon.form"><samp class="t05"></samp>预告管理</a></li>
	</ul>

	<h2><samp class="t03"></samp>秒杀</h2>
	<ul class="ul">
			<li><a href="${base}/secKill/listSecKill.form"><samp class="t05"></samp>秒杀列表</a></li>
			<li><a href="${base}/secKill/preAddSecKill.form"><samp class="t05"></samp>添加秒杀</a></li>
			<li><a href="${base}/secKill/heraldSecKill.form"><samp class="t05"></samp>预告管理</a></li>
	</ul>

