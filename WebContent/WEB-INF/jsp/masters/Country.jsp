<%String Mode = request.getParameter("Mode");
String Value = request.getParameter("Value"); %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=IE9" />
   
</head>
<script>
	var formName='fmCountry';
	var value=$('#fmCountry #hidValue').val();//document.getElementById("hidValue").value;
	$(function (){
		$('#fmCountry #nameS').watermark('<spring:message code="label.name"/>');
	});
	
	
	$(document).ready(function (){
		debugger;
		$("#btnDelete").linkbutton("disable");
		$("#btnAdd").linkbutton("disable");
		var currentDate = new Date();
		var minDate=new Date((currentDate.getFullYear()-50),currentDate.getMonth() + 1,currentDate.getDate());
		var maxDate=new Date((currentDate.getFullYear()+50),currentDate.getMonth() + 1,currentDate.getDate());
		//loadForm();
		$('#fmCountry #keyword').focus();
		/* $('#fmModuleDP1').datepicker({
	      //  defaultDate: "+1w",
	        changeMonth: true,
	        changeYear: true,
	        yearRange:'c-50:c+50',
	        showButtonPanel: true,
	        dateFormat: 'dd-M-yy',
	        minDate:minDate,
		    maxDate:maxDate,
	        onClose: function( selectedDate ) {debugger;
	        	$('#fmModuleDP2').datepicker( "option", "minDate", selectedDate );
	        }
	    });
	 
	$('#fmModuleDP2').datepicker({
	       // defaultDate: "+1w",
	        changeMonth: true,
	        changeYear: true,
	        yearRange:'c-50:c+50',
	        showButtonPanel: true,
	        dateFormat: 'dd-M-yy',
	        minDate:minDate,
		    maxDate:maxDate,
	        onClose: function( selectedDate ) {debugger;
	        	$('#fmModuleDP1').datepicker( "option", "maxDate", selectedDate );
	        }
	    }); */
	 $('#keyword').bind('keypress',function(){
		 debugger;
		  $(this).data('validatebox').validating = false;
		}).bind('focusout',function(){debugger;
		  $(this).validatebox('validate');  // call validate method to do the validation when box lose focus
		});
	 
	 var mode=document.getElementById("hidMode").value;
	  var value=document.getElementById("hidValue").value;
	 debugger;
	  switch(mode)
	  {
	  case "A":
		 // clearForm();	
		  break;
	  case "E":
		  LoadData();
		  break;
	  case "V":		 
		  LoadData(); 
		  $('#fmCountry #nameS').attr("disabled", true);
		  break;	  
	  }
		
	 
	 
	});
	
		 function clearForm()
		  {
			  $('#fmCountry').form('clear' ); 
			  $('#fmCountry #nameS').attr("disabled", false);
			   document.getElementById("hidMode").value="A";
			  document.getElementById("hidValue").value="-1";
			  document.getElementById("hidHasChanges").value="0";
			  $("#statusDiv").html("<span style='color: black;font-weight:bold;'>Status : </span><span style='color: blue;font-weight:bold;'>New</span>");
		  }
		 function LoadData()
		  {
			 debugger;
			 var Value=$('#hidValue').val();
			  debugger;
			  $.ajax({
			        type: "POST",
			        url:"getCountryByKey.do?key="+Value,       
			        
			        success: function(obj) {	        	
				    	  if(obj.keyword=="success")
				    		  {
				    		   loadFormWithData(obj.data);
				    		   document.getElementById("hidRecLevel").value="";
				    		  }
				    	  else if(obj.keyword=="failure")
				    		  {
				    		  loadFormWithData(obj.data);
				    		  if(document.getElementById("hidMode").value!="A")
				    		  {
					    	  		$.messager.alert('<spring:message code="messages.error"/>',obj.message);
					    	  }
					    	  document.getElementById("hidRecLevel").value="";
					    	  
				    		  }
				    	  else if(obj.keyword=="first"){
				    		  document.getElementById("hidRecLevel").value="first";
				    		  loadFormWithData(obj.data);
				    	  }
				    	  else if(obj.keyword=="last"){
				    		  document.getElementById("hidRecLevel").value="last";
				    		  loadFormWithData(obj.data);
				    	  }
				    	  else if(obj.keyword=="cantMoveForward"){
				    		  document.getElementById("hidRecLevel").value="last";
				    		  $.messager.alert('<spring:message code="messages.alert"/>','<spring:message code="messages.nextrecord"/>');
				    	  }
				    	  else if(obj.keyword=="cantMoveBackward"){
				    		  document.getElementById("hidRecLevel").value="first";
				    		  $.messager.alert('<spring:message code="messages.alert"/>','<spring:message code="messages.previousrecord"/>');
				    	  }
			          }
			      });
		  }
		  function loadFormWithData(data)
		  {
			  $('#fmCountry').form('load',data ); 
			  var status=document.getElementById("hidStatus").value;
			  var mode=document.getElementById("hidMode").value;
			  document.getElementById("hidValue").value=document.getElementById("hidkey").value;
			  document.getElementById("hidHasChanges").value="0";
			  var htmlStr="<span style='color: black;font-weight:bold;'><label><spring:message code='label.status'/> :</label></span>";
			  switch(status)
			  {
			  case "I":
				  htmlStr+="<span style='color: red;font-weight:bold;'> <label><spring:message code='label.inactive'/></label></span>";	
				  $("#btnConfirm").linkbutton("enable");
				  $("#btnDelete").linkbutton("enable");
				  break;
			  case "A":
				  htmlStr+="<span style='color: green;font-weight:bold;'> <label><spring:message code='label.active'/></label></span>";	
				  $("#btnConfirm").linkbutton("disable");
				  $("#btnDelete").linkbutton("disable");
				  break;
			 default:
				  htmlStr+="<span style='color: blue;font-weight:bold;'> <label><spring:message code='label.new'/></label></span>";
			  $("#btnDelete").linkbutton("disable");	
			  }
			  $("#statusDiv").html(htmlStr);
		  }
		  function MenuClicked(itemClicked)
		  { if(checkConnection()=="true")
		 	{
			  debugger;
			  var mode=document.getElementById("hidMode").value;	 
			  var value=document.getElementById("hidValue").value;	
			  document.getElementById("hidItemClicked").value=itemClicked;
			  var hasChanges=document.getElementById("hidHasChanges").value;
			  switch (itemClicked)
			  {
			  case "Add":
				  if(hasChanges=="1")
					  {
					  showConfirm('<spring:message code="messages.confirm"/>','<spring:message code="messages.confirmmessages"/>');
					  return false;
					  }
				  else
					  {	
					  document.getElementById("hidRecLevel").value="";
					  $("#btnDelete").linkbutton("disable");			 
					  document.getElementById("hidMode").value="A";		  
					  clearForm();
					  $('#fmCountry #nameS').attr("disabled", false);
					  }
				  
				  break;
			  case "Draft":
				 
				  if(validateSave())
					  {
					  
					  	saveData(false);			  
					  }
				  else
					  {
					
					  return false;
					  }
				  break;
			  case "Confirm":
				  if(validateConfirm())
					  {debugger;
					  document.getElementById("hidHasChanges").value="1";
					  document.getElementById("hidStatus").value="A";
					 $.messager.confirm('Confirm','Do you want to confirm this record?',function(r){
								if (r){ saveData(false);	}});
					  }
				  break;
			  case "Delete":
				  if(confirmDelete())
					  {
					   $.messager.confirm('Confirm','Do you want to delete this record?',function(r){
								if (r){ deleteData();	}});
					  }
				  else
				  return false;
				  break;
			  }
		 	}
		  }
		  function deleteData()
		  { 	var Value=$('#hidValue').val();
			  var updatedDate=document.getElementById("hidUpdatedDate").value;
			  var url="deleteCountry.do?key="+Value+'&updatedDate='+updatedDate;
			  
			/*   if(deleteYN==true)
				  {
				  url+='&flag=Delete';
				  }
			  else
				  {
				  url+='&flag=';
				  } */
			  $.ajax({
			        type: "POST",
			        url:url,       
			        dataType: 'json',
			        success: function(obj) {	        	
				    	  if(obj.keyword=="success")
				    		  {
					    	  $.messager.alert('<spring:message code="messages.deleted"/>',obj.message);
					    	  document.getElementById("hidMode").value="A";
							  clearForm();
				    		  }
				    	  else if(obj.keyword=="failure")
				    		  {
					    	  $.messager.alert('<spring:message code="messages.error"/>',obj.message);
					    	  
				    		  }
				    	  else if(obj.keyword=="modified")
		  				{  				
		  					latestData=obj.data;
		  					showDIConfirmDlg('<spring:message code="messages.DIError"/>','<spring:message code="messages.deleteModifiedDb.error"/>','<spring:message code="messages.delete"/>');
		  				}
			          }
			      });
		  }
		  function saveData(OverWrite)
		  {  debugger;
		 	  var mode=document.getElementById("hidMode").value;	
			  var hasChanges=document.getElementById("hidHasChanges").value;
			  
			  if($('#fm').form('validate'))
				  {
				  if(hasChanges=="1")
					  {
					  var url;
						  if(mode=="A")
							  url="saveCountry.do?";
						  if(mode=="E")
							  url="updateCountry.do?";
							  $.ajax({
							        type: "POST",
							        url:url,       
							       dataType: 'json',
							        data:$('#fmCountry').serialize(),			  
						    		success: function(obj){		    			
						    			if(obj.keyword=="success")
						    				{		
						    				debugger;
						    					document.getElementById("hidMode").value="E";
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
				  else
					  {
					  $.messager.alert('<spring:message code="messages.alert"/>','<spring:message code="messages.savealert"/>');
					 
					  }
				  }
		  }  
		  function HasChanges() {	 
		      document.getElementById("hidHasChanges").value = "1";
		  }
	
	function validateSave()
	{

		debugger;
		var mode=$('#'+formName+' #hidMode').val();
		if(mode=="A")
			{
			
			$('#'+formName+' #hidStatus').val("I");
			
			}
		$("#keyword").removeAttr("disabled", "disabled");
		$("#fmModuleDP1").removeAttr("disabled", "disabled");
		return true;	
		
	} 

	
	function confirmDelete()
	{		
		var status=$('#fmCountry #hidStatus').val();
		if(status=="A")
			{
			window.parent.$.messager.alert('<spring:message code="messages.alert"/>','<spring:message code="messages.activerecord"/>','<spring:message code="messages.warning"/>'); 
			
			return false;
			}
		else
			{		 
			 return true;
			}		
	}
	
	
	function validateConfirm()
	{
		
		if( $('#fmModuleDP1').attr('value')=="")
		{
			$.messager.alert('<spring:message code="messages.warning"/>','<spring:message code="messages.effstartdate"/>','<spring:message code="messages.warning"/>'); 
	
		return false;
		
		}
	else
		{
		if(validateSave())
		{
			$('#'+formName+' #hidStatus').val("A");//document.getElementById("hidStatus").value="A";
		}
		
		$("#keyword").removeAttr("disabled", "disabled");
		 $("#fmModuleDP1").removeAttr("disabled", "disabled");
		return true;  
		}
	}
	
	
	 $.extend($.fn.validatebox.defaults.rules, {  
			validateKeyword: {  
		        validator: function(value){
		        	debugger;
		        	var filter = /^[a-zA-Z0-9-_]+$/;
		        	if(filter.test(value)){
		        		if(value.length >= 3 & value.length <=30){
		        			if(duplicationCheck(value)=="EXIST" & document.getElementById("hidMode").value=="A"){
		        				$.fn.validatebox.defaults.rules.validateKeyword.message='<spring:message code="messages.validate.keywordDuplication"/>';
		        				  return false;  
		        			}else
		        				return true;
		        			
		        		}else{
		        			
		        			$.fn.validatebox.defaults.rules.validateKeyword.message='<spring:message code="messages.validate.loginIDLength"/> '+3+' <spring:message code="label.to"/> '+20;
		        			  return false;  
		        			}
		        		  return true;  
		        	}else{
		        		$.fn.validatebox.defaults.rules.validateKeyword.message='<spring:message code="messages.validate.space"/>';
		        		  return false;  
		        	}
		        },  
		    
		        message: 'default message'
		    }

		});  
	</script>
	
	
<div id="masterToolbar" class="dialog-toolbar" style="position:absolute;top:30px;z-index:99999;width:100%">


	<a id="btnAdd" href="javascript:;" class="easyui-linkbutton button button-magenta l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled" data-options="iconCls:'fa fa-plus'" plain="true" onclick="MenuClicked('Add')" group=""><label>Add</label></a> 
	<a id="btnSave" href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small l-btn-plain" iconcls="fa fa-floppy-o" plain="true" onclick="MenuClicked('Draft')" group=""><label>Save</label></a> 
	<a id="btnConfirm" href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small l-btn-plain" iconcls="fa fa-check-square-o" plain="true" onclick="MenuClicked('Confirm')" group=""><label>Confirm</label></a> 
	<div id="statusDiv"></div>
	<a id="btnDelete" href="javascript:;" class="easyui-linkbutton l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled" iconcls="fa fa-trash" plain="true" onclick="MenuClicked('Delete')" group=""><label>Delete</label></a> 
</div>

<form id ="fmCountry" class="normform" method = "post" data-enter-as-tab="true">

<br /><br />
 	<input type="hidden" id="hidMode" value="<%=Mode%>" />
    <input type="hidden" id="hidValue" value="<%=Value%>" />
    <input type="hidden" id="hidItemClicked"  />
    <input type="hidden" id="hidHasChanges" value="0" />
    <input type="hidden" id="hidRecLevel" value="0" />
    
		 <input type="hidden" id="hidkey" name="cntKey" />
         <input type="hidden" id="hidStatus" name="status" />
         <input type="hidden" id="hidCreatedUser" name="createdUser"/>
         <input type="hidden" id="hidCreatedDate" name="createdDateTime"/>
         <input type="hidden" id="hidUpdatedUser" name="updatedUser"/>
         <input type="hidden" id="hidUpdatedDate" name="updatedDateTime"/>
         <input type="hidden" id="hidCreatedUserName" name="createdUserName"/>
         <input type="hidden" id="hidUpdatedUserName" name="updatedUserName"/>
        <h3 style="margin-top: 0px"><div id="statusDiv"> </div></h3>  
      
 <fieldset>
  <table style="width:100%">
    	
    	
    <br><br><br>
    <tr>
    	<td class="tdLabels"><label><spring:message code="label.name"/><b><span style="color: red;"> * </span></b></label>
    	</td>
    	<td>	
		<input name="nameS"  id="nameS" maxlength=30 class="easyui-validatebox" required="true" onChange="HasChanges()"> 
    	</td>
    	</tr>
    </table>  
     </fieldset>         
</form>
</body >
</html>