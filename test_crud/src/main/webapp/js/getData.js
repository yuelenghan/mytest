var totalPageCount; // 总页数
var totalCount; // 总的记录数
var countPerPage = 20; // 一页显示的最大记录数

$(document).ready(function() {
	$.ajax({
				url : "/test_crud/getData.do",
				dataType : "json",
				type : "POST",
				success : function(data, textStatus, jqXHR) {
					totalCount = data.returnMap.resultCount;
					if (totalCount % countPerPage == 0) {
						totalPageCount = totalCount / countPerPage;
					} else {
						totalPageCount = parseInt(totalCount / countPerPage)
								+ 1;
					}
					initPage();
					showData(data);
				}
			});

});

// 初始化分页
function initPage() {
	$("#pager").pager({
				pagenumber : 1,
				pagecount : totalPageCount,
				buttonClickCallback : pageClick
			});
}

function showData(data) {
	var table = $("#result");
	var tableStr = "";

	tableStr += "<tr>";
	tableStr += "<td class=\"cd\">用户名</td>";
	tableStr += "<td class=\"cd\">年龄 </td>";
	tableStr += "<td class=\"cd\">手机号 </td>";
	tableStr += "<td class=\"cd\">中奖时间 </td>";
	tableStr += "<td class=\"cd\">编辑 </td>";
	tableStr += "<td class=\"cd\">删除 </td>";
	tableStr += "</tr>";

	var n = countPerPage;
	if (data.returnMap.resultList.length < countPerPage) {
		n = data.returnMap.resultList.length;
	}
	for (var i = 0; i < n; i++) {
		tableStr += "<tr>";
		tableStr += "<td>" + data.returnMap.resultList[i].userName + "</td>";
		tableStr += "<td>" + data.returnMap.resultList[i].age + "</td>";
		tableStr += "<td>" + data.returnMap.resultList[i].phone + "</td>";
		tableStr += "<td>" + data.returnMap.resultList[i].date + "</td>";
		tableStr += "<td><a href='update.jsp?id="+data.returnMap.resultList[i].id+"&age="+data.returnMap.resultList[i].age+"&phone="+data.returnMap.resultList[i].phone+"'>编辑</a></td>";
		tableStr += "<td><a href='deleteUser.do?id="+data.returnMap.resultList[i].id+"'>删除</a></td>";
		tableStr += "</tr>";
	}

	table.empty();
	table.html(tableStr);
}

function pageClick(pageclickednumber) {
	$("#pager").pager({
				pagenumber : pageclickednumber,
				pagecount : totalPageCount,
				buttonClickCallback : pageClick
			});
	$.ajax({
				url : "/test_crud/getData.do?start="
						+ ((pageclickednumber - 1) * countPerPage),
				dataType : "json",
				type : "POST",
				success : function(data, textStatus, jqXHR) {
					showData(data);
				}
			});
}

function queryUser() {
	var phone = $("#textfield").val();
	$.ajax({
				url : "/test_crud/queryUser.do?phone=" + phone,
				dataType : "json",
				type : "POST",
				success : function(data, textStatus, jqXHR) {
					if(data !=null && data.returnMap.resultList.length > 0) {
						showData(data);
					} else {
						alert("没中奖！");
					}
				}
			});
}