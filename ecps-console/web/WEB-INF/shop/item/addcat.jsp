<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/ecps/console/common/taglibs.jsp"%>
<head>
<title>添加分类_类目管理_商品管理</title>
<meta name="heading" content="<fmt:message key='mainMenu.heading'/>"/>
<meta name="cat" content="catName"/>
<script type="text/javascript">
function backList(url){
	document.location=url;
}
$(document).ready(function(){
	$("#form111").submit(function(){
		var isSubmit = true;
		var parentId=$("#parentId").val();//判断是那种类型
		//if(parentId==0){
		//	alert("请选择上级类目");
		//	isSubmit = false;
		//}
		$(this).find("[reg],[url]").each(function(){
			
			if(typeof($(this).attr("reg")) != "undefined"){
				if(!clientValidate($(this))){
					isSubmit = false;
				}
			}
		});
		return isSubmit;
	});
});
</script>
</head>
<body id="main">

<div class="frameL"><div class="menu icon">
    <jsp:include page="/${system}/common/itemmenu.jsp"/>
</div></div>

<div class="frameR"><div class="content">
	<c:if test="${rootId==1 }">
		<c:url value="/${system}/item/cat/listCat.form?catType=${rootId }" var="backurl"/>
	</c:if>
	<c:if test="${rootId==2 }">
		<c:url value="/${system}/card/listCardCat.form?catType=${rootId }" var="backurl"/>
	</c:if>
    <div class="loc icon"><samp class="t12"></samp>
    <fmt:message key='menu.current.loc'/>：<fmt:message key='ItemMgmtMenu.title'/>&nbsp;&raquo;&nbsp;<a href="<c:url value="/${system}/item/cat/listCat.form"/>?catType=1" title="分类管理">分类管理</a>&nbsp;&raquo;&nbsp;<fmt:message key='menu.addCat'/>
   	<a href="${backurl }" title="返回分类管理" class="inb btn80x20">返回分类管理</a>
    </div>
	<form id="form111" name="form1" action="${path }/item/cat/addCat.form" method="post">
	    <div class="edit set">
            <c:url value="/${system}/item/cat/checkCatName.form" var="check"></c:url>
            <p><label><samp>*</samp>分类名称：</label><input type="text" id="catName" name="catName" value="${cat.catName}" class="text state" reg="^[a-zA-Z0-9\u4e00-\u9fa5]{1,40}$" tip="必须是中英文或数字字符，长度1~40"/>
            <span id="nameInfo"><c:out value="${catNameInfo1}"/></span></p>
            
      		<p><label><samp>*</samp>上级分类：</label><ui:select name="parentId" list="listcat" currentValue="${cat.parentId }" rootId="0" defaultvalue="0" defaulttext="添加为顶级分类"/>
      		</p>
      		
      		<p><label><samp>*</samp>标识：</label><input type="text" name="mark"  value="${cat.mark}" class="text state" reg="^[a-zA-Z0-9]{1,20}$" tip="必须是英文或数字字符，长度1~20"/>
      		<span class="pos" ><c:out value="${catNameInfo2}"/></span></p>
      		
            <p><label><samp>*</samp>排序：</label><input type="text" name="catSort" value="${cat.catSort}"  class="text state" reg="^(?!0)(?:[0-9]{1,3})$" tip="必须是正整数，大于等于1小于1000"/>
            <span class="pos" ></span></p>
                 
      		<p><label>页面关键词：</label><input type="text" name="keywords"  value="${cat.keywords}" class="text state" reg="^[a-zA-Z0-9\u4e00-\u9fa5]{0,40}$" tip="必须是中英文或数字字符，长度大于0小于等于40个字符 "/>
      		<span></span></p>
      		    
            <p><label class="alg_t" >页面描述：</label><textarea rows="4" cols="21" name="catDesc" class="are" reg="^[a-zA-Z0-9\u4e00-\u9fa5]{0,80}$" tip="必须是中英文或数字字符，长度大于0小于等于80个字符   ">${cat.catDesc}</textarea>
            <span class="pos"></span></p>
            
            <p><label><samp>*</samp>是否显示：</label><input type="radio" value="1" name="isDisplay"   checked/>是&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" value="0" name="isDisplay" <c:if test='${cat.isDisplay == 0}'> checked</c:if>/>否
            </p>   
            <p><label>&nbsp;</label>
            	<input type="submit" class="hand btn83x23" value="完成" />
            	
            	<input type="hidden" name="catType" value="${rootId }"/>
           		<input type="button" class="hand btn83x23b" id="reset1" value='<fmt:message key="button.cancel"/>' onclick="backList('${backurl}')"/>
           	</p>
        </div>
	</form>

    <div class="loc">&nbsp;</div>

</div></div>
</body>

