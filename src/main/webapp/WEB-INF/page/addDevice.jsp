<%--
  Created by IntelliJ IDEA.
  User: my-deepin
  Date: 21-5-15
  Time: 下午3:16
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
    <title>添加设备</title>
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
<div class="container">
    <h1 class="text-center">设备信息</h1>
    <hr/>
    <br/>
    <form class="form-inline text-center" action="<%=basePath%>/device/save" method="post">
        <div class="form-group form-inline">
            <label>设备ID：</label>
            <input type="text" name="id" class="form-control"/>
        </div>
        <br/>
        <br/>
        <div class="form-group form-inline">
            <label>设备SN：</label>
            <input type="text" name="deviceSN" class="form-control"/>
        </div>
        <br/>
        <br/>
        <div class="form-group form-inline">
            <label>设备名称：</label>
            <input type="text" name="deviceName" class="form-control"/>
        </div>
        <br/>
        <br/>
        <div class="form-group form-inline">
            <label>设备IP：</label>
            <input type="text" name="deviceIP" class="form-control"/>
        </div>
        <br/>
        <br/>
        <div class="form-group form-inline">
            <label>设备分组：</label>
            <input type="text" name="deviceGroup" class="form-control"/>
        </div>
        <br/>
        <br/>
        <div class="form-group form-inline">
            <label>出入口标识：</label>
            <input type="text" name="deviceDirection" class="form-control"/>
        </div>
        <br/>
        <br/>
        <div class="form-group form-inline">
            <label>属性：</label>
            <input type="text" name="deviceProperty" class="form-control"/>
        </div>
        <br/>
        <br/>
        <br/>
        <input type="submit" class="btn btn-info text-center"/>
        <input type="reset" class="btn btn-info text-center"/>
    </form>
</div>

</body>
</html>
