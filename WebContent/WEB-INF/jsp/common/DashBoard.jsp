<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Call Collection System</title>
<meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
         <link rel="stylesheet" type="text/css" href="css/dboardtv.css" />
           <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/jquery-easyui-1.3.6/themes/default/easyui.css">
          
       <script  src="<%=request.getContextPath() %>/jquery-ui-1.9.2.custom/js/jquery-1.8.3.m.js"></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<%-- <script  type="text/javascript" src="<%=request.getContextPath() %>/commonjs/analogclock.js"></script> --%>
 <script  src="commonjs/date.js"></script>
		<style>
			body {
				background: #ffffff   no-repeat;
				background-size:100% 1024px;
				 height: auto;
				filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/background.jpg', sizingMethod='scale');
				-ms-filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/background.jpg', sizingMethod='scale')";
 				position: absolute; 
				  top: 0; 
				  left: 0; 
				  right: 0; 
				  bottom: 0; 
				  margin: auto; 
				  min-width: 50%;
				  min-height: 50%;
			}
			
.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber {
margin: 0;
padding: 0 4px;
white-space: nowrap;
word-wrap: normal;
overflow: hidden;
height: 18px;
line-height: 18px;
font-size: 16px;
font-weight: bolder;
}

.datagrid-header .datagrid-cell span {
font-size: 16px;
font-weight: bolder;
}
.panel-title {
font-size: 16px;
font-weight: bolder;
color: #ffffff;
height: auto;
line-height: 16px;
}
		</style>
		

<script type="text/javascript">


$(document).ready(function (){
	
	
	function fillMarquee()
	{
		$.ajax({
		      type: "POST",
		      url: 'DashBoardLastColDet.do',    
		      /* dataType: 'json', */
		      success: function(responseJson) {	  
		    	 /// debugger;
		    	  //alert(responseJson);
			    	$("marquee").html(responseJson);	
		      }
		    });	
		
		 $.ajax({
		      type: "POST",
		      url: 'DashBoardgetSiteDevCount.do',    
		      dataType: 'json',
		      success: function(obj) {	    	   	
			    	$('#devCount').html(obj.devCount);
			    	 $('#siteCount').html(obj.siteCount);
		      }
		    });
		  
		  $.ajax({
		      type: "POST",
		      url: 'DashBoardColUpdate.do',    
		      dataType: 'json',
		      success: function(obj) {	
		    	  //debugger;
		    	  for ( var i = 0; i < obj.length; i++ ) {
		    		    if ( obj[i].siteCode=='PREV' ) {
		    		    	$('#yInc').html(obj[i].incomingCalls);
		   		    	    $('#yOut').html(obj[i].outgoingCalls);
		   		    	    $('#yInt').html(obj[i].internalCalls);
		   		    	    $('#yRej').html(obj[i].rejectedCalls);
		   		    	    $('#yTot').html(obj[i].totalCalls);
		   		    	    $('#ySite').html(obj[i].key);
		    		    }
		    		    if ( obj[i].siteCode=='CURR' ) {
		    		    	$('#tInc').html(obj[i].incomingCalls);
		   		    	    $('#tOut').html(obj[i].outgoingCalls);
		   		    	    $('#tInt').html(obj[i].internalCalls);
		   		    	    $('#tRej').html(obj[i].rejectedCalls);
		   		    	    $('#tTot').html(obj[i].totalCalls);
		   		    	    $('#tSite').html(obj[i].key);
		    		    }
		    		}
		      }
		    });	
	}
	
	 
	 										
				var currDate = getDateString(new Date());		
				var prev=new Date();
				prev.setDate(prev.getDate() - 1);
				var prevDate = getDateString(prev);
				
				 var count=0; 		
				 var currentIndex=0;
				 var gd=0;
				 var scrollCount=0;
				$.ajax({
				      type: "POST",
				      url: 'DashBoardCurrentDate.do?callDate='+currDate,    
				      dataType: 'json'
				    }).done(function( responseJson ) 
				      {
				    					    	
				    	//debugger;
				    	$('#tlGrid').datagrid({data:responseJson,title:'Site-Wise Collection Log(Today)'});
				    	count=$('#tlGrid').datagrid('getRows').length;
				    	//alert(count);
				    	gd=0;
				    	scrollCount=0;
				    	setTimeout(grid_scroll, 1000); 
				      });	
				
				function grid_scroll() {
					//debugger;
					if(scrollCount==4)
						{
						changeData();
						}
					else
						{
							if(currentIndex<count)
								{
								  currentIndex=currentIndex+2;
								  if (currentIndex<=count)
									  {
									  $('#tlGrid').datagrid('scrollTo',currentIndex);
									  }
								  else
									  {
									  $('#tlGrid').datagrid('scrollTo',0);
									  currentIndex=0;
									  scrollCount++;
									  }
								}
							else
							  {
							  $('#tlGrid').datagrid('scrollTo',0);
							  currentIndex=0;
							  scrollCount++;
							  }
							    setTimeout(grid_scroll, 1000); 
						}
					  }
				function changeData()
				{
					fillMarquee();
					if(gd==0){
						$.ajax({
						      type: "POST",
						      url: 'DashBoardCurrentDate.do?callDate='+prevDate,    
						      dataType: 'json'
						    }).done(function( responseJson ) 
						      {						    					    							    	
						    	$('#tlGrid').datagrid({data:responseJson,title:'Site-Wise Collection Log(Yesterday)'});
						    	count=$('#tlGrid').datagrid('getRows').length;
						    	//alert(count);
						    	gd=1;
						    	scrollCount=0;
						    	setTimeout(grid_scroll, 1000); 
						      });	
					}
					else
						{
						$.ajax({
						      type: "POST",
						      url: 'DashBoardCurrentDate.do?callDate='+currDate,    
						      dataType: 'json'
						    }).done(function( responseJson ) 
						      {
						    	$('#tlGrid').datagrid({data:responseJson,title:'Site-Wise Collection Log(Today)'});
						    	count=$('#tlGrid').datagrid('getRows').length;
						    	//alert(count);
						    	gd=0;
						    	scrollCount=0;
						    	setTimeout(grid_scroll, 1000); 
						      });	
						}
					
					
				}
				
				function blinker() {
				    $('.blink_me').fadeOut(100);
				    $('.blink_me').fadeIn(100);
				}
				fillMarquee();
				//setInterval(blinker, 500);
				  setInterval( function() {			    	 
			      var seconds = new Date().getSeconds();
			      var sdegree = seconds * 6;
			      var srotate = "rotate(" + sdegree + "deg)";
			      
			      $("#sec").css({ "transform": srotate });
			          
			      }, 1000 );
			      
			      setInterval( function() {
			      var hours = new Date().getHours();
			      var mins = new Date().getMinutes();
			      var hdegree = hours * 30 + (mins / 2);
			      var hrotate = "rotate(" + hdegree + "deg)";
			      
			      $("#hour").css({ "transform": hrotate});
			          
			      }, 1000 );

			      setInterval( function() { 
			      var mins = new Date().getMinutes();
			      var mdegree = mins * 6;
			      var mrotate = "rotate(" + mdegree + "deg)";
			      
			      $("#min").css({ "transform" : mrotate });
			          
			      }, 1000 );
			 
				/* $("#flipcountdownbox1").flipcountdown({
					  size:"lg"
					}); */
		
});
</script>

</head>
<body class="easyui-layout" fit="true" oncontextmenu="return false;" onload="window.history.forward(); " onpageshow="if (event.persisted) window.history.forward();" >
   <div data-options="region:'north' "  id="north">
			<table  width="100%"  cellspacing="0" cellpadding="0" style="border-color: navy;border-style: solid;border-width:2">
			<tr>
			<td rowspan="3">
				<div id="logo" style="border-color: navy;border-style: solid;border-width:2">
				<img alt="" src="images/rao1.jpg" height="125px" width="150px">
			</div>
			</td>
			<td style="background-color:white;width:100%"><div id="title"></div></td>			
			<td rowspan="3">
				<div id="logo"  style="border-color: navy;border-style: solid;border-width:2;float:right" >
				<img alt="" src="images/signal.jpg" height="125px" width="150px">
			</div></td></tr>
			<tr><td style="background-color:red"><div id="title">SAF-SIGNALS</div></td></tr>
			<tr style="background-color:green">	<td ><div id="title">Hipath - Call Collection System</div></td></tr>
			</table></div>
			<div data-options="region:'center' "  id="center">
			<%-- <div style="float:right"><b><spring:message code="label.time" /> :<span id="clock"></span></b></div> --%>
			<table height="80%" width="100%" >
			<tr>
			<td  style="width:74%;vertical-align:middle;margin-left:10px; text-align:center;height:100%"	>
				<marquee  style="color: white;font-weight: bolder;height: 40px;font-size: xx-large;"
				 bgcolor="navy" width="100%" height="20%">Last Collection Details</marquee>
				
			<table class="easyui-datagrid"  title="Site-Wise Collection Log"   id="tlGrid" style="height:270px;" fit="false"
        data-options="singleSelect:true,idField:'key',striped:true,remoteSort:false,showFooter:true,rownumbers:true">
    <thead>
        <tr>
            <th data-options="field:'siteCode',width:150,halign:'center',sortable:true">Site</th>
            <th data-options="field:'rejectedCalls',width:150,halign:'center',align:'right'">Rejected calls</th>
             <th data-options="field:'incomingCalls',width:150,halign:'center',align:'right'">Incoming Calls</th>
              <th data-options="field:'outgoingCalls',width:150,halign:'center',align:'right'">Outgoing Calls</th>
               <th data-options="field:'internalCalls',width:150,halign:'center',align:'right'">Internal Calls</th>
                <th data-options="field:'totalCalls',width:150,halign:'center',align:'right'">Total Calls</th>
           
            
            
        </tr>
    </thead>
</table>
		
				</td>
			
			
			
			<td><ul id="clock">	
  <li id="sec"></li>
  <li id="hour"></li>
  <li id="min"></li>
</ul></td>
			</tr>
				<tr>
				<td>
			<table width="100%">
			<tr>
					<td style ="vertical-align:top; margin-left: 10px; width:50%" >
					 <fieldset height="100%"><legend class="blink_me">Site Details</legend>
					 <table class="cutable">
					 <tr><td>Total No. of Sites :</td><td style="width:50%"><div class="data" id="siteCount"></div></td></tr>
					 <tr><td>Total No. of Devices :</td><td><div class="data" id="devCount"></div></td></tr>
					 </table>
					 </fieldset>
					 </td>
				</tr>
			<tr><td style ="vertical-align:top; margin-left: 10px; width:50%" >
					 <fieldset><legend class="blink_me">Collection updates</legend>
					 <table class="cutable" >
					 
					 <tr  style="border:2;border-style:solid;"><th > <h1></h1></th><th style="width:25%">Yesterday</th><th style="width:25%">Today</th></tr>
					 <tr><td >Incoming calls:</td><td ><div class="data" id="yInc"></div></td><td><div class="data" id="tInc"></div></td></tr>
					 <tr><td>Outgoing calls:</td><td><div class="data" id="yOut"></div></td><td><div class="data" id="tOut"></div></td></tr>
					 <tr><td>Internal calls:</td><td><div class="data" id="yInt"></div></td><td><div class="data" id="tInt"></div></td></tr>
					 <tr><td> Rejected calls:</td><td><div class="data" id="yRej"></div></td><td><div class="data" id="tRej"></div></td></tr>
					 <tr><td>Total calls:</td><td><div class="data" id="yTot"></div></td><td><div class="data" id="tTot"></div></td></tr>
					 <tr><td>Total no of sites collected:</td><td><div class="data" id="ySite"></div></td><td><div class="data" id="tSite"></div></td></tr>
					 
					 </table>						 					 					 										 					 
					 </fieldset>
					</td>
					</tr>
			</table></td>
					<td> <img src="images/flash.gif" width="330" height="310"></td>
				</tr>
				
			</table>
        </div>

</body>
</html>