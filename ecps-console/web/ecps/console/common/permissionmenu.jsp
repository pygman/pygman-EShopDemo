<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="taglibs.jsp"%>
<head>
<meta name="menu" content="permission"/>
</head>
<h2><samp class="t03"></samp>权限管理</h2>
<ul class="ul">
<li><a href="${base}/permission/user/listUser.form"><samp class="t05"></samp>用户管理</a></li>
<li><a href="${base}/permission/role/listRole.form"><samp class="t05"></samp>角色管理</a></li>
<li><a href="${base}/permission/perm/listPerm.form"><samp class="t05"></samp>权限配置</a></li>
</ul>
