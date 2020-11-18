<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>
<body>
<script>
var gridData=null;
$(document).ready(function ()
	     {$.messager.progress('close');
	$.ajax({
	      type: "POST",
	      url: 'CountryGridData.do',    
	      dataType: 'json'
	    }).done(function( responseJson ) 
	      {
	    	debugger;
	    	gridData=responseJson;
	      });
	     });
function doSearch(value,name){
	var rows=gridData;
	
	var opts = $('#countryGrid').datagrid('getColumnFields');
	
	    var trows=[];
	   
		    for(var i=0;i<rows.length;i++)
		    	{
		    	for(var j=0;j<opts.length;j++)
		    		{
		    			var objToStr;
		    			if(rows[i][opts[j]] != undefined){
		    				objToStr = rows[i][opts[j]];
		    			}
			    		if(objToStr.toString().search(new RegExp(value, "i"))!=-1){
			    			trows.push(rows[i]);
			    			j=opts.length;
			    		}	    		
		    		}	    	
		    	}
	    	
	    
	    $('#countryGrid').datagrid('loadData',trows);
} 	     
function toolClick(btnMode,index)
{  if(checkConnection()=="true")
	{
	debugger;
	var mode="V";	
	var Val="";		
	var opts = $('#countryGrid').datagrid('options');
	var rowSelect = $('#countryGrid').datagrid('selectRow',index);
	var row = $('#countryGrid').datagrid('getSelected');	
	$('#countryGrid').datagrid('unselectAll');	
	switch(btnMode)
	{
    	case "Add":
    		mode="A";    		
    		break;
    	case "Delete":
    		mode="D";    		      
            if (row)
            {  
               Val=row[opts.idField];
               
               var status=row.activeYN;              
               if(status=="A"||status=="Y")
               {
            	   window.parent.$.messager.alert('<spring:message code="messages.alert"/>', '<spring:message code="messages.deletemessages"/>'); 
            	   return false;
               }
            } 
            else
        	{	            
            	window.parent.$.messager.alert('<spring:message code="messages.alert"/>', '<spring:message code="messages.selectRecord"/>'); 
            	return false;
        	}           
    	 	break;
    	case "View":
    		mode="V";    	                
            if (row)
            {  
               Val=row[opts.idField];
            } 
            else
        	{	            	
            	window.parent.$.messager.alert('<spring:message code="messages.alert"/>', '<spring:message code="messages.selectRecord"/>'); 
            	return false;
        	}
    		break;    	
    }	
	url="Country.do?Mode="+mode+"&Value="+Val;
	if(mode=="D")
		{
				
					$.messager.confirm('Confirm','Do you want to delete this record?',function(r){
								if (r){
					 $.ajax({
					        type: "POST",
					        url:'deleteCountry.do?key='+Val+'&flag=Delete',       
					        dataType: 'json',
					        success: function(obj) {		        	
						    	  if(obj.keyword=="success")
						    		  {
						    		  $('#countryGrid').datagrid('reload'); 								    		 
							    	  $.messager.alert('<spring:message code="messages.deleted"/>',obj.message);								    	  
						    		  }
						    	  else if(obj.keyword=="failure")
						    		  {
							    	  $.messager.alert('<spring:message code="message.error"/>',obj.message);				    	  
						    		  }
					          }
					      });
								}
					});
			 
		}
	else
		{
		
		createFormDlg();

		$('#FormDlg').dialog({			    
		    width:650,   
		    height: 300     
		}); 	
		url+="&w="+(500-14);
		
		$('#FormDlg').dialog('open').dialog('refresh', url).dialog('setTitle','<spring:message code="messages.setCountryTitle"/>');
		}

	
	
	
	
	
	}
}   
function createFormDlg()
{
	$('#dynaform').html('<div id="FormDlg" class="easyui-dialog" style="width:650px;height:300px; "  closed="true"  data-options="iconCls:\'icon-master\',modal:true" ></div> ');     
	$('#FormDlg').dialog({
		
		onClose: function()
		{
			debugger;
			
			checkConnection();
			if($('#ss').val()==""){
			$('#countryGrid').datagrid('reload'); 	
			}
			$('#FormDlg').dialog('destroy').empty();
			$('#dynaform').html('');
		} 
	
	});
}
 function rowdblClicked(index, row) {
    debugger;
    var mode="V";	
	var Val="";		
	var opts = $('#countryGrid').datagrid('options');
	var rowSelect = $('#countryGrid').datagrid('selectRow',index);
	var row = $('#countryGrid').datagrid('getSelected');	
	mode="E";
	 if (row)
     {
     	Val=row[opts.idField];
     } 
    var url = "Country.do?Mode=E&Value="+Val;
    var height = "300";
    var width = "650";
   createFormDlg('<spring:message code="messages.setCountryTitle"/>', height, width, title);
    $('#FormDlg').dialog('open').dialog('refresh', url).dialog('setTitle','<spring:message code="messages.setCountryTitle"/>');
} 
  function ActionTemplate(value, row, index) {
	  debugger;
	     <%--  if (row.createdUser == '<%=request.getSession().getAttribute("loginUser")%>')  --%>
	    	 if(row.status=="I")
	    	 {
	    	     return '<a style="cursor: pointer;" id="btnGEdit" href="javascript:void(0)"  iconCls="fa fa-pencil" plain="true" onclick=rowdblClicked(' + index + ')><i class="fa fa-pencil" aria-hidden="true" style="font-size:15px"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
	    	     '<a style="cursor: pointer;" id="btnGView" href="javascript:void(0)"  iconCls="fa fa-eye" plain="true" onclick=toolClick("View",' + index + ')><i class="fa fa-eye" aria-hidden="true" style="font-size:15px"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
	    	     '<a style="cursor: pointer;" id="btnGDelete" href="javascript:void(0)"   iconCls="fa fa-trash" plain="true" onclick=toolClick("Delete",' + index + ')><i class="fa fa-trash" aria-hidden="true" style="font-size:15px"></i></a>';
	    	     
	    	 } 
	    	 
	    	 else(row.status=="A")
	    		   {
	    	     return '<a style="cursor: pointer;" id="btnGEdit" href="javascript:void(0)"  iconCls="fa fa-pencil" plain="true" onclick=rowdblClicked(' + index + ')><i class="fa fa-pencil" aria-hidden="true" style="font-size:15px"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
	    	     '<a style="cursor: pointer;" id="btnGView" href="javascript:void(0)"  iconCls="fa fa-eye" plain="true" onclick=toolClick("View",' + index + ')><i class="fa fa-eye" aria-hidden="true"style="font-size:15px"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
	    	     '<i class="fa fa-trash" aria-hidden="true" disabled:"true" style="font-size:15px;color:lightgray"></i>';

	    		   }
	    
	  }
function activeColumn(value, row, index) {
	debugger;
    switch(value)
    {
    case "I":
   	 return"<span style='color:red'>Inactive</span>";
   	 break;
   
    case "A":
   	 return"<span style='color:green'>Active</span>";
   	 break;
    }
    }

</script>
<table class="easyui-datagrid"  title="Country" fit="true" toolbar="#tbCountry" id="countryGrid" 
        data-options="url:'CountryGridData.do',fitColumns:true,singleSelect:true,idField:'cntKey',striped:true,remoteSort:false,onDblClickRow:rowdblClicked">
    <thead>
        <tr>
           <!--  <th data-options="field:'keyword',width:100,halign:'center',sortable:true">Keyword</th> -->
            <th data-options="field:'nameS',width:100,halign:'center'"><label><spring:message code="grid.name"/></label></th>
           <!--  <th data-options="field:'moduleCls',width:200,halign:'center'">Style class</th> -->
             <th data-options="field:'status',width:100,align:'center',halign:'center',formatter:activeColumn"><label><spring:message code="grid.status"/></label></th>
              <th data-options="field:'action',width:100,align:'center',halign:'center',formatter:ActionTemplate"><label><spring:message code="grid.actions"/></label></th>
        </tr>
    </thead>
</table>
<div id="tbCountry" >  
 
        <a id="btnGAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus" plain="true" onclick="toolClick('Add')"><b><label ><spring:message code="grid.add"/></label></b></a> 
        <!--  <a id="btnGEdit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-pencil" plain="true" onclick="toolClick('Edit')"><b>Edit</b> </a>   -->
         <a id="btnGView" href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-eye" plain="true" onclick="toolClick('View')"><b><label ><spring:message code="grid.view"/></label></b></a> 
       <!--   <a id="btnGDelete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-trash" plain="true" onclick="toolClick('Delete')"><b>Delete</b ></a> -->
       
       
       
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input id="ss"  onkeyup="doSearch(this.value)" data-enter-as-tab="false" style="width:150px"/><span id="g-search-button"></span><!-- <img id="searchImg" alt="" src="images/Search2.png"  style="margin-top:3px;margin-bottom:-5px;" height="20px" width="25px"></span> -->
         </div>
         <div id="dynaform"></div>
</body>
</html>