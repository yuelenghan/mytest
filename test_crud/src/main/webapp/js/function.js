//调试
function mydebug(state){
	if(state == true) { 
		//alert('现在是开发阶段');
	} else {
		//alert('调试已关闭');
	}	
}

//加载内部页面
function Loading_Page(url,id){
	$(id).load(url+'.html',{},function(){
		//加载后执行
	});
}