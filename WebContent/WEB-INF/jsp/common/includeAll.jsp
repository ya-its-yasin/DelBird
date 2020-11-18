<%@page import="java.util.Locale"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="aurora.common.model.SystemLanguage"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />

<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>

<%
		
		List<SystemLanguage> langList = new ArrayList<SystemLanguage>();
		SystemLanguage systemLanguage = new SystemLanguage();
		String lang = "";String rlang="";
		
		if(session.getAttribute("bilingual")!=null && session.getAttribute("bilingual").toString().equalsIgnoreCase("Y"))
		{
			langList.add((SystemLanguage)session.getAttribute("langp"));
			langList.add((SystemLanguage)session.getAttribute("langs"));
			if(request.getParameter("lang")!=null || session.getAttribute("lang") != null)
			{
				lang = request.getParameter("lang")!=null?request.getParameter("lang").toString():session.getAttribute("lang").toString();
				if(lang.equals(langList.get(0).getSlCode()))
				{
					systemLanguage = (SystemLanguage)session.getAttribute("langp");
					rlang=((SystemLanguage)session.getAttribute("langs")).getSlCode();
				}
				else if(lang.equals(langList.get(1).getSlCode()))
				{
					systemLanguage = (SystemLanguage)session.getAttribute("langs");
					rlang=((SystemLanguage)session.getAttribute("langp")).getSlCode();
				}
			}
			else
			{
				systemLanguage = (SystemLanguage)session.getAttribute("langp");
				lang=systemLanguage.getSlCode();
			}
		}
		else
		{
			langList.add((SystemLanguage)session.getAttribute("langp"));
			systemLanguage = (SystemLanguage)session.getAttribute("langp");
		}
		
	 	String dir = systemLanguage.getSlDir();
	 	
	 	String dirAlign=systemLanguage.getSlDir().equalsIgnoreCase("LTR")?"left":"right";	 	
	 	String rdirAlign=systemLanguage.getSlDir().equalsIgnoreCase("LTR")?"right":"left";	 	
 			session.setAttribute("dir", systemLanguage.getSlDir());
 			session.setAttribute("lang", systemLanguage.getSlCode());
	 %>
	  <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
	     <link rel="stylesheet" href="css/bootstrap.min.css"> 
  
	 <%

if(dir.equalsIgnoreCase("RTL"))
{
%>
	<link rel="stylesheet" type="text/css" href="css/common_rtl.css"/>

	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/easyui-rtl.css"/>
	
<%
}
else
{	
%>
<link rel="stylesheet" type="text/css" href="css/common.css"/>

<link rel="stylesheet" type="text/css" href="css/easyui.css">
<%
}
%>


<%-- Jquery Css --%>
<link rel="stylesheet" href="css/jquery-ui.min.css" />
<%-- Common js --%>
<script async type="text/javascript" src="commonjs/date.m.js"></script>
<script  src="commonjs/jquery.min.js"></script>
<script  src="commonjs/jquery-ui.min.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/commonjs/popper.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/commonjs/bootstrap.min.js"></script>   --%>
<script  type="text/javascript" src="commonjs/jquery.easyui.min.js"></script>
<%if(dir.equalsIgnoreCase("RTL")){%>
 <script type="text/javascript" src="commonjs/easyui-rtl.js"></script>  
<%}%>

<script type="text/javascript" src="commonjs/jquery.watermark.m.js"></script>  
<%--user inputs --%>
<input type="hidden" id="lang" value="<%=lang%>" >
<input type="hidden" id="rlang" value="<%=rlang%>" >
<input type="hidden" id="loginUser" value="<%=session.getAttribute("loginUser")%>" >
<input type="hidden" id="currentUserKey" value="<%=session.getAttribute("currentUserKey")%>" >

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>	
 <script>  
  function showLogOutMsg(){ 	  
 	  $.messager.confirm('Confirm', 'Do you wish to logout of the Application...?', function(r)
			   {  
           if (r)
           {  
        	   window.location.href="<c:url value="/j_spring_security_logout" />";        	  
           } 
           else
        	   {
           		window.location.reload();       	   
        	   }
       });  
  }
  </script> 
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/iziToast.css" />  
<script type="text/javascript" src="<%=request.getContextPath()%>/commonjs/iziToast.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commonjs/datagrid-filter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commonjs/datagrid-groupview.js"></script>
<input type="hidden" value="<spring:message code='messages.selectrecord'/>" id="selectrecord">
<input type="hidden" value="<spring:message code='messages.title'/>" id="title">
<input type="hidden" value="<spring:message code='messages.alert'/>" id="alertId">
<input type="hidden" value="<spring:message code='messages.warning'/>" id="warning">
<input type="hidden" value="<spring:message code='messages.selectActiveRecord'/>" id="selectActiveRecord">
<input type="hidden" value="<spring:message code='messages.confirm'/>" id="confirm">
<input type="hidden" value="<spring:message code='messages.activerecord'/>" id="activerecord">
<input type="hidden" value="<spring:message code='messages.linkmessages'/>" id="linkmessages">
<input type="hidden" value="<spring:message code='messages.sessionExpire'/>" id="sessionExpire">


<input type="hidden" value="<spring:message code='messages.passwordMissmatch'/>" id="passwordMissmatch">
<input type="hidden" value="<spring:message code='messages.validate.space'/>" id="validateSpace">
<input type="hidden" value="<spring:message code='messages.passValidate.msgShldCont'/>" id="msgShldCont">
<input type="hidden" value="<spring:message code='messages.passValidate.msgCannCont'/>" id="msgCannCont">		
<input type="hidden" value="<spring:message code='messages.passValidate.number'/>" id="msgNumber">	
<input type="hidden" value="<spring:message code='messages.passValidate.spChar'/>" id="msgSpChar">
<input type="hidden" value="<spring:message code='messages.passValidate.alphabet'/>" id="msgAlphabet">
<input type="hidden" value="<spring:message code='messages.passValidate.validateLen'/>" id="validateLenMsg1">
<input type="hidden" value="<spring:message code='messages.passValidate.validateLen1'/>" id="validateLenMsg2">


	
		