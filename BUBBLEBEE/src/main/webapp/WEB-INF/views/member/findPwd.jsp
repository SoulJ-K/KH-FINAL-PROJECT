<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<style>
.inputBox {
    height: 100%;
    line-height: 12px;
    font-size: 14px;
    padding: 14px 0px;
    outline: none;
    border-style: none;
  	border-bottom:  1px solid rgb(238, 238, 238);
}
.loginBtn{
	border: none;
    width: 325px;
    height: 65px;
    background: #f1e018;
    color: #fff;
    border-radius: 8px;
}
.loginBtn:disabled{
	border: none;
    width: 325px;
    height: 65px;
    background: #adb5bd;
    color: #fff;
    border-radius: 8px;
}
</style>
<body>
	<header id="header">
      <c:import url="../layout/header.jsp"/>
   	</header>

	<section style="padding-top: 115px; padding-left: 20%; width: 78%; text-align: center;">
		<h3 style="font-weight: bold;">비밀번호 재설정</h3>
		<br><br>
		<b>비밀번호를 재설정하려는 계정(이메일)을 입력해 주세요.</b>
		<br><br>
		<form method="post" action="emailSendPage.me" id="emailSendForm">
		<div class="">
			<div class="">
				<input class="inputBox" type="email" id="idBox" placeholder="이메일 입력" size="46" value="" name="email">
			</div>
		</div>
		<br><br>
		<button class="loginBtn" type="button" id="emailSend" disabled onclick="validate();">이메일 발송</button>
		</form>
	</section>
	<script>
		$('#idBox').keyup(function(){
			var id = $('#idBox').val();
			var regExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

			if(regExp.test(id)==false){
				$('#emailSend').attr('disabled', true); 
			} else {
				$('#emailSend').attr('disabled', false);
			}
		})
		
		function validate(){
			var id = $('#idBox').val();
			$.ajax({
  	    		url : 'emailSameCheck.me',
  	    		data : {id : id},
  	    		success : function(data){
  	    			if(data == 'success'){
  	    	  	         alert('가입되지 않은 이메일입니다.');
  	    			} else{
  	    				$('#emailSendForm').submit();
  	    			}
  	    		}
  	   		  })
		}
	</script>
	<footer id="footer" style="padding-top: 115px;">
		<c:import url="../layout/footer.jsp"/>
	</footer>
   
</body>
</html>