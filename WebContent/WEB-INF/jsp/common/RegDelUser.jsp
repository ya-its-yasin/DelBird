<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>DelBird - Register</title>
    <link rel="shortcut icon" href="images/logo4.png" type="image/png">
    <style type="text/css">
	#logo{ 
	position:fixed; 
	top:-35; 
	left:0; 
}
body{
	background-image:url('background.jpg');
	background-repeat: no-repeat;
	
}

input[type=text],input[type=email],input[type=password],input[type=date], select {
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
th:odd{
	font-size: 30px;
}

</style>

            <!-- <link href="css/stylePwReset.css" rel="stylesheet" type="text/css"> -->
        <script src="commonjs/jquery.min.js"></script>
    <script src="commonjs/jquery-ui.min.js"></script>
    <script  type="text/javascript" src="commonjs/jquery.easyui.min.js"></script>
   <!--  <script src="commonjs/jquery.ui.touch-punch.js"></script>
    <script src="commonjs/chosen.jquery.js"></script>
    <script src="commonjs/uniform.jquery.js"></script> -->
     <script src="commonjs/iziToast.js"></script>
      <script src="commonjs/calert.js"></script>
<script type="text/javascript">

var formName='fmDelReg';
//var value=$('#fmCountry #hidValue').val();//document.getElementById("hidValue").value;


        
        function saveData()
		  {  debugger;
		 	  //var mode=document.getElementById("hidMode").value;	
			  //var hasChanges=document.getElementById("hidHasChanges").value;
			 url="addDelUser.do?";
							 $.ajax({
							        type: "POST",
							        url:url,       
							        dataType: 'json',
							        data:$('#fmDelReg').serialize(),			  
						    		success: function(obj){		    			
						    			if(obj.keyword=="success")
						    				{		
						    				debugger;
						    					//document.getElementById("hidMode").value="E";
						    				    loadFormWithData(obj.data);
							    				$.messager.progress('close');	
							    				$.messager.alert('<spring:message code="messages.saved"/>',obj.message);		    			
						    				}
						    			else if(obj.keyword=="modified")
						    				{    				
						    					latestData=obj.data;
						    					showDIConfirmDlg('<spring:message code="messages.DIError"/>','<spring:message code="messages.updateModifiedDb.error"/>','<spring:message code="messages.save"/>');
						    				}
						    			else if(obj.keyword=="failure")
							    		  {
								    	  $.messager.alert('<spring:message code="messages.error"/>',obj.message);
								    	  
							    		  }
						    		}
						    	});
					  }
				
				    
        
        
    </script>
   
</head>


<body id="theme-default" class="full_block"  onload="window.history.forward(); document.forms[0].j_username.focus();" onpageshow="if (event.persisted) window.history.forward();" >
	<div id="logo"> 
	<img src="delbird.png"> 
</div> 

<form id ="fmDelReg" role="form" class="form-horizontal">
	<center><b><font family="myFirstFont" size="20px" color="black">CREATE ACCOUNT</font></b></center>
<table align="center">
	<tr><th>USERNAME:</th><th><input type="text" class="form-control" name="userName" placeholder="Enter your name" required></th>
	</tr>
	<tr><th>MAILID:</th><th><input type="email" class="form-control" name="userId" placeholder="Enter your email id" required></th></tr>
	<tr><th>MOBILE NUMBER:</th><th><input type="text" class="form-control" name="mobNum" placeholder="Enter your mobile number" required></th></tr>
		<tr><th>GENDER:</th><th><input type="text" class="form-control" name="gender" placeholder="Enter your gender" required></th>
	<tr><th>DATE OF BIRTH:</th><th><input type="date" class="form-control"  name="dobDateTime" placeholder="Enter your date of birth" required></th></tr>
	</tr>
	<!--<tr><th>GENDER:</th><th><select class="form-control" name="gender">
		<option value="selectgender">-select gender-</option>
		<option value="male">Male</option>
		<option value="female">Female</option>
	</select></th></tr>-->
	<tr><th>PASSWORD:</th><th><input type="password" class="form-control" name="password" placeholder="Enter your password" required></th></tr>
		<tr><th>USERKEY:</th><th><input type="hidden" class="form-control" name="userKey" ></th></tr>
	
	<!-- <tr><th>CONFIRM PASSWORD:</th><th><input type="password" class="form-control" name="cpassword" placeholder="Re enter your password" required></th></tr>  -->
	
</table>
<center><input type="submit" name="submit" onclick="saveData()">
</center></form><br><br>

</body>


</html>