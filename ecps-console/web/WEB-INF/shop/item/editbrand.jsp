<%@ include file="/ecps/console/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<title>修改品牌_品牌管理_商品管理</title>
<meta name="heading" content="品牌管理"/>
<meta name="tag" content="tagName"/>
<script type="text/javascript" src="<c:url value='/${system}/res/js/jquery.form.js'/>"></script>
<script type="text/javascript">
$(function(){
	$("#form111").submit(function(){
		//设置表单阻止的标志
		var isSubmit = true;
		//非必填项的校验
		$(this).find("[reg2]").each(function(){
			//获得你填入的值
			var val = $(this).val();
			//获得当前文本区的正则表达式的字符串
			var reg = $(this).attr("reg2");
			//获得当前文本区的提示信息
			var tip = $(this).attr("tip");
			//创建正则表达式对象
			var regObj = new RegExp(reg);
			//如果验证失败
			if(!regObj.test(val)){
				//设置阻止标志
				isSubmit = false;
				//显示提示信息
				$(this).next("span").html(tip);
				//跳出循环，在jquery中跳出循环不是break;也不是return;而是return false;
				//而且return false 必须在each的直接下层
				
				return false;
			}else{
				//隐藏验证成功的提示信息
				$(this).next("span").html("");
				
			}
		});
		//非必填项的校验
		$(this).find("[reg1]").each(function(){
			//获得你填入的值
			var val = $(this).val();
			//获得当前文本区的正则表达式的字符串
			var reg = $(this).attr("reg1");
			//获得当前文本区的提示信息
			var tip = $(this).attr("tip");
			//创建正则表达式对象
			var regObj = new RegExp(reg);
			//如果验证失败
			if(val != null && $.trim(val) != "" && !regObj.test(val)){
				//设置阻止标志
				isSubmit = false;
				//显示提示信息
				$(this).next("span").html(tip);
				//跳出循环，在jquery中跳出循环不是break;也不是return;而是return false;
				//而且return false 必须在each的直接下层
				
				return false;
			}else{
				//隐藏验证成功的提示信息
				$(this).next("span").html("");
			}
		});
		if(isSubmit){
			tipShow("#refundLoadDiv");
		}
		return isSubmit;
	});
	
	
	//离开焦点的校验
	$("#form111").find("[reg2]").blur(function(){
		//获得你填入的值
		var val = $(this).val();
		//获得当前文本区的正则表达式的字符串
		var reg = $(this).attr("reg2");
		//获得当前文本区的提示信息
		var tip = $(this).attr("tip");
		//创建正则表达式对象
		var regObj = new RegExp(reg);
		//如果验证失败
		if(!regObj.test(val)){
			//显示提示信息
			$(this).next("span").html(tip);
		}else{
			//隐藏验证成功的提示信息
			$(this).next("span").html("");
			
			
		}
	});
	
	
	//非必填项的校验
	$("#form111").find("[reg1]").blur(function(){
		//获得你填入的值
		var val = $(this).val();
		//获得当前文本区的正则表达式的字符串
		var reg = $(this).attr("reg1");
		//获得当前文本区的提示信息
		var tip = $(this).attr("tip");
		//创建正则表达式对象
		var regObj = new RegExp(reg);
		//如果验证失败
		if(val != null && $.trim(val) != "" && !regObj.test(val)){
			//显示提示信息
			$(this).next("span").html(tip);
			//跳出循环，在jquery中跳出循环不是break;也不是return;而是return false;
			//而且return false 必须在each的直接下层
		}else{
			//隐藏验证成功的提示信息
			$(this).next("span").html("");
		}
	});
	
});
function submitUpload(){
	var option = {
			url:"${path}/upload/uploadPic.form",//一旦指定了url那么表单中的action的url就被覆盖了，如果不指定url就用表单的原有的url
			dataType:"text",
			data:{
				fileName:"imgsFile"
			},
			success:function(responseText){
				//把json格式的字符串解析成json对象
				var jsonObj = $.parseJSON(responseText);
				//回显图片
				$("#imgsImgSrc").attr("src",jsonObj.realPath);
				//设置相对路径
				$("#imgs").val(jsonObj.relativePath);
			},
			error:function(){
				alert("系统错误");
			}
	};
	$("#form111").ajaxSubmit(option);
	
}
</script>
</head>
<body id="main">
<div class="frameL"><div class="menu icon">
    <jsp:include page="/${system}/common/itemmenu.jsp"/>
</div></div>

<div class="frameR"><div class="content">

	<c:url value="/${system}/item/brand/listBrand.form" var="backurl"/>
	
	<div class="loc icon"><samp class="t12"></samp>当前位置：商品管理&nbsp;&raquo;&nbsp;<a href="<c:url value="/${system }/item/brand/listBrand.form"/>" title="品牌管理">品牌管理</a>&nbsp;&raquo;&nbsp;<span class="gray">添加品牌</span>
    <a href="<c:url value="/${system }/item/brand/listBrand.form"/>" title="返回品牌管理" class="inb btn80x20">返回品牌管理</a>
    </div>
	<form id="form111" name="form111" action="${path }/brand/updateBrand.form" method="post" enctype="multipart/form-data">
		<div class="edit set">
			<input type="hidden" value="${brand.brandId }" name="brandId">
			<p><label><samp>*</samp>品牌名称：</label><input type="text" id="brandName" readonly="readonly" value="${brand.brandName }" name="brandName" class="text state" reg2="^[a-zA-Z0-9\u4e00-\u9fa5]{1,20}$" tip="必须是中英文或数字字符，长度1-20"/>
				<span></span>
			</p>
            <p><label class="alg_t"><samp>*</samp>品牌LOGO：</label><img id='imgsImgSrc' src="${image_path }${brand.imgs}" height="100" width="100" />
            </p>
             <p><label></label><input type='file' size='27' id='imgsFile' name='imgsFile' class="file" onchange='submitUpload()' /><span id="submitImgTip" class="pos">请上传图片宽为120px，高为50px，大小不超过100K。</span>
                <input type='hidden' id='imgs' name='imgs' value='${brand.imgs }' reg2="^.+$" tip="亲！您忘记上传图片了。" /><span></span>
			</p> 
				
			<p><label>品牌网址：</label><input type="text" name="website" value="${brand.website }" class="text state" maxlength="100"  tip="请以http://开头" reg1="http:///*"/>
				<span class="pos">&nbsp;</span>
			</p>
			<p><label class="alg_t">品牌描述：</label><textarea rows="4" cols="45" name="brandDesc"  class="are" reg1="^(.|\n){0,300}$" tip="任意字符，长度0-300">${brand.brandDesc }</textarea>
				<span class="pos">&nbsp;</span>
			</p>
			<p><label>排序：</label><input type="text" value="${brand.brandSort }" id="brandSort" reg1="^[1-9][0-9]{0,2}$" tip="排序只能输入1-3位数的正整数" name="brandSort" class="text small"/>
				<span class="pos">&nbsp;</span>
			</p>
			<p><label>&nbsp;</label><input type="submit" name="button1" d class="hand btn83x23" value="完成" /><input type="button" class="hand btn83x23b" id="reset1" value='取消' onclick="backList('${backurl}')"/>
			</p>
		</div>
	</form>
    <div class="loc">&nbsp;</div>

</div></div>
</body>

