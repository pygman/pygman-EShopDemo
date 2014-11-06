<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="taglibs.jsp"%>
<head>
	<meta name="menu" content="PaymentMgmtMenu"/>
</head>

<h2><samp class="t03"></samp>支付管理</h2>
<ul class="ul">
<li><a href="${base}/payment/listpaymentway.form"><samp class="t05"></samp>支付方式管理</a></li>
<li><a href="${base}/payment/listPaymentOrg.form"><samp class="t05"></samp>支付机构管理</a></li>
<li><a href="${base}/payment/listpaymenttool.form"><samp class="t05"></samp>支付工具管理</a></li>
<li><a href="${base}/payment/listOrderPayment.form"><samp class="t05"></samp>订单支付总览</a></li>
<li><a href="${base}/payment/revocationManagement.form"><samp class="t05"></samp>撤销管理</a></li>
<li><a href="${base}/payment/listPayRecord.form"><samp class="t05"></samp>支付单管理</a></li>
<li><a href="${base}/payment/listRefundRecord.form"><samp class="t05"></samp>退款单管理</a></li>
<li><a href="${base}/payment/listPaymentStatus.form"><samp class="t05"></samp>支付状态查询</a></li>
<li><a href="${base}/payment/check/listPayCheckTotal.form"><samp class="t05"></samp>支付对账</a></li>
</ul>