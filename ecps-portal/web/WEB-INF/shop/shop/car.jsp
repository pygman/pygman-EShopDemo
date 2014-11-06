<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="author" content="http://www.asiainfo-linkage.com/" />
<meta name="copyright" content="asiainfo-linkage.com 版权所有，未经授权禁止链接、复制或建立镜像。" />
<meta name="description" content="中国移动通信 name.com"/>
<meta name="keywords" content="中国移动通信 name.com"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes, minimum-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
<title>我的购物车_移动商城_中国移动通信</title>
<link rel="icon" href="/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="search" type="application/opensearchdescription+xml" href="../opensearch.xml" title="移动购物" />
<link rel="stylesheet" href="${path }/res/css/style.css" />
<script src="${path }/res/js/jquery.js"></script>
<script src="${path }/res/js/getUser.js"></script>
<script src="${path }/res/js/com.js"></script>
<script type="text/javascript">
$(function(){

	$("#loginAlertIs").click(function(){
		tipShow('#loginAlert');
	});

	$("#promptAlertIs").click(function(){
		tipShow('#promptAlert');
	});

	$("#transitAlertIs").click(function(){
		tipShow('#transitAlert');
	});

	$(".form li input").blur(function(){
		var type = $(this).attr("name");
		var val = $(this).val();
		if(val == null || $.trim(val) == ""){
			if(type == "username"){
				$("#loginAlertError").html("请输入用户名");
			}else if(type == "password"){
				$("#loginAlertError").html("请输入密码");
			}else if(type == "captcha"){
				$("#loginAlertError").html("请输入验证码");
			}
			$("#loginAlertError").show(500);
		}else{
			$("#loginAlertError").html("");
			$("#loginAlertError").hide(500);
		}
	});
});
function addNum(skuId, quantity){
	if(validStock(skuId, quantity)){
		window.location.href = "${path}/cart/addNum.form?skuId="+skuId;
	}
}

function validStock(skuId, quantity){
	var isOk = true;
	$.ajax({
		url:"${path}/cart/validStockByNum.form",
		type:"post",
		dataType:"text",
		async:false,
		data:{
			skuId:skuId,
			quantity:quantity
		},
		success:function(responseText){
			if(responseText == "no"){
				isOk = false;
			}
		},
		error:function(){
			alert("系统错误");
			isOk = false;
		}
	});
	return isOk;
}

function trueBuy(){
	$.ajax({
		url:"${path}/user/getUser.form",
		type:"post",
		dataType:"text",
		success:function(responseText){
			//把json格式的字符串转换成js中的json对象
			var userObj = $.parseJSON(responseText);
			if(userObj.user == null){
				tipShow("#loginAlert");
			}else{
				var stockTip = validStockAllItem();
				if(stockTip != "success"){
					alert(stockTip);
				}else{
					window.location.href = "${path}/order/toSubmitOrder.form";
				}
			}
		},
		error:function(){
			//1.后台抛异常会走到error，
			//2.404
			alert("系统错误");
		}
	});
	
}
function clearCart(){
	window.location.href = "${path}/cart/clearCart.form";
}

function changeImage(){
	var path = "${path}/user/getImage.form?date="+new Date();
	$("#captchaImage").attr("src",path);
}

function ajaxLogin(){
	var username = $("#username").val();
	var password = $("#password").val();
	var captcha = $("#captcha").val();
	$.ajax({
		url:"${path}/user/ajaxLogin.form",
		type:"post",
		dataType:"text",
		data:{
			username:username,
			password:password,
			captcha:captcha
		},
		success:function(responseText){
			if(responseText == "cap_error"){
				$("#loginAlertError").html("验证码错误");
				$("#loginAlertError").show(500);
			}else if(responseText == "user_error"){
				$("#loginAlertError").html("用户名或者密码错误");
				$("#loginAlertError").show(500);
			}else{
				$("#loginAlertIs").html(username);
				tipHide("#loginAlert");
				var stockTip = validStockAllItem();
				if(stockTip != "success"){
					alert(stockTip);
				}else{
					window.location.href = "${path}/order/toSubmitOrder.form";
				}
			}
		},
		error:function(){
			//1.后台抛异常会走到error，
			//2.404
			alert("系统错误");
		}
	});
}

function validStockAllItem(){
	var result = "success";
	$.ajax({
		url:"${path}/cart/validStockAllItem.form",
		type:"post",
		dataType:"text",
		async:false,
		success:function(responseText){
			result = responseText;
		},
		error:function(){
			//1.后台抛异常会走到error，
			//2.404
			alert("系统错误");
		}
	});
	return result;
}
</script>
</head>
<body>
<div id="tipAlert" class="w tips">
	<p class="l">本网站将于4月11日12:00进行系统维护，维护期间，本站将暂停业务办理等相关业务，敬请见谅。</p>
	<p class="r"><a href="javascript:void(0);" title="关闭" onclick="$('#tipAlert').hide();"></a></p>
</div>

<div class="bar"><div class="bar_w">
	<p class="l">
		<!-- 未登录 -->
		<b class="l">
			<a href="#" title="个人客户" class="here">个人客户</a>
			<a href="#" title="企业客户">企业客户</a>
		</b>
		<span class="l">
			欢迎来到中国移动！<a href="javascript:void(0);" title="登录" id="loginAlertIs" class="orange"><samp>[</samp>请登录<samp>]</samp></a>&nbsp;<a href="passport/register.html" title="免费注册">免费注册</a>
			<a href="javascript:void(0);" id="promptAlertIs" title="promptAlert">promptAlert</a>
			<a href="javascript:void(0);" id="transitAlertIs" title="transitAlert">transitAlert</a>
		</span>
		<!-- 登录后
		<span class="l">
			您好，<a href="passport/personalInfo.html" title="13717782727">13717782727</a>！&nbsp;&nbsp;&nbsp;<a href="#" title="我的账户" class="blue">我的账户</a>&nbsp;&nbsp;&nbsp;<a href="#" title="我要办理" class="blue">我要办理</a>&nbsp;&nbsp;<a href="passport/loginOut.html" title="退出" class="orange"><samp>[</samp>退出<samp>]</samp></a>
		</span>
		-->
	</p>
	<ul class="r uls">
	<!--
	<li class="dev"><a href="#" title="我的订单">我的订单</a></li>
	<li class="dev"><a href="#" title="我的收藏">我的收藏</a></li>
	<li class="dev"><a href="#" title="帮助中心">帮助中心</a></li>
	-->
	<li class="dev"><a href="#" title="在线客服">在线客服</a></li>
	<li class="dev"><a href="#" title="关于中国移动">关于中国移动</a></li>
	<li class="dev after"><a href="#" title="English">English</a></li>
	
	</li>
	</ul>
</div></div>

<div class="w header bor_h">
	<h1><a href="http://www.bj.10086.cn" title="中国移动通信">中国移动通信</a></h1>
	<div class="area">
		<dl>
			<dt>贵州</dt>
		</dl>
	</div>
	<p title="移动改变生活"><span>移动</span>改变生活<samp>&gt;&gt;</samp></p>
</div>

<ul class="ul step st3_1">
<li title="1.查看购物车" class="here">1.查看购物车</li>
<li title="2.填写核对订单信息">3.填写核对订单信息</li>
<li title="4.成功提交订单">4.成功提交订单</li>
</ul>

<div class="w ofc case">

	<div class="confirm">
		<div class="tl"></div><div class="tr"></div>
		<div class="ofc pb40">

			<div class="page">
				<b class="l f14 blue pt48">
					我挑选的商品：
				</b>
				<ul class="ul exp r">
					<li class="n01"><b title="行货正品">行货正品</b></li>
					<li class="n02"><b title="全场包邮">全场包邮</b></li>
					<li class="n03"><b title="提供发票">提供发票</b></li>
				</ul>
			</div>
			

			<table cellspacing="0" summary="" class="tab tab4">
			<thead>
			<tr>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>规格</th>
			<th>单价（元）</th>
			<th>数量</th>
			<th>小计（元）</th>
			<th>操作</th>
			</tr>     
			</thead>
			<tbody>
				<c:forEach items="${cartList }" var="cart">
					<tr class="over">
						<td>${cart.sku.item.itemNo }</td>
						<td class="nwp pic">
							<ul class="uls">
								<li>
									<a href="#" title="摩托罗拉ME525" class="pic"><img src="${image_path }${cart.sku.item.imgSize1}" alt="摩托罗拉ME525" /></a>
									<dl>
										<dt><a href="#" title="摩托罗拉ME525">${cart.sku.item.itemName }</a></dt>
									</dl>
								</li>
							</ul>
						</td>
						<td>
							<c:forEach items="${cart.sku.specList }" var="spec">
								${spec.specValue }
							</c:forEach>
						</td>
						<td>￥${cart.sku.skuPrice }</td>
						<td>
							<a href="javascript:void(0);" title="减" class="inb arr">-</a>
							<input type="text" class="txts" size="1" name="" value="${cart.quantity }" />
							<a href="javascript:void(0);" onclick="addNum(${cart.skuId},${cart.quantity })" title="加" class="inb arr">+</a></td>
						<td>￥${cart.sku.skuPrice*cart.quantity }</td>
						<td class="blue"><a href="javascript:void(0);" title="删除">删除</a><br /><a href="javascript:void(0);" title="收藏">收藏</a></td>
					</tr>
				</c:forEach>
				
				
			</tbody>
			</table>


			<div class="page">
				<span class="l">
					<input type="button" value="继续购物" title="继续购物"  class="hand btn100x26c" />
					<input type="button" value="清空购物车"  title="清空购物车" class="hand btn100x26c" onclick="clearCart()" />
				</span>
				<span class="r box_gray">
					<dl class="total">
						<dt>购物车金额小计：<cite>(共<var id="totalNum"><c:out value="${itemNum }"/></var>个商品)</cite></dt>
						<dd><em class="l">商品金额：</em>￥<var id="totalMoney"><fmt:formatNumber value="${totalPrice }" pattern="#0.00"/></var></dd>
						<dd><em class="l">运费：</em>￥<var><c:out value="0.00"/></var></dd>
						<dd class="orange"><em class="l">应付总额：</em>￥<var id="totalMoney1"><fmt:formatNumber value="${totalPrice }" pattern="#0.00"/></var></dd>
						<dd class="alg_c"><input id="settleAccountId" type="button" value="结算" class="hand btn136x36a" onclick="trueBuy();"/></dd>
					</dl>
				</span>
			</div>

			
		</div>
		<div class="fl"></div><div class="fr"></div>
	</div>

</div>

<div class="w footer">
	<p><a href="#" title="新闻公告">新闻公告</a><samp>|</samp><a href="#" title="法律声明">法律声明</a><samp>|</samp><a href="#" title="诚招英才">诚招英才</a><samp>|</samp><a href="#" title="联系我们">联系我们</a><samp>|</samp><a href="#" title="采购信息">采购信息</a><samp>|</samp><a href="#" title="企业合作">企业合作</a><samp>|</samp><a href="#" title="站点导航">站点导航</a><samp>|</samp><a href="#" title="网站地图">网站地图</a></p>
	<p>掌上营业厅：<a href="#" title="掌上营业厅：wap.10086.cn">wap.10086.cn</a>&nbsp;&nbsp;语音自助服务：10086&nbsp;&nbsp;短信营业厅：10086&nbsp;&nbsp;<a href="http://www.bj.10086.cn/index/10086/channel/index.shtml">自助终端网点查询</a>&nbsp;&nbsp;<a href="http://www.bj.10086.cn/index/10086/channel/index.shtml">满意100营业厅网点查询</a>&nbsp;&nbsp;<a href="http://www.bj.10086.cn/index/10086/download/index.shtml">手机客户端下载</a></p>
	<p><a href="#" title="京ICP备05002571" class="inb i18x22"></a>&nbsp;京ICP备05002571<samp>|</samp>中国移动通信集团&nbsp;版权所有</p>
</div>

<div id="loginAlert" class="alt login" style="display:none">
	<h2 class="h2"><em title="登录">登录</em><cite></cite></h2>
	<a href="javascript:void(0);" id="loginAlertClose" class="close" title="关闭"></a>
	<div class="cont">
		<ul class="uls form">
		<li id="loginAlertError" class="errorTip" style="display:none"></li>
		
		<li>
			<label>用户名：</label>
			<span class="bg_text">
				<input type="text" maxlength="50" vld="{required:true}" name="username" id="username" reg1="^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$" desc="用户名长度不超过50个，必须是邮箱格式！" />
				<em id="userNameLabel" class="def">请输入手机号码</em>
			</span>
			<span class="word"><a title="免费注册" href="/ecps-portal/ecps/portal/register.form">免费注册</a></span>
		</li>
		
		<li><label>服务密码：</label>
			<span class="bg_text"><input type="password" vld="{required:true}" maxlength="20" name="password" id="password" value="" reg1="^.{6,20}$" desc="密码长度范围为6-20，允许为中英文、数字或特殊字符！" /></span>
		</li>
		<li>
			<label for="captcha">验 证 码：</label>
			<span class="bg_text small"><input type="text" vld="{required:true}" maxlength="7" name="captcha" id="captcha" value="" reg1="^\w{6}$" desc="验证码不正确" /></span>
			<img alt="换一张" id="captchaImage" class="code" onclick="changeImage();" src="${path }/user/getImage.form" /><a href="#" onclick="changeImage();" title="换一张">换一张</a>
		</li>
		<li class="gray"><label>&nbsp;</label><input type="checkbox" name="">记住我的手机号码</li>
		<li><label>&nbsp;</label><input type="button" id="loginSubmit" class="hand btn66x23" value="登 录" onclick="ajaxLogin()" ><a title="忘记密码？" href="/ecps-portal/ecps/portal/getpwd/getpwd1.form">忘记密码？</a></li>
		<!--li class="alg_c dev gray">还不是移动商城会员？<a title="免费注册" href="/ecps-portal/ecps/portal/register.form">免费注册</a></li-->
		</ul>
	</div>
</div>

<div id="promptAlert" class="alt prompt" style="display:none">
	<h2 class="h2"><em title="提示">提示</em><cite></cite></h2>
	<a href="javascript:void(0);" id="promptAlertClose" class="close" title="关闭"></a>
	<div class="cont">
		<dl class="dl_msg">
			<dt>请在新页面完成支付！</dt>
			<dd>支付完成前请不要关闭此窗口，<br />完成支付后请根据您的情况点击下面的按钮。</dd>
			<dd><a href="#" title="遇到付款问题" class="inb btn96x23 mr20">遇到付款问题</a><a href="#" title="已完成支付" class="inb btn96x23">已完成支付</a></dd>
			<dd class="alg_r"><a href="#" title="返回选择其他支付方式">返回选择其他支付方式&gt;&gt;</a></dd>
		</dl>
	</div>
</div>

<div id="transitAlert" class="alt transit" style="display:none">
	<h2 class="h2"><em title="提示">提示</em><cite></cite></h2>
	<a href="javascript:void(0);" id="transitAlertClose" class="close" title="关闭"></a>
	<div class="cont">
		<div class="warningMsg">
			<p class="indent">您即将访问的网站不属于中国移动通信集团公司门户网站站群范围，任何通过使用中国移动通信集团公司门户网站站群链接到的第三方页面均系第三方平台制作或提供，您可能从该第三方网页上获得资讯及享用服务，中国移动通信集团公司对其合法性概不负责，也不承担任何法律责任。</p>
			<p class="alg_c"><input type="button" class="hand btn66x23" value="确 定" /></p>
		</div>
	</div>
</div>

</body>
</html>