<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath }/dwr/engine.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/dwr/util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/dwr/interface/MessagePush.js"></script>
<script type="text/javascript">
function onPageLoad(){  
    var userId = '${user.userId}';  
    MessagePush.onPageLoad(userId);  
}  
 
//推送信息    
function showMessage(autoMessage){    
         
    alert(autoMessage);  
         
}    
</script>
</head>
<body style="overflow-x:hidden;" onload="dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);onPageLoad();">
	<h2>Hello World!</h2>
</body>
</html>
