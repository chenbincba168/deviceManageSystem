<%--
  Created by IntelliJ IDEA.
  User: my-deepin
  Date: 21-5-20
  Time: 下午15:50
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
    <title>page</title>
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
                    <li><a>入门学习项目</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
