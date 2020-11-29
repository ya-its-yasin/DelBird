<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>DelBird</title>
    <link rel="shortcut icon" href="images/logo4.png" type="image/png">
 <style type="text/css">
	#logo{ 
	position:fixed; 
	top:-35; 
	left:0; 
}
body{
	background-image:url('images/background.jpg');
	background-repeat: no-repeat;
	
}
input[type=text],input[type=password]{
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;

}
th{
	text-align: left;
}
input[type=submit] {
  width:match_parent;
  background-color: #33cc00;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}
</style>
            
 <%--   <script type="text/javascript" src="<%=request.getContextPath() %>/commonjs/passwordStrengthVal.js"></script> --%>
   
    <script type="text/javascript">



function checkValidation(){debugger;
var name =document.forms[0].userName.value;
var pass =document.forms[0].password.value;
	$.messager.progress(); 
		 $.ajax({
	 		  type: "POST",
	 		  url: "LoginValidation.do",
	 		  dataType:"json",
	 		
	 		 async:false,
	 		  data:{
	 			 
	 			 loginId: $('#userName').val(),
	 		      password: $('#password').val()
	 			  },
	 	  success: function(response){
	 		  debugger;

	 	/*	 if(response.keyword=="SUCCESS")
	 		 {
	 			  var userName=$('#userName').val();
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
	 		 
	 		  */
		 }, error: function (error) {
           debugger;
         }
	 		
	
	 		});
		// document.forms[0].action="LoginValidation.do";
		//document.forms[0].submit();
		//return false;
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
	
	function Register()
	{debugger; window.location='DelRegister.do' ;
		<%-- var path="<%=request.getContextPath() %>";
		  path=path+"/"+"DelRegister.do?";
		 window.location.replace(path); --%>
	}
</script>
</head>

<body  >
 	<div id="logo"> 
	<img src="delbird.png"> 
</div><br><br><br>

<center>
<form role="form" class="form-horizontal">
	<center><b><font family="myFirstFont" size="30px" color="black">Sign In</font></b></center>
<table align="center">
	<tr><th>USERNAME:</th><th><input type="text" class="form-log" name="userName" id="username" placeholder="Enter your username" required></th>
	</tr>
	<tr><th>PASSWORD:</th><th><input type="password" class="form-log" name="password" id="password" placeholder="Enter your password" required></th></tr>
</table>
<center><input type="submit" name="LogIn" onclick ="return checkValidation(this);"  value="Login" >   <br>                     
<button  name="btnSubmit" onclick ="Register();" value="Register" >	Sign up</button>

</center></form></center><br><br>
</body>

<!-- Mirrored from www.lab.westilian.com/bingo-admin/login-02.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 12 Jun 2018 09:05:57 GMT -->
</html>