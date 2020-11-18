var passwordMissmatch=document.getElementById("passwordMissmatch").value;
var validateSpace=document.getElementById("validateSpace").value;

var msgShldCont=document.getElementById("msgShldCont").value;
var msgCannCont=document.getElementById("msgCannCont").value;
var msgNumber=document.getElementById("msgNumber").value;
var msgSpChar=document.getElementById("msgSpChar").value;
var msgAlphabet=document.getElementById("msgAlphabet").value;
var validateLenMsg1=document.getElementById("validateLenMsg1").value;
var validateLenMsg2=document.getElementById("validateLenMsg2").value;
var loginUser=document.getElementById("loginUser").value;
var ppObj;
function getPasswordPolicyByCompKey(compKey){
	
	$.ajax({
        type: "POST",
        url:'getCompanyByKey.do',  
        data:{key:compKey},
        success: function(obj) {
        	debugger;
        	ppObj=obj.data;
        	var data=msgShldCont+" "+ppObj.minNoChars+" "+msgAlphabet+ " ,"+ppObj.minNoNumber+" "+msgNumber+ " ,"+ppObj.minNoSpecialChars+" "+msgSpChar+ " ";
        	var d1="";
        	var d2="";
        	var d3="";
        	var data1="";
        	if(ppObj.restrictSPChars!=""){
        		
        		d1=msgSpChar+" : "+ppObj.restrictSPChars+" ";
        	}
        	
        	if(ppObj.restrictWords!=""){
        		
        		d2="and Words: "+ppObj.restrictWords+",";
        	}
        	 if(ppObj.allowNames=='N'){
        		
        		 d3=loginUser;
        	 }
        	if(d1!="" || d2!="" || d3!=""){
        	 data1="and " +msgCannCont+" "+d1+" "+d2+" "+d3+"  ";
        	}
        	$('#ppDet').html(data);
        	$('#ppDet1').html(data1);
        }
	 });	
	
	
}



$.extend($.fn.validatebox.defaults.rules, {  
	validatePasswordStrenght: {  
        validator: function(value){
        	debugger;
    var charCount=0;
    var numCount=0;
    var spCharCount=0;
        		if(value.length >= ppObj.minLength & value.length <=ppObj.maxLength){
        			
        			
        		    var restrictSPChars = ppObj.restrictSPChars;
        		    var spResChar=restrictSPChars.split(',');
        		    var restrictWords = ppObj.restrictWords;
        		    var spResWord=restrictWords.split(',');
        		  
        		    var allowUserLogId=ppObj.allowNames;
        		
        		    if(allowUserLogId=='N'){
        		    	
        		    	
        		    	
             		    	if(value.match(loginUser)){
             		    		$.fn.validatebox.defaults.rules.validatePasswordStrenght.message=msgCannCont+' "'+loginUser+'"';
              	        		  return false;  
             		    	}
             		    	 
        		    	
        		    }
        		    
        		    
        		    if(restrictWords!="" & spResWord.length>0){
        		    	
        		    	 for (var q=0;q<spResWord.length;q++){
        		    	
        		    	if(value.match(spResWord[q])){
        		    		$.fn.validatebox.defaults.rules.validatePasswordStrenght.message=msgCannCont+' "'+spResWord[q]+'"';
         	        		  return false;  
        		    	}
        		    	 }
        		    }
        			
        			 
        			 if(restrictSPChars!="" & spResChar.length>0){
        				 
        				 for (var p=0;p<spResChar.length;p++){
        					// console.log(value);
        					// console.log(spResChar[p].charCodeAt(0));
        			
        			
        			 for (var i=0;i<value.length;i++)
        			 {
        		          asciiNum = value.charCodeAt(i);
        		          
        		          if(spResChar[p].charCodeAt(0)==asciiNum){
     						 
      						$.fn.validatebox.defaults.rules.validatePasswordStrenght.message=msgCannCont+' '+spResChar[p]+'';
       	        		  return false;  
      					 }
        		      
        		          
        			 if((asciiNum != 32)){
        			 if ((asciiNum>32 && asciiNum<48) || (asciiNum>57 && asciiNum<65)|| (asciiNum>90 && asciiNum<97)|| (asciiNum>122 && asciiNum<127))
        			     {
        				  
        				 spCharCount+=1;
        				 
        		           } 
        		         else if(asciiNum>47 && asciiNum<58)
        		          {
        		        	 numCount+=1;
        		           }else{
        		        	   charCount+=1;
        		           }
        			 
        			 }else{
        	        		$.fn.validatebox.defaults.rules.validatePasswordStrenght.message=validateSpace;
        	        		  return false;  
        	        	}
        				
        			 }
        				 }
        				 
        			 }
        			 if(spCharCount<ppObj.minNoSpecialChars){
        				 
        				 $.fn.validatebox.defaults.rules.validatePasswordStrenght.message=msgShldCont+' '+ppObj.minNoSpecialChars+' '+msgSpChar;
           			  return false;  
        			 }
        			 
        			 else if(numCount<ppObj.minNoNumber){
        				 
        				 $.fn.validatebox.defaults.rules.validatePasswordStrenght.message=msgShldCont+' '+ppObj.minNoNumber+' '+msgNumber;
           			  return false;  
        			 }
        			 
        			 else if(charCount<ppObj.minNoChars){
        				 
        				 $.fn.validatebox.defaults.rules.validatePasswordStrenght.message=msgShldCont+' '+ppObj.minNoChars+' '+msgAlphabet;
           			  return false;  
        			 }else{
        				 return true;  
        				 
        			 }
        				 
        	
        		}else{
        			$.fn.validatebox.defaults.rules.validatePasswordStrenght.message= validateLenMsg1+' '+ppObj.minLength+' '+validateLenMsg2+' '+ppObj.maxLength;
        			  return false;  
        			}
        },  
    
        message: 'default message'
    },
    //--------------------EQUAL VALIDATION------------------------------
    equals: {  
        validator: function(value,param){ 
        
        	if(value == $(param[0]).val()){
        		return true;  
        	}else{
        		return false;
        	}
            
        },  
        message: passwordMissmatch  
    } 

});  