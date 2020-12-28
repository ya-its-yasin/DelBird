<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>DelBird - View Mail</title>
</head>
<body>

<div>
	<form role="form">
		<br><br>
		From::&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<input class="MailText" type="text" name="mTo"  style="width: 300px" readonly="true">
		<br><br>
		Subject :: &nbsp <input class="MailText" type="Mail" name="mSubject" style="width: 600px" readonly="true">
		<br><br>
		Content ::<br><textarea class="MailText" type="text" name="mContent" style="height:150px; width: 700px" readonly="true"></textarea> 
		<br><br><br>
		
	</form>
</div>


</body>
</html>