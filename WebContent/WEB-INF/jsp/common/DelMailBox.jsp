<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>DelBird - Mailbox</title>

	<style type="text/css">
		
		.column {
			
			border-radius: 15px;
  			border: 2px solid #73AD21;
		    float: left;
		    height: 450px; 
		}

		.left
		{
			text-align: center;
			width: 14%;
		  
		} 
		
		.middle {
			margin-left: 1%;
		  width: 26%;
		}

		.right {
			margin-left: 1%;
		  width: 56%;
		  
		}

		.top-layer
		{
			border-radius: 15px;
  			border: 2px solid #73AD21;
  			margin-bottom: 1%;
			width:99%;
			height: 80px;
		}
	
	</style>
</head>
<body>

<div class="top-layer" style="display:flex;">
	
	<div style="width: 90%">
		<center>
			<h1>DelBird</h1>
		</center>
	</div>
	<div>
			<button style="margin-top: 25%" onclick="ChangeCont()">Write A New Mail</button>
	</div>	
		
	
</div>

<div class="main-content" >	

	<div class="column left">
		<center><h2>Options</h2></center>
    	
    	<div class="buttons-left">
	   		
	   		<button>Inbox</button>
	   		<br><br>
	   		<button>Sent items</button>
	   		<br><br>
	   		<button>Drafts</button>
   		</div>

  	</div>
	
	<div class="column middle" >
	    <center><h2>Mails</h2></center>
	    <div style="margin-left: 2%">
	    	List of mailsss...............
	    </div>
  	</div>
	
	<div class="column right">
	    <center><h2>Mail expansion</h2></center>
	    <div style="margin-left: 2%">
	    	<iframe id="Expan" src="" style="height:370px;width:650px">
	    		sdvdvsvd
	    	</iframe>
	    </div>
  	</div>

</div>

<script type="text/javascript">
	function ChangeCont() {
		
	  document.getElementById("Expan").src = "common/CreateMail.jsp";
		
	}
</script>

</body>
</html>