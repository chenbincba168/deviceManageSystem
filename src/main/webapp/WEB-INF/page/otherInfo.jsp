<%--
  Created by IntelliJ IDEA.
  User: my-deepin
  Date: 18-4-7
  Time: 下午9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<link rel="stylesheet" href="<%=basePath%>/lib/bootstrap.min.css"/>
<head>
    <title>info</title>
</head>
<body>
<!-- 导航栏 -->
<div class="sidebar text-left">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div>
                <ul class="nav navbar-nav">
                    <li><a href="<%=basePath%>/customer/toAddPerson"><strong>添加人员</strong></a></li>
                    <li><a href="<%=basePath%>/customer/toShowPersonList"><strong>查询人员</strong></a></li>
                    <li><a href="<%=basePath%>/device/toAddDevice"><strong>添加设备</strong></a></li>
                    <li><a href="<%=basePath%>/device/toShowDeviceList"><strong>查询设备</strong></a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<br/>
<br/>
<br/>
<h1 class="text-center">${message}</h1>


</body>
</html>
