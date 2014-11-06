<%@ include file="/ecps/console/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<title>属性管理_类目管理_商品管理</title>
<meta name="heading" content="商品管理"/>
<meta name="tag" content="tagName"/>
<script type="text/javascript" src="<c:url value='/${system}/res/js/jquery.tablesorter.js'/>"></script>
<script type="text/javascript">
	
</script>
</head>
<body id="main">
<div class="frameL"><div class="menu icon">
    <jsp:include page="/${system}/common/itemmenu.jsp"/>
</div></div>

<div class="frameR"><div class="content">

<div class="loc icon"><samp class="t12"></samp>当前位置：商品管理&nbsp;&raquo;&nbsp;<a href="<c:url value="/${system}/item/cat/listCatform?catType=1"/>" >类目管理</a>&nbsp;&raquo;&nbsp;<span class="gray">属性管理</span><a href="<c:url value="/${system}/item/cat/listCatform?catType=1"/>" class="inb btn120x20">返回类目管理</a></div>

<form id="form1" name="form1" action="${base}/item/feature/listFeatureform" method="post">
    
    
    
    <div class="page_c">
       
        <span class="r inb_a">
            <a href="${path }/shop/item/addfeature.jsp" title="添加" class="btn80x20">添加</a>
        </span>
    </div>
    
	<table id="sortTable" cellspacing="0" summary="" class="tab">
		<thead>
			<tr>
				<th>属性名称</th>
				<th>属性类型</th>
				<th>可选值</th>
				<th>规格</th>
				<th>筛选条件</th>
				<th>显示</th>
				<th>排序</th>
				<th>操作</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${fList }" var="feature">
				<tr>
					<td>${feature.featureName }</td>
					
					
					<td>
						<c:if test="${feature.inputType == 1 }">下拉框</c:if>
						<c:if test="${feature.inputType == 2 }">单选</c:if>
						<c:if test="${feature.inputType == 3 }">多选</c:if>
						<c:if test="${feature.inputType == 4 }">文本</c:if>
					</td>
					
					
					<td class="nwp">${feature.selectValues }</td>
					
					<td>
						<c:if test="${feature.isSpec == 0 }">否</c:if>
						<c:if test="${feature.isSpec == 1 }">是</c:if>
					</td>
					
					
					
					
					
					<td>
						<c:if test="${feature.isSelect == 0 }">否</c:if>
						<c:if test="${feature.isSelect == 1 }">是</c:if>
					</td>
					<td>
						<c:if test="${feature.isShow == 0 }">否</c:if>
						<c:if test="${feature.isShow == 1 }">是</c:if>
					</td>
					
					
					<td>${feature.featureSort }</td>
					<td><a href="#">编辑</a>
					<a href="#">删除</a>
					
					</td>
				</tr>
			</c:forEach>
			
			
				

			
		</tbody>
	</table>
	
	
</form>
</div></div>
</body>


