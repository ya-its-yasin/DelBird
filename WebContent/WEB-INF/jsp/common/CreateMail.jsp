<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>DelBird - Write a Mail</title>

	<style type="text/css">
		
	</style>
</head>
<body>

<div>
	<form role="form" action="#" method="post" id="theFormID" >
		<br><br>
		To::&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<input class="MailText" type="text" name="mTo"  style="width: 300px" value="@delbird.com">
		<br><br>
		Subject :: &nbsp <input class="MailText" type="Mail" name="mSubject" style="width: 600px" >
		<br><br>
		Content ::<br><textarea class="MailText" type="text" name="mContent" style="height:150px; width: 700px" ></textarea> 
		<br><br><br>
		<center>
			<input type="submit" name="Send" value="Send">
			&nbsp&nbsp&nbsp&nbsp&nbsp
			<button name="SaveAsDraft">Save as draft</button>
			&nbsp&nbsp&nbsp&nbsp&nbsp
			<button name="Discard">Discard</button>
		</center>
	</form>
</div>

</body>
</html>