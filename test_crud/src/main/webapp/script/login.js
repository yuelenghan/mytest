var checkDataResult;
var checkPasswordResult;
var confirmPassowrdResult;
var checkPhoneResult;
function checkData() {
	var userName = $("#userName").val();

	// 去除两边空格
	userName = userName.replace("^\s+|\s+$", "");
	
//	if (userName.length == 0) {
//		alert("请输入用户名！");
//		$("#userName").focus();
//		checkDataResult =  false;
//		return;
//	}
//	if (userName.length > 20) {
//		alert("用户名太长！");
//		$("#userName").focus();
//		checkDataResult = false;
//		return;
//	}

	// 3-20位字母数字下划线
//	var partenUsername = /^\w{3,20}$/;
//	if (!partenUsername.test(userName)) {
//		alert("用户名格式不对！");
//		$("#userName").focus();
//		checkDataResult =  false;
//		return;
//	}

	$.ajax({
				url : "/test_crud/checkData",
				dataType : "json",
				type : "POST",
				data : "userName=" + userName,
				success : function(data, textStatus, jqXHR) {
					$("#result").attr("style", data.style);
					$("#result").html(data.content);
					if(data.content == '用户名可用') {
						checkDataResult = true;
					} else {
						checkDataResult =  false;
					}
				}
			});

}

function checkPassword() {
	var password = $("#password").val();
	if (password.length < 4) {
		alert("密码不能小于4位！");
		$("#password").focus();
		checkPasswordResult =  false;
	} else {
		checkPasswordResult = true;
	}
}

function confirmPassword() {
	var password = $("#password").val();
	var password2 = $("#password2").val();
	if (password != password2) {
		alert("两次密码输入不一致！");
		$("#password2").focus();
		confirmPassowrdResult =  false;
	} else {
		confirmPassowrdResult =  true;
	}
}

function checkPhone() {
	var phone = $("#phone").val();
	var partenPhone = /^1{1}\d{10}$/;
	if (!partenPhone.test(phone)) {
		alert("手机号码格式不对！");
		$("#phone").focus();
		checkPhoneResult =  false;
	} else {
		checkPhoneResult =  true;
	}
}

function checkAll() {
	if(!checkDataResult || !checkPasswordResult || !confirmPassowrdResult || !checkPhoneResult) {
		return false;
	} 
}
