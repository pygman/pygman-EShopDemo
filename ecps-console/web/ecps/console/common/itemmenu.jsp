<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="taglibs.jsp"%>
<head>
<title>商品管理</title>

</head>
<h2><samp class="t03"></samp>商品管理</h2>
<ul class="ul">
<li><a href="${path}/item/listItem.form?showStatus=1&auditStatus=1"><samp class="t05"></samp>商品录入/上下架</a></li>
<li><a href="${path}/item/listAuditItem.form?auditStatus=0&showStatus=1"><samp class="t05"></samp>商品审核</a></li>
<li><a href="${base}/activity/listOfferGroup.form?showStatus=1&labelStatus=4"><samp class="t05"></samp>营销案管理</a></li>
<li><a href="${base}/activity/listAudit.form?auditStatus=0&labelStatus=1"><samp class="t05"></samp>营销案审核</a></li>
<li><a href="${base}/activity/listoffer.form"><samp class="t05"></samp>营销案档次</a></li>
<li><a href="${path}/feature/selectAllFeature.form"><samp class="t05"></samp>属性管理</a></li>
<li><a href="${path}/brand/selectBrandAll.form"><samp class="t05"></samp>品牌管理</a></li>
</ul>

