function clear() {
	//清除多余提示信息
	if($("#nullMsg").className == "" || $("#nullMsg").className == null)
		$("#nullMsg").addClass("hidden");
	if($("#accountMsg").className == "" || $("#accountMsg").className == null)
		$("#accountMsg").addClass("hidden");
	if($("#pwdMsg").className == "" || $("#pwdMsg").className == null)
		$("#pwdMsg").addClass("hidden");
	if($("#accountDiv".className == "has-error"))
		$("#accountDiv").removeClass("has-error");
	if($("#pwdDiv".className == "has-error"))
		$("#pwdDiv").removeClass("has-error");
}


//登录按钮点击时间
$("#btn_login").click(function(){
	var accountNo = $('#inputAccount').val();
	var password = $('#inputPassword').val();
	clear();
	$.ajax({
		type:"post",//请求方式
		data:{"accountNo":accountNo,"password":password},//传递给controller的json数据
		url:"controller/loginController.jsp",//请求地址
		error:function(){//如果出错了，将事件重新绑定
			//$("#accountDiv").addClass("has-error");
			$("#errorMsg").removeClass("hidden");
			},
			success:function(data){ //返回成功执行回调函数。
			//alert(data);
			if(data == -1 ) {
				//空错误
				$("#nullMsg").removeClass("hidden");
				}else if(data == -2 ) {
					//用户不存在
					$("#accountDiv").addClass("has-error");
		            $("#accountMsg").removeClass("hidden");
				}else if(data == -3 ) {
				//密码错误
				$("#pwdDiv").addClass("has-error");
				$("#pwdMsg").removeClass("hidden");
				}else{
					//登录成功后返回首页
					window.location.href = "index.jsp"; 
					}
			}
	});	
})


//重置按钮点击事件
$("#btn_reset").click(function(){
	clear();
})
