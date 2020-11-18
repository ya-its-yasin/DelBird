$.messager.progress();
var moduleKey="";
var parentKey="";
var lang=document.getElementById("lang");
var tempData;
var selectedId;
$.fn.panel.defaults.onBeforeOpen=function(){	
	if(checkConnection()=="true")
		{
		return true;
		}
	else
		{
		this.close();
		}
};
$.fn.panel.defaults.onClose=function()
{	
	if(checkConnection()=="true");		
};
$(document).unbind('keydown').bind('keydown', function (event) {
    var doPrevent = false;
    if (event.keyCode === 8) {
        var d = event.srcElement || event.target;
        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD')) 
             || d.tagName.toUpperCase() === 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        }
        else {
            doPrevent = true;
        }
    }

    if (doPrevent) {
        event.preventDefault();
    }
});
$(document).ready(function ()
     {
	
	

	
	   $('#lnkLang'+lang.value).hide();
	   $('#lnkLang'+rlang.value).show();
	   $("#lnkLangen").click(function(e){
		    e.preventDefault();//this will prevent the link trying to navigate to another page
		    var href = $(this).attr("href");//get the href so we can navigate later

		    //do the update

		    //when update has finished, navigate to the other page
		    window.location = href;
		});
		$("#lnkLangar").click(function(e){
		    e.preventDefault();//this will prevent the link trying to navigate to another page
		    var href = $(this).attr("href");//get the href so we can navigate later

		    //do the update

		    //when update has finished, navigate to the other page
		    window.location = href;
		});
	
	$('#logoutBtn').bind('click', function()
	{
		
       var currentUserKey=document.getElementById("currentUserKey").value ;  
      
       $.ajax({
 		  type: "GET",
 		  url: "userLogoutLog.do",
 		 async:false,
 		  data:{"currentUserKey":currentUserKey}
 		 
 		});
       
    });

	/*====================Start of Dynamic accordion Menu============== */
	
	
	 $.ajax({
		  type: "GET",
		  url: "ModuleContent.do",
		  async:false,
		  dataType: "json"
		}).done(function(msg2) 
		{		
			
				/*$('#aa').tabs({				
		     	onSelect:function(title,index){ 	
		     		if(checkConnection()=="true")
		      	   	{
		     		var tabid=$('#aa').tabs('getTab',index)[0].id;
		     		moduleKey=tabid;
		     		generateModule(tabid);
		      	   	}
		        }  
		 });	*/
			var str="";
			
			 $.each(msg2, function(index, item)
	    			  {     			  
				
				 var itemName="";
				
				 if(document.getElementById("lang").value=='ar')
					 {
					  itemName=item.nameS;
					 }
				 else
					 {
					  itemName=item.nameP;
					 }			
				
					var isSelected=false;
					
				 if(item.moduleKey==$('#moduleId').val()){
					 isSelected=true;
				 }

				 if($('#moduleId').val()==''&&index==0){
					 isSelected=true;
				 
				 }
				 if(isSelected==true)
					 {
					 selectedId=item.moduleKey;
str+="<a href='#' id="+item.moduleKey+" class='blackBtn' title='"+itemName+"'	  onClick='updateMainPanel(\""+item.moduleKey+"\",\""+item.keyword+"\",\""+itemName+"\")'>" +
		"<table style='height:100%;'><tr style='height:100%;'><td   width='35px' style='background-size: 46px 46px;font-size: 25px;color:white;' valign='middle' class='"+item.moduleCls+"'></td>" +
				//"<td style='vertical-align: middle;color: white;font-family: verdana;'>" +""+itemName+"</td>"+
		"</tr></table></a>";
updateMainPanel(selectedId,item.keyword,itemName);
					 }
				 else
					 {
					 str+="<a href='#' id="+item.moduleKey+" class='orangeBtn'	title='"+itemName+"'  onClick='updateMainPanel(\""+item.moduleKey+"\",\""+item.keyword+"\",\""+itemName+"\")'>" +
						"<table style='height:100%;'><tr style='height:100%;'><td   width='35px' style='background-size: 46px 46px;font-size: 25px;color:white;' valign='middle' class='"+item.moduleCls+"'></td>" +
								//"<td style='vertical-align: middle;color: white;font-family: verdana;'>" +""+itemName+"</td>"+
						"</tr></table></a>";
					 }
			 		});
			 //alert(str);
			 $("#menudiv").html(str);
		});
	
	

		    	
	 

		
   
		
	
     });



/*====================Start of Select Theme ============== */
function updateMainPanel(id,keyword,menuName)
{
	$.messager.progress();
	var chkConn=checkConnection();
	if(chkConn=="true"){
		var url='';
	if(keyword=="StudentManagement"){
		url=keyword+".do";				
	}
	if(keyword=="StaffManagement"){
		url=keyword+".do";		
	}
	if(keyword=="Setup"){
		url=keyword+".do?moduleKey="+id;		
	}
	if(keyword=="Reports"){
		url=keyword+".do?moduleKey="+id;				
	}
	if(keyword=="Logs"){
		url=keyword+".do?moduleKey="+id;				
	}
	if(keyword=="CourseManagement"){
		url=keyword+".do?moduleKey="+id;			
	}
	if(keyword=="Accounts"){
		url=keyword+".do?moduleKey="+id;			
	}
	if(id!=selectedId)
		{
		$('#'+id).switchClass("orangeBtn","blackBtn");
		$('#'+selectedId).switchClass( "blackBtn", "orangeBtn");	
		}
	selectedId=id;
	$('#mainPanel').panel('refresh',url).panel('setTitle',menuName);
	var menuPanel=$('#GridPanelLayout').layout('panel', 'west');
	menuPanel.panel('setTitle',menuName);
	$('#menuTree').tree({url:'ModulePanel.do?modulekey='+id});
	$('#menuTree').tree({
		onBeforeSelect: function(node){
			if(node.attributes==undefined || node.attributes.url==undefined)
			{
				return false;
			}	
			return true;
	},
		onSelect: function(node){
			debugger;
			
					$.messager.progress();
					var cp=$('#GridPanelLayout').layout('panel', 'center');
					cp.panel('refresh',node.attributes.url);
									
		},
		onLoadSuccess:function(node,data)
		{debugger;
			$.messager.progress('close');
			var cp=$('#GridPanelLayout').layout('panel', 'center');
			cp.panel('clear');
		}
	
	});
	
	}// end chkConn if condition
}

function ChangeTheme(value)
     {
	  
	//alert(value);
  	   if(checkConnection()=="true")
  	   	{
  	   
  	  $.messager.confirm('Confirm', 'Do you Want to set as default Theme?', function(r)
  			   {  
             if (r)
             {  

          	   $.ajax({
             		  type: "GET",
             		  url: "changeTheme.do",
             		  data: { "themeName": value,"setAsDefaultTheme":"YES"}
             		}).done(function( msg ) 
             		{
             			
             		window.location.reload();
             			
             		});
             } 
             else
          	   {
          	   $.ajax({
             		  type: "GET",
             		  url: "changeTheme.do",
             		  data: { "themeName": value,"setAsDefaultTheme":"NO"}
             		}).done(function( msg ) 
             		{
             		window.location.reload();
             			
             		});
          	   
          	   }
         });  
     }
     }

  /*====================END of Select Theme ============== */

/*function changeLanguage(toLang,fromLang)
{
	e.preventDefault();//this will prevent the link trying to navigate to another page
    var href = $(this).attr("href");//get the href so we can navigate later

    //do the update

    //when update has finished, navigate to the other page
    window.location = href;debugger;
$('#lnkLang'+toLang).show();
$('#lnkLang'+fromLang).show();		
return true;

}*/

/*function changeLanguage(lang,desc)
{
	debugger;
	
	if(lang=="en")
		{
			$('#lnkEnglish').hide();
			$('#lnk'+desc).show();
		}
	else if(lang=='ar')
		{
			$('#lnkEnglish').show();
			$('#lnk'+desc).hide();
		}
	else if(lang=='ru')
	{
		$('#lnkEnglish').show();
		$('#lnk'+desc).hide();
	}
	return true;
}*/


function generateModule(value)
{
	
	

$.ajax({
	  type: "GET",
	  url: "ModulePanel.do",
	  async:false,
	data: { "modulekey":value}
	}).done(function( msg ) 
	{ 
	$('#Modules').hide();
	 var tab = $('#aa').tabs('getSelected'); 
		var len=msg.length;
		if(len==0)
			{			
			 $('#aa').tabs('update', {
			 	tab: tab,
			 	options: {			 		
			 		content: "No List found.."  
			 	}
			 });
			}
		else
			{
		var noRows=Math.floor(len/2)+(len%2);		
		var id="panelTable"+value;
		 var str='<div style="height:95%;overflow-y: auto;"> <table id='+id+' border="4" cellpadding="30" cellspacing="20" align="center" width="100%" height="100%">';
		 for(var i=0;i<(noRows);i++)
			 {
			 var tempid=value+"_"+i;
			  str+='<tr><td    id='+tempid+'0></td><td  id='+tempid+'1></td></tr>';
			 }		
		  str+='</table></div>';		  
		  $('#aa').tabs('update', {
			 	tab: tab,
			 	options: {			 		
			 		content: str 
			 	}
			 });
		 $.each(msg, function(index,item1)
			    	{
			 

			 var itemName='<table><tr><td  rowspan="2" width="60px" valign="middle" class="'+item1.icon+'"></td><td class="modHeading"><a href="#"  onClick="generateSubMenus(\''+item1.id+'\',\''+item1.text+'\')"><b>'+item1.text+'</b></a></td></tr>';

			 var tdId=value+"_"+Math.floor(index/2)+''+(index%2);			 
			 var panelContent="<div class='modList'>"+itemName+"<tr><td><table width='100%' class='subList'><tr><td >";
			 var ilen=Math.round(item1.children.length/2);
			 var nextcol=false;
			 $.each(item1.children, function(index1,item2)
					 {
				 		if(index1==(ilen))
				 			{
				 			panelContent+="</td><td>";
				 			nextcol=true;
				 			}
				 		panelContent+='<ul style="padding-top:3px; padding-bottom:3px;text-wrap:suppress;">';
				 		if(nextcol)
				 			{
				 			panelContent+='<span class="splitter"> | </span>';
				 			}
				 		panelContent+='<a href="#" onClick="openDialog(\''+item2.keyword+'\',\''+item2.url+'\',\''+item2.text+'\')" >'+item2.text+'</a></ul>';				 
					 });
			 panelContent+="</td></tr></table></td></tr></table></div>";
			 
			
			 
			 
			 var panelId="menuPanel_"+value+"_"+index;
			 $("#"+tdId).html('<div  class="easyui-panel"  id='+panelId+'> </div>');	
		
			 $('#'+panelId).panel({  
				  width:450,  
				 
				  //title: itemName,
				  noheader:true,
				  iconCls:item1.icon,
				  closable:false,
				  collapsible:false,
				  minimizable:false,
				  maximizable:false,				  
				  content:panelContent,
				  headerCls:'modList',
				  bodyCls:'bdcls'
				});
			
			 
			 
			
			 
			    	});
			}
		
		 
  
		
	});
}

function generateSubMenus(value,name)
{	
	$('#Modules').hide();	
	parentKey=value;
	$.ajax({
		  type: "GET",
		  url: "MenuPanel.do",
		  async:false,
		data: { "menuKey":value}
		}).done(function( msg ) 
		{ 			
			 var tab = $('#aa').tabs('getSelected'); 
			var len=msg.length;
			var trow=0;
			if(len%4>0)
				trow=1;
			var noRows=Math.floor(len/4)+trow;		
			 var str='<div style="height:95%;overflow-y: auto;"> <table style="display: block;" id="panelTable"'+moduleKey+' border="4" cellpadding="10" cellspacing="10" align="center"  >';
			 for(var i=0;i<(noRows);i++)
				 {
				 var tempid=moduleKey+"_"+i;
				  str+='<tr><td    id='+tempid+'0></td><td  id='+tempid+'1></td><td  id='+tempid+'2></td><td  id='+tempid+'3></td></tr>';
				 }		
			  str+='</table></div>';
			  
			  $('#aa').tabs('update', {
				 	tab: tab,
				 	options: {			 		
				 		content: str  
				 	}
				 });
			 $.each(msg, function(index,item1)
				    	{
				 
				 var tdId=moduleKey+"_"+Math.floor(index/4)+''+(index%4);			 
				 var panelContent= '<div class="cptd"><table  width="250px"><tr><td width="60px" valign="middle" rowspan="2" class="'+item1.icon+'"> </td><td><a href="#" onClick="openDialog(\''+item1.keyword+'\',\''+item1.url+'\',\''+item1.text+'\')" ><b>'+item1.text+'</b></a></td></tr><tr><td class="tdwrap">';  
				 panelContent+='' + item1.desc + '</td></tr>';  		              
				 panelContent+='</table><div>'  ;
				 
				 $("#"+tdId).html(panelContent);				 
						 
				    	});
			 
			
			 
	  
			
		});
}

function openDialog(keyword,url,name)
{
	debugger;
	var chkConn=checkConnection();
	if(chkConn=="true"){
		createMasterDlg();
	if(keyword=="ResetPassword"){
		$('#masterDlg').dialog({
			height:530,
			width:600,
			fit:false
			
		
		});	
		
	}else if(keyword=="TransferUserTo"){
		$('#masterDlg').dialog({
			height:550,
			width:1024,
			fit:false
			
		
		});	
		
	}	
	else 
		if(keyword=="EmailConfig"){
			$('#masterDlg').dialog({
				height:420,
				width:500,
				fit:false
				
			
			});	
		}
			
			else 
				if(keyword=="AdConfig"){
					$('#masterDlg').dialog({
						height:500,
						width:550,
						fit:false
						
					
					});	
		}else if(keyword=="UnlockUser"){
			$('#masterDlg').dialog({
				height:530,
				width:650,
				fit:false
				
			
			});	
			
		}
	
	else{
		
		$('#masterDlg').dialog({fit:true});	
	}
if(url=='ScrollViewGrid.do')
{
	
	url="ScrollViewGrid.do?keyword="+keyword;
	
}
	
	if(url=='undefined' || url=="")
		{
		url="CommonGrid.do?keyword="+keyword;
		//url="ScrollViewGrid.do?keyword="+keyword;
		}	
	
		$('#masterDlg').dialog('open').dialog('refresh', url).dialog('setTitle',name);
		
	}// end chkConn if condition
}

function openLink(url)
{
	
	finalUrl=url+".do";
	createMasterDlg();
	if(url=="UserPreference")
		{
		
	$('#masterDlg').dialog({
		height:330,
		width:550,
		fit:false
	});	
		}
	else if(url=="ChangePassword")
	{
		
		$('#masterDlg').dialog({
			height:350,
			width:570,
			fit:false
		});	
			}
	else
		{
		$('#masterDlg').dialog({
			
			fit:true
		});	
		}
	$('#masterDlg').dialog('open').dialog('refresh', finalUrl).dialog('setTitle',url);
}
function createMasterDlg()
{
	
	$('<div id="masterDlg" class="easyui-dialog"   closed="true"  data-options="iconCls:\'icon-master\',modal:true,resizable:false,inline:true" fit=true > </div>').appendTo("#CenterPanel");     
	
$('#masterDlg').dialog({
		
		onOpen: function()
		{			
			$('#mainSearch').val("");
			$('#mainSearch').attr("disabled", true);
			$('#submit').removeAttr("onclick");	
			tempData=null;
		},
		onClose: function()
		{			
			$('#mainSearch').attr("disabled", false);
			$('#submit').bind('click', function()
			{
				doMenuSearch();
			});	
			tempData=null;
			$('#masterDlg').dialog('destroy').empty();
			if(gridData!=undefined && gridData!=null){
				gridData=null;
			}
			
		}
	});
	
}
function createOutsideDlg()
{
	
	$('<div id="masterDlg" class="easyui-dialog"   closed="true"  data-options="iconCls:\'icon-master\',modal:true,resizable:false" fit=true > </div>').appendTo("#CenterPanel");     
	
$('#masterDlg').dialog({
		
		onOpen: function()
		{			
			$('#mainSearch').val("");
			$('#mainSearch').attr("disabled", true);
			$('#submit').removeAttr("onclick");	
			tempData=null;
		},
		onClose: function()
		{			
			$('#mainSearch').attr("disabled", false);
			$('#submit').bind('click', function()
			{
				doMenuSearch();
			});	
			tempData=null;
			$('#masterDlg').dialog('destroy').empty();
			if(gridData!=undefined && gridData!=null){
				gridData=null;
			}
			
		}
	});
	
}

var searchResult;
function searchKeyUp(event)
{ 
	if (event.keyCode == 13) {
	            doMenuSearch();
	}   
}
function doMenuSearch(){
	$('#Modules').hide();
	var value=$('#mainSearch').val();
//	alert(value);
	if(value!='')
		{
		$.ajax({
			  type: "POST",
			  url: "searchMenus.do?value="+value+"&module="+moduleKey+"&parent="+parentKey,
			  async:false			
			}).done(function( msg ) 
			{
				 var tab = $('#aa').tabs('getSelected');
				 var len = msg[0].children.length+msg[1].children.length;
				 var str="",str1="",resultstr="";
				 if (len > 0) {
					 if(len==1)
						 {
						 resultstr="1 result found.. ";
						 }
					 else
						 {
					 resultstr=len+" results found..";
						 }
					 var backlink="";
					 if(parentKey!="")
						 {
						 backlink='<div class="backlink"><a href="#"  onClick="generateSubMenus(\''+parentKey+'\')">Back</a></div>';
						 }
					 else if(moduleKey!="")
						 {
						 backlink='<div class="backlink"><a href="#"  onClick="generateModule(\''+moduleKey+'\')">Back</a></div>';						
						 }
					// resultstr="<div class='resultstr'>"+resultstr+backlink+"</div>";
					 resultstr="<div class='resultstr'><table width='100%'><tr><td class='icon-search' height='20px' width='20px'></td><td><b>Search Results</b></td><td>"+
					 resultstr+backlink+"</div></td></tr></table></div>";
					 if(msg[0].children.length>0)
						 {
						 var trow = 0;
						 len=msg[0].children.length;
						 if (len % 4 > 0)
							 trow = 1;
						 var noRows = Math.floor(len / 4) + trow;
						  str= '<div class="samePage"><h1 >Search Results from '+tab.panel('options').tab[0].textContent+' Module</h1><table class="paneltables"  id=panelTables'+ moduleKey
							 + ' border="4" cellpadding="10" cellspacing="10" align="center"  >';
						 for ( var i = 0; i < (noRows); i++) {
							 var tempid = moduleKey + "s_" + i;
							 str += '<tr><td    id=' + tempid
							 + '0></td><td  id=' + tempid
							 + '1></td><td  id=' + tempid
							 + '2></td><td  id=' + tempid
							 + '3></td></tr>';
						 }
						 str += '</table></div>';
						 
						
						
						 }
					 if(msg[1].children.length>0)
					 {
					 var trow = 0;
					 len=msg[1].children.length;
					 if (len % 4 > 0)
						 trow = 1;
					 var noRows = Math.floor(len / 4) + trow;
					 var otherresult='<h1>Search Results from other Modules</h1>';
					 if(tab==null)
						 {
						 otherresult='<h1>Search Results from all Modules</h1>';
						 }
					 str1 = otherresult+'<table style="display: block;" class="paneltables" id=panelTableo'+ moduleKey
						 + ' border="4" cellpadding="10" cellspacing="10" align="center"  >';
					 for ( var i = 0; i < (noRows); i++) {
						 var tempid = moduleKey + "o_" + i;
						 str1 += '<tr><td    id=' + tempid
						 + '0></td><td  id=' + tempid
						 + '1></td><td  id=' + tempid
						 + '2></td><td  id=' + tempid
						 + '3></td></tr>';
					 }
					 str1 += '</table>';
					 }
					 var con=resultstr+str+str1+"<div style='height:70px;display:block;'></div>";
					 if(tab==null)
						 {
						 $("#Modules").html(con);
						 $('#Modules').show();	
						 }
					 else
						 {
						 $('#aa').tabs('update', {
							 tab : tab,
							 options : {
								 content : con
							 }
						 });
						 }
					
					 
					 $.each(msg[0].children,function(index, item1) {
						 var tdId = moduleKey + "s_" + Math.floor(index / 4) + '' + (index % 4);
						 var panelContent = '<div class="cptd"><table  width="250px"><tr><td width="60px" valign="middle" rowspan="2" class="'
							 + item1.icon + '"> </td><td><a href="#" onClick="openDialog(\'' + item1.keyword+ '\',\'' + item1.url + '\',\''
							 + item1.text + '\')" ><b>' + item1.text + '</b></a></td></tr><tr><td class="tdwrap">';
						 panelContent += '' + item1.desc + '</td></tr></table><div>';								

						 $("#" + tdId).html(panelContent);								 				 
					 });
					 $.each(msg[1].children,function(index, item1) {
								 var tdId = moduleKey + "o_" + Math.floor(index / 4) + '' + (index % 4);
								 var panelContent = '<div class="cptd"><table  width="250px"><tr><td width="60px" valign="middle" rowspan="2" class="'
									 + item1.icon + '"> </td><td><a href="#" onClick="openDialog(\'' + item1.keyword+ '\',\'' + item1.url + '\',\''
									 + item1.text + '\')" ><b>' + item1.text + '</b></a></td></tr><tr><td class="tdwrap">';
								 panelContent += '' + item1.desc + '</td></tr></table><div>';								

								 $("#" + tdId).html(panelContent);								 				 
							 });
					
					
				 } else {
					 $.messager.alert('Search', 'No Results found..');
				 }
				///////////////debugger;
			
			});
		}
	else
		{
		
		$.messager.alert('Search','Please input search text..');
		$('#mainSearch').focus();
		}
} 
function myFavourites()
{
$('#Modules').hide();
	$.ajax({
		  type: "POST",
		  url: "getFavouritesFromUserMenuLink.do",
		  async:false		
		}).done(function( msg ) 
		{
		
		 var tab = $('#aa').tabs('getSelected'); 
		 var resultstr="";
		 var backlink="";
		 if(parentKey!="")
			 {
			 backlink='<span class="splitter"> | </span><a href="#"  onClick="generateSubMenus(\''+parentKey+'\')">Back</a>';
			 }
		 else if(moduleKey!="")
			 {
			 backlink='<span class="splitter"> | </span><a href="#"  onClick="generateModule(\''+moduleKey+'\')">Back</a>';						
			 }
		 resultstr="<div class='resultstr'><table width='100%'><tr><td class='icon-favourites' height='20px' width='20px'></td><td><b>My Favourites</b></td><td>"+
		 '<div class="backlink"><a href="#"  onClick="openLink(\'ManageFavourites\')">Edit</a>    '
			 +backlink+"</div></td></tr></table></div>";
			var len=msg.length;
			var trow=0;
			if(len%4>0)
				trow=1;
			var noRows=Math.floor(len/4)+trow;		
			 var str='<table style="overflow-y: scroll;display: block;" id="panelTablef" border="4" cellpadding="10" cellspacing="10" align="center"  >';
			 for(var i=0;i<(noRows);i++)
				 {
				 var tempid="f_"+i;
				  str+='<tr><td    id='+tempid+'0></td><td  id='+tempid+'1></td><td  id='+tempid+'2></td><td  id='+tempid+'3></td></tr>';
				 }		
			  str+='</table>';
			  
			  if(tab==null)
				 {
				 $("#Modules").html(resultstr+str);
				 $('#Modules').show();	
				 }
			 else
				 {
				 $('#aa').tabs('update', {
					 tab : tab,
					 options : {
						 content : resultstr+str
					 }
				 });
				 }			  
			 $.each(msg, function(index,item1)
				    	{
				 
				 var tdId="f_"+Math.floor(index/4)+''+(index%4);			 
				 var panelContent= '<div class="cptd"><table  width="250px"><tr><td width="70px" valign="middle" rowspan="2" class="'+item1.icon+'"> </td><td><a href="#" onClick="openDialog(\''+item1.keyword+'\',\''+item1.url+'\',\''+item1.text+'\')" ><b>'+item1.text+'</b></a></td></tr><tr><td class="tdwrap">';  
				 panelContent+='' + item1.desc + '</td></tr>';  		              
				 panelContent+='</table><div>'  ;				 
				 $("#"+tdId).html(panelContent);			 
							 
				    	});
			 
	  
			
		});
}
function checkConnection()
{
	
	var res="true";
	$.ajax({
	      type: "GET",
	      url:'checkConnection.do' ,   	    
	    	  async:false, dataType: "json"
	    }).done(function( msg ) 
	      {
	    	
	    	if(msg!='ACTIVE')
	    	{
	    		res="false";
	    		//$.messager.alert('Alert','Your Current Session has Expired.. Please Login Again!','warning', function(ok)
	    				//{  
	    			
	                		//if (ok==undefined)
	                		//{  
	                			res="false";
	                			//window.location.reload();
	    window.location.href = window.location.pathname.substring( 0, window.location.pathname.lastIndexOf( '/' ) + 1 ) + 'sessionexpired.do';
	                		//} 
	                		
	    				//}); 
	    	}
	    	else{
    			res="true";
    			
    			}
	      });	
	
	return res;
}
