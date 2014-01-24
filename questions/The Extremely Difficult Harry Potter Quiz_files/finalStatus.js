function changeStatus(url,resultTextDiv){var xmlHttp;try
{xmlHttp=new XMLHttpRequest();}
catch(e)
{try
{xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");}
catch(e)
{try
{xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");}
catch(e)
{alert("Please enable your browser Java Script !!!");return false;}}}
document.getElementById(resultTextDiv).innerHTML="<img src='/images/loader.gif' /><span style='font-size:80%;'>wait...</span>";xmlHttp.onreadystatechange=function()
{if(xmlHttp.readyState==4){document.getElementById(resultTextDiv).innerHTML=xmlHttp.responseText;}}
xmlHttp.open("GET",url,true);xmlHttp.send(null);}
$("#allow_attempt_request_popup_wizard").live("click",function(e){e.preventDefault();$('#basic-modal-allow_attempt_request').modal({maxHeight:300,maxWidth:450});});$(document).ready(function(){$("#frmRequest").submit(function(){var requestName=$.trim($("#requestName").val());var requestEmail=$.trim($("#requestEmail").val());var requestMessage=$.trim($("#requestMessage").val());var quizAuthorEmail=$.trim($("#quizAuthorEmail").val());var quizAuthorName=$.trim($("#quizAuthorName").val());var qtextTitle=$.trim($("#qtextTitle").val());var quiztitleURL=$.trim($("#titleURL").val());var error='false';if(requestName==""){errorMessage="Please enter your name.<br />";$("#error_message").html(errorMessage);$("#error").hide();$("#error").fadeIn("slow");$("#loader_create").hide();error='true';$("#requestName").focus();return false;}else if(requestEmail==""){errorMessage="Please enter your email id.<br />";$("#error_message").html(errorMessage);$("#error").hide();$("#error").fadeIn("slow");$("#loader_create").hide();error='true';$("#requestEmail").focus();return false;}else if(echeck(requestEmail)==false){errorMessage="Please enter valid email id.<br />";$("#error_message").html(errorMessage);$("#error").hide();$("#error").fadeIn("slow");$("#loader_create").hide();error='true';$("#requestEmail").select();return false;}else if(requestMessage==""){errorMessage="Please enter your message.<br />";$("#error_message").html(errorMessage);$("#error").hide();$("#error").fadeIn("slow");$("#loader_create").hide();error='true';$("#requestMessage").focus();return false;}
$.ajaxSetup({cache:false});var ajax_load="<img src='../images/loader.gif' alt='please wait...' />";var _mainRoot='/';var loadUrl=_mainRoot+"quiz-school/request_attempt.php";var returnHtmlText='';alert(requestName);alert(requestEmail);alert(requestMessage);alert(quizAuthorEmail);alert(quizAuthorName);alert(qtextTitle);alert(quiztitleURL);$("#loader_create").html(ajax_load);$("#loader_create").show();$.modal.close();$("#requestedSuccessfully").html("<strong style='font-size: 18px;'>Your request sent successfully to quiz author.</strong><br /><br />Please wait...<br /><br /><img src='../images/loader.gif' alt='please wait...' />");$("#requestedSuccessfully").modal({maxHeight:100,maxWidth:420});$.ajax({url:loadUrl,type:"POST",data:({rName:requestName,rEmail:requestEmail,rMessage:requestMessage,qAuthorEmail:quizAuthorEmail,qAuthorName:quizAuthorName,qTitle:qtextTitle,titleURL:quiztitleURL}),cache:false,async:false,success:function(data){alert(data);}});return false;});});function echeck(str){var at="@"
var dot="."
var lat=str.indexOf(at)
var lstr=str.length
var ldot=str.indexOf(dot)
if(str.indexOf(at)==-1){return false;}
if(str.indexOf(at)==-1||str.indexOf(at)==0||str.indexOf(at)==lstr){return false;}
if(str.indexOf(dot)==-1||str.indexOf(dot)==0||str.indexOf(dot)==lstr){return false;}
if(str.indexOf(at,(lat+1))!=-1){return false;}
if(str.substring(lat-1,lat)==dot||str.substring(lat+1,lat+2)==dot){return false;}
if(str.indexOf(dot,(lat+2))==-1){return false;}
if(str.indexOf(" ")!=-1){return false;}
return true}