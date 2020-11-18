<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>JBASE</title>
    <link rel="shortcut icon" href="images/logo4.png" type="image/png">
    <link href="css/reset.css" rel="stylesheet" type="text/css">
    <link href="css/layout.css" rel="stylesheet" type="text/css">
    <link href="css/themes.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="css/easyui.css">
    <link href="css/typography.css" rel="stylesheet" type="text/css">
    <link href="css/styles.css" rel="stylesheet" type="text/css">
    <link href="css/shCore.css" rel="stylesheet" type="text/css">
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
     <link href="css/form.css" rel="stylesheet" type="text/css">
      <link href="css/wizard.css" rel="stylesheet" type="text/css">
      
          <link href="css/iziToast.css" rel="stylesheet" type="text/css">
            <!-- <link href="css/stylePwReset.css" rel="stylesheet" type="text/css"> -->
        <script src="commonjs/jquery.min.js"></script>
    <script src="commonjs/jquery-ui.min.js"></script>
    <script  type="text/javascript" src="commonjs/jquery.easyui.min.js"></script>
   <!--  <script src="commonjs/jquery.ui.touch-punch.js"></script>
    <script src="commonjs/chosen.jquery.js"></script>
    <script src="commonjs/uniform.jquery.js"></script> -->
     <script src="commonjs/iziToast.js"></script>
      <script src="commonjs/calert.js"></script>
 <%--   <script type="text/javascript" src="<%=request.getContextPath() %>/commonjs/passwordStrengthVal.js"></script> --%>
    <script type="text/javascript">
        $(function () {
            $(window).resize(function () {
                $('.login_container').css({
                    position: 'absolute',
                    left: ($(window).width() - $('.login_container').outerWidth()) / 2,
                    top: ($(window).height() - $('.login_container').outerHeight()) / 2
                });
            });
            // To initially run the function:
            $(window).resize();
        });
    </script>
    <script type="text/javascript">

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}


function checkValidation(){
	$.messager.progress(); 
	var name =document.forms[0].j_username.value;
	var pass =document.forms[0].j_password.value;
	
	if(trim(name).length==0)
	{
		$.messager.alert('<spring:message code="messages.alert"/>','<spring:message code="messages.enterUserName"/>','<spring:message code="messages.warning"/>', function(ok)
				{  
			
            		if (ok==undefined)
            		{  
						document.form.j_password.value=pass;
						document.form.j_username.focus();
            		}
	});	
		return false;
	}
	
	else if(trim(pass).length==0)
	{
		$.messager.alert('<spring:message code="messages.alert"/>','<spring:message code="messages.enterPassword"/>','<spring:message code="messages.warning"/>', function(ok)
				{  
		document.form.j_username.value=name;
		document.form.j_password.focus();
				});
		return false;	
}
	else 
		
	 {
		
		 $.ajax({
	 		  type: "POST",
	 		  url: "LoginValidation.do",
	 		  dataType:"json",
	 		
	 		 async:false,
	 		  data:{
	 			 
	 			 loginId: $('#j_username').val(),
	 		      password: $('#j_password').val()
	 			  },
	 	  success: function(response){
	 		  debugger;

	 		 if(response.keyword=="SUCCESS")
	 		 {
	 			  var userName=$('#j_username').val();
	 			  var userKey=response.data[0].userKey;
		 		  var action="L1";
		 		  var userKey=response.data[0].userKey;
			 		 $.ajax({
			 	 		  type: "POST",
			 	 		  url: "saveUserLogWithUserKey.do",
			 	 		  dataType:"json",
			 	 	  	  async:false,
			 	 		  data:{
			 	 			  userKey:userKey,
			 	 		      userName: userName,
			 	 		      action:action
			 	 			  },
			 	 	  success: function(response){
			 	 		  
			 	 	  }
			 		
			 		  });
	 		  var userType=response.data[0].userType; 
	 	     if(userType=='A'){
	 			 var path="<%=request.getContextPath() %>";
		 		  path=path+"/"+response.message;
		 		 window.location.replace(path);
		 		//NewAlert.Success(output.message);
		 		
	 	     }
	 		if(userType=='C')
	 			{
	 			 var path="<%=request.getContextPath() %>";
		 		  path=path+"/"+"ClientDashBoard.do?lang=en";
		 		 window.location.replace(path);
	 			}
	 		if(userType=='T')
 			{
 			 var path="<%=request.getContextPath() %>";
	 		  path=path+"/"+"ContractorDashBoard.do?lang=en";
	 		 window.location.replace(path);
 			}
	 				
	 		 }
	 		 else 
	 			 {
	 			var userName=$('#j_username').val();
		 		  var action="L1";
			 		 $.ajax({
			 	 		  type: "POST",
			 	 		  url: "saveUserLog.do",
			 	 		  dataType:"json",
			 	 	  	  async:false,
			 	 		  data:{
			 	 		      userName: userName,
			 	 		      action:action
			 	 			  },
			 	 	  success: function(response){
			 	 		  
			 	 	  }
			 		
			 		  });
	 			CAlert.Danger("The username or password you entered is incorrect !");
	 			$.messager.progress('close'); 
	 			 }
	 		 
	 		 } 
	 		
	
	 		});
		// document.forms[0].action="LoginValidation.do";
		//document.forms[0].submit();
		return false;
	}
	
}

</script>
<script language="Javascript">

function capLock(e){
	 kc = e.keyCode?e.keyCode:e.which;
	 sk = e.shiftKey?e.shiftKey:((kc == 16)?true:false);
	 if(((kc >= 65 && kc <= 90) && !sk)||((kc >= 97 && kc <= 122) && sk))
	  document.getElementById('capsid').style.visibility = 'visible';
	 else
	   document.getElementById('capsid').style.visibility = 'hidden';
	}
function capLock1(e){
	document.getElementById('errorblock').text="";
	}
	
</script>
</head>

<body id="theme-default" class="full_block"  onload="window.history.forward(); document.forms[0].j_username.focus();" onpageshow="if (event.persisted) window.history.forward();" >
 <div class="panel layout-panel layout-panel-north panel-htop" style="width: 100%; left: 0px; top: 0px;">
 <div data-options="region:'north'"  title="" class="panel-body panel-body-noheader layout-body">
        <nav class="navbar navbar-default navbar-fixed-top" style="height:45px;z-index:9999999;background-color:#335075;">
            <div class="container-fluid" style="height:45px;">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header" style="height:40px;">
                    
                   <img style="margin-top:-13px;" alt="" src="images/logo4.png" height="50px" width="50px"> <div class="navbar-brand" style="height:40px;color:white;">JBASE</div>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">



                    


                    


                    
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
     <input type="hidden" name="userType" id="userType" value="<%=request.getSession().getAttribute("currentUserType")%>"/> 
    </div></div>
    <div id="login_page">
        <div class="login_container" style="width:543;margin-top:-25px;">
          
            <div class="block_container blue_d" style="background-color:#f8f7f1 !important">
                <form action="#" method="post" id="theFormID">
                    <div class="block_form">
                    <div class="container login-box-header " >
                        
                        <label><spring:message code="label.logIn"/></label></div>
                        <hr style="    border-top: 4px solid rgb(51, 80, 117);">
                        <ul>
                     
                            <li class="login_user">
                               <!--  <input name="" value="username" type="text"> -->
                               <label class="input-group" for="username" style="margin-bottom:0px;"><spring:message code="label.userName"/></label>
                                <input type="text" name="j_username"  id="j_username"  class="inputtxt"   />
                            </li>
                            <li class="login_pass">
                             <!--    <input name="" type="password" value="123456"> -->
                                 <label class="input-group" for="username" style="margin-bottom:0px;"><spring:message code="label.password"/></label>
                                <input type="password" name="j_password" id="j_password"   class="inputtxt" />
                            </li>
                        </ul>
                        <!-- <input class="login_btn blue_lgel" name="" value="Login" type="button" onclick="newpage()"> -->
                        <input class="login_btn blue_lgel" style="background-color:#335075     !important" name="btnSubmit" onclick ="return checkValidation(this);" type="submit" value="Login" >	
						
                    </div>
                   <ul class="login_opt_link">
                      
                    </ul> 
                </form>
            </div>
        </div>
    </div>
  
</body>

<!-- Mirrored from www.lab.westilian.com/bingo-admin/login-02.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 12 Jun 2018 09:05:57 GMT -->
</html>