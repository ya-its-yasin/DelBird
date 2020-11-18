<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Jbase</title>
<link rel="shortcut icon" href="images/logo4.png" type="image/png">
<%@ include file="includeAll.jsp"%>
<script type="text/javascript" src="commonjs/DashBoard.js"></script>



   
 
 <script type="text/javascript">
// window.history.forward();

 
 var openRepWin = function(verb, url, data, target) {
		var form = document.createElement("form");
		form.action = url;
		form.method = verb;
		form.target = target || "_new";
		if (data) {
			for (var key in data) {
				var input = document.createElement("input");
				input.name = key;
				//alert(key + "---" + data[key]);
				input.value = data[key];//typeof data[key] === "object" ? JSON.stringify(data[key]) : data[key];
				form.appendChild(input);
			}
		}
		form.style.display = 'none';
		document.body.appendChild(form);
		form.submit();
	};
 
 </script>
  <script>  
  function showLogOutMsg(){ 	  
 	  $.messager.confirm('Confirm', 'Do you wish to logout of the Application...?', function(r)
 			  
			   {  
           if (r)
           {  
        	   //window.location.href="login.do";  
        	   var path="<%=request.getContextPath() %>";
		 		  path=path+"/"+"login.do";
		 		 window.location.replace(path);
           } 
           else
        	   {
           		window.location.reload();       	   
        	   }
       });  
  }
  
  function openLink(keyword){
	  debugger;
	  
	    var Val="";		
	    if(keyword=="ChangePassword"){
	    	
	    
	    	var url="ChangePassword.do?Value="+Val;
	    	
	    	createHomeFormDlg();

	    	 $('#FormDlghome').dialog({			    
	    		    width:500,   
	    		    height: 300     
	    		}); 
	    		
	    		//alert("Url:"+url);
	    		url+="&w="+(500-14);
	    		
	    		$('#FormDlghome').dialog('open').dialog('refresh', url).dialog('setTitle','Change Password');
	    	
	    }
	  
  }
  
  function openUser(keyword){
	  debugger;
	  
	    var Val=($('#hidLoggedInUser').val());		
	    if(keyword=="MyProfile"){
	    	
	    
	    	var url="MyProfile.do?Value="+Val;
	    	
	    	createUserFormDlg();

	    	 $('#FormDlgUser').dialog({			    
	    		    width:650,   
	    		    height:320     
	    		}); 
	    		
	    		//alert("Url:"+url);
	    		url+="&w="+(500-14);
	    		
	    		$('#FormDlgUser').dialog('open').dialog('refresh', url).dialog('setTitle','My Profile');
	    	
	    }
	  
  }
  
  
  function createHomeFormDlg()
  {
  	$('#dynaformhome').html('<div id="FormDlghome" class="easyui-dialog" style="width:500px;height:300px; "  closed="true"  data-options="iconCls:\'icon-master\',modal:true" ></div> ');     
  	$('#FormDlghome').dialog({
  		
  		onClose: function()
  		{
  			debugger;
  			
  			
  				
  			$('#FormDlghome').dialog('destroy').empty();
  			$('#dynaformhome').html('');
  		} 
  	
  	});
  }
  
  
  function createUserFormDlg()
  {
  	$('#dynaformhome').html('<div id="FormDlgUser" class="easyui-dialog" style="width:650px;height:320px; "  closed="true"  data-options="iconCls:\'icon-master\',modal:true" ></div> ');     
  	$('#FormDlgUser').dialog({
  		
  		onClose: function()
  		{
  			debugger;
  			
  			
  				
  			$('#FormDlgUser').dialog('destroy').empty();
  			$('#dynaformhome').html('');
  		} 
  	
  	});
  }
  </script>  
</head>
<body dir=<%=dir%> onload="window.history.forward();"    oncontextmenu="return true;"  onpageshow="if (event.persisted) window.history.forward();">
<input type="hidden" id="hidgridName"/> 
<input type="hidden" id="hidLoggedInUser" value="<%=request.getSession().getAttribute("loginUser")%>"/>

	<div class="easyui-panel" fit="true">
	<!-- 	<div data-options="region:'north' "  id="north"  style="height:31px"	> -->
		<table style="width:100%;"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="bar1" >		
					<table  style="width:100%;left:0px;height:30px;" border="0" cellspacing="0" cellpadding="0">		
		 					<tr  >
		 						<td style="font-size: large;vertical-align: middle;padding-left: 15px;">
		 						&nbsp;&nbsp;<img alt="" src="images/logo4.png" height="30px" width="30px">&nbsp;&nbsp;
		 						
		 						<b>JBASE</b></td>
		 						 <td style="vertical-align: middle;padding-left: 15px;padding-right: 15px;">
								
								
								</td> 
								
						</tr>
						</table>	
					</div>
				</td>
			</tr>				
			<!-- <tr>
			<td>					
					<div class="bar2" >
						<table  style="width:100%;left:0px;height:30px;" border="0" cellspacing="0" cellpadding="0">		
		 					<tr  > -->
		 						<%-- <td style="vertical-align: middle;padding-left: 15px;">
				<b><label><spring:message code="label.welcome"/></label>&nbsp;
				<%=request.getSession().getAttribute("loginUser")%>,</b>&nbsp;&nbsp;&nbsp;
		 </td> --%>
		 						<%-- <td style="text-align:<%=rdirAlign%>">
		 							<a id="lnkish"	href="DashBoard.do?lang=<%=lang%>"	class="easyui-linkbutton" title="Home" iconCls="fa fa-home"plain="true"></a> 
									<a id="lnkTheme" href="#" class="easyui-linkbutton"	iconCls="icon-theme"  title="Change theme" plain="true"></a>	
				    				<a id="settings" href="#" class="easyui-linkbutton" iconCls="icon-userPreference"  title="My Preference"   onclick="openLink('UserPreference')" plain="true"></a>		
									<a id="lnkish" href="#" class="easyui-linkbutton"	iconCls="icon-changePassword" onclick="openLink('ChangePassword')" title="Change Password" plain="true"></a>
									<%	if (session.getAttribute("bilingual") != null&& session.getAttribute("bilingual").toString().equalsIgnoreCase("Y")) {%>
								<a id="lnkLang<%=langList.get(0).getSlCode()%>"	href="?lang=<%=langList.get(0).getSlCode()%>" class="easyui-linkbutton" plain="true" iconCls="icon-toggle"
								 ><b><%=langList.get(0).getSlDesc()%></b></a>
								<a id="lnkLang<%=langList.get(1).getSlCode()%>"	href="?lang=<%=langList.get(1).getSlCode()%>" class="easyui-linkbutton" plain="true" iconCls="icon-toggle"
									><b><%=langList.get(1).getSlDesc()%></b></a>
							<%}	%>
									
		 						</td> --%>
							<!-- </tr> 
						</table>			
					</div>		
				</td>
			</tr> -->
		</table>
				
		
		<!-- </div>
	<div id="CenterPanel" data-options="region:'center'"  > -->
	<!-- /// -->
	<div  class="easyui-layout" style="width:600px;height:400px;" fit="true">
		 <%

if(dir.equalsIgnoreCase("RTL"))
{
%>
 <div data-options="region:'east'" style="width:58px;">
<%
}
else
{	
%>
 <div data-options="region:'west'" style="width:58px;">


<%
}
%>
   
    <table  style="width:100%;left:0px;height:30px;" border="0" cellspacing="0" cellpadding="0">		
 			<tr  >
 			
 			<td style="float:<%=dirAlign%>"><div id="menudiv"></div></td>
			</tr> 
			</table>
    </div>
   
    <div data-options="region:'center'" >
    
     <!--  // -->
      <div id="GridPanelLayout" class="easyui-layout" style="width:600px;height:400px;" fit="true">
    
     <%

if(dir.equalsIgnoreCase("RTL"))
{
%>
  <div data-options="region:'east',title:'Options',collapsible:false" style="width:215px;">
<%
}
else
{	
%>
 <div data-options="region:'west',title:'Options',collapsible:false" style="width:215px;">


<%
}
%>
    
    
    
    
     <ul class="easyui-tree" id="menuTree" data-options="method:'get'"></ul>
    </div>
    <div data-options="region:'center'" >
    <!-- <div class="mainPanel"  style="height:80%">
 	<div id="mainPanel" class="easyui-panel" title="My Panel" 
        style="padding:10px;background:#fafafa;"
        data-options="fit:true,noheader:true,border:false">
    
</div>
 	</div> -->
    </div>
</div>
      <!-- // -->
    </div>
</div>
	<!-- //// -->
	
 	
		<!-- </div>	 -->	
	</div>		
	


<input type="hidden" value="<%=request.getSession().getAttribute("userDefaultModule")==null?"":request.getSession().getAttribute("userDefaultModule").toString() %>" id="moduleId" >
  
   		<div class="themesMenu" id="themesMenu" style="left:10px;top:45px" >   		 
   		<a id="default" href="#" class="easyui-linkbutton"	iconCls="icon-blue" onclick="ChangeTheme('default');" title="Default Theme" plain="true"></a>
   		<a id="green" href="#" class="easyui-linkbutton"	iconCls="icon-green" onclick="ChangeTheme('bootstrap');" title="Bootstrap Theme" plain="true"></a>
   		<a id="sunny" href="#" class="easyui-linkbutton"	iconCls="icon-red" onclick="ChangeTheme('black');" title="black Theme" plain="true"></a>
   		<a id="vibgyor" href="#" class="easyui-linkbutton"	iconCls="icon-vibgyor" onclick="ChangeTheme('metro');" title="metro Theme" plain="true"></a>    			
<div id="dynaformhome"></div>
</div> 

</body>
</html>