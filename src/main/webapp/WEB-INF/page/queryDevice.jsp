<%--
  Created by IntelliJ IDEA.
  User: my-deepin
  Date: 21-5-15
  Time: 下午5:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<link rel="stylesheet" href="<%=basePath%>/lib/bootstrap.min.css"/>
<link rel="stylesheet" href="<%=basePath%>/lib/font-awesome.min.css"/>
<head>
    <title>设备列表</title>
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
<div class="container">
    <h1 class="text-center">设备列表信息页面</h1>
    <hr/>
    <br/>
    <form class="form-inline" action="<%=basePath%>/device/findByPage" method="post">
        <div class="form-group">
            <label>设备SN：</label>
            <input type="text" class="form-control" name="deviceSN"/>
        </div>
        &nbsp;&nbsp;
        &nbsp;&nbsp;
        <div class="form-group">
            <label>设备IP：</label>
            <input type="text" class="form-control" name="deviceIP"/>
        </div>
        &nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;
        <div class="form-group">
            <input type="submit" value="查询" class="form-control btn btn-info"/>
            &nbsp;&nbsp;
            <input type="reset" value="重置" class="form-control btn btn-danger"/>
        </div>
    </form>
    <br/>
    <hr/>
    <div class="table-responsive">
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th style="text-align: center;">设备SN</th>
                <th style="text-align: center;">设备名称</th>
                <th style="text-align: center;">设备IP</th>
                <th style="text-align: center;">设备分组</th>
                <th style="text-align: center;">出入口标识</th>
                <th style="text-align: center;">属性</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.page.beanList}" var="device">
                <tr class="text-center">
                    <td>${device.id}</td>
                    <td>${device.deviceName}</td>
                    <td>${device.deviceSN}</td>
                    <td>${device.deviceIP}</td>
                    <td>${device.deviceGroup}</td>
                    <td>${device.deviceProperty}</td>
                    <td>
                        <a href="#" onclick="return edit(${device.id})" style="text-decoration: none;">
                            <span class="fa fa-edit fa-fw"></span>
                        </a>
                        <a href="#" onclick="return trash(${device.id})" style="text-decoration: none;"
                           data-toggle="modal" data-target="#trashModal">
                            <span class="fa fa-trash-o fa-fw"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <form class="listForm" method="post" action="<%=basePath%>/device/findByPage">
        <div class="row">
            <div class="form-inline">
                <label style="font-size:14px;margin-top:22px;">
                    <strong>共<b>${requestScope.page.totalCount}</b>条记录，共<b>${requestScope.page.totalPage}</b>页</strong>
                    &nbsp;
                    &nbsp;
                    <strong>每页显示</strong>
                    <input name="pageCode" value="${requestScope.page.pageCode}" hidden="hidden"/>
                    <select class="form-control" name="pageSize">
                        <option value="2"
                                <c:if test="${requestScope.page.pageSize == 2}">selected</c:if> >2
                        </option>
                        <option value="3"
                                <c:if test="${requestScope.page.pageSize == 3}">selected</c:if> >3
                        </option>
                        <option value="4"
                                <c:if test="${requestScope.page.pageSize == 4}">selected</c:if> >4
                        </option>
                        <option value="5"
                                <c:if test="${requestScope.page.pageSize == 5}">selected</c:if> >5
                        </option>
                    </select>
                    <strong>条</strong>
                    &nbsp;
                    &nbsp;
                    <strong>到第</strong>&nbsp;<input type="text" size="3" id="page" name="pageCode"
                                                    class="form-control input-sm"
                                                    style="width:11%"/>&nbsp;<strong>页</strong>
                    &nbsp;
                    <button type="submit" class="btn btn-sm btn-info">GO!</button>
                </label>

                <ul class="pagination" style="float:right;">
                    <li>
                        <a href="<%=basePath%>/device/findByPage?pageCode=1"><strong>首页</strong></a>
                    </li>
                    <li>
                        <c:if test="${requestScope.page.pageCode > 2}">
                            <a href="<%=basePath%>/device/findByPage?pageCode=${requestScope.page.pageCode - 1}">&laquo;</a>
                        </c:if>
                    </li>

                    <!-- 写关于分页页码的逻辑 -->
                    <c:choose>
                        <c:when test="${requestScope.page.totalPage <= 5}">
                            <c:set var="begin" value="1"/>
                            <c:set var="end" value="${requestScope.page.totalPage}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="begin" value="${requestScope.page.pageCode - 1}"/>
                            <c:set var="end" value="${requestScope.page.pageCode + 3}"/>

                            <!-- 头溢出 -->
                            <c:if test="${begin < 1}">
                                <c:set var="begin" value="1"/>
                                <c:set var="end" value="5"/>
                            </c:if>

                            <!-- 尾溢出 -->
                            <c:if test="${end > requestScope.page.totalPage}">
                                <c:set var="begin" value="${requestScope.page.totalPage -4}"/>
                                <c:set var="end" value="${requestScope.page.totalPage}"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>

                    <!-- 显示页码 -->
                    <c:forEach var="i" begin="${begin}" end="${end}">
                        <!-- 判断是否是当前页 -->
                        <c:if test="${i == requestScope.page.pageCode}">
                            <li class="active"><a href="javascript:void(0);">${i}</a></li>
                        </c:if>
                        <c:if test="${i != requestScope.page.pageCode}">
                            <li>
                                <a href="<%=basePath%>/device/findByPage?pageCode=${i}&pageSize=${requestScope.page.pageSize}">${i}</a>
                            </li>
                        </c:if>
                    </c:forEach>

                    <li>
                        <c:if test="${requestScope.page.pageCode < requestScope.page.totalPage}">
                            <a href="<%=basePath%>/device/findByPage?pageCode=${requestScope.page.pageCode + 1}">&raquo;</a>
                        </c:if>
                    </li>
                    <li>
                        <a href="<%=basePath%>/device/findByPage?pageCode=${requestScope.page.totalPage}"><strong>末页</strong></a>
                    </li>
                </ul>
            </div>
        </div>
    </form>

    <!-- 删除的模态框 -->
    <div class="modal fade" id="trashModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- 模糊框头部 -->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;
                    </button>
                    <h4 class="modal-title">警告！</h4>
                </div>
                <!-- 模糊框主体 -->
                <div class="modal-body">
                    <strong>你确定要删除吗？</strong>
                </div>
                <!-- 模糊框底部 -->
                <div class="modal-footer">
                    <button type="button" class="delSure btn btn-info" data-dismiss="modal">确定</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 编辑的模态框 -->
    <form class="form-horizontal" role="form" method="post" action="<%=basePath%>/device/update"
          id="form_edit">
        <div class="modal fade" id="editModal" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">修改设备信息</h4>
                    </div>
                    <div class="modal-body" style="margin-left: 80px;">
                        <input name="id" id="id" hidden="hidden"/>
                        <div class="form-group form-inline">
                            <label>设备SN：</label>
                            <input type="text" name="deviceSN" class="form-control" id="deviceSN"/>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group form-inline">
                            <label>设备名称：</label>
                            <input type="text" name="deviceName" class="form-control" id="deviceName"/>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group form-inline">
                            <label>设备IP：</label>
                            <input type="text" name="deviceIP" class="form-control" id="deviceIP"/>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group form-inline">
                            <label>设备分组：</label>
                            <input type="text" name="deviceGroup" class="form-control" id="deviceGroup"/>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group form-inline">
                            <label>出入口标识：</label>
                            <input type="text" name="deviceDirection" class="form-control" id="deviceDirection"/>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group form-inline">
                            <label>属性：</label>
                            <input type="text" name="deviceProperty" class="form-control" id="deviceProperty"/>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="editSure btn btn-info" data-dismiss="modal">修改</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<script src="<%=basePath%>/lib/jquery-3.3.1.min.js"></script>
<script src="<%=basePath%>/lib/bootstrap.min.js"></script>
<script type="text/javascript">
    // 删除信息的方法
    function trash(id) {
        if (!id) {
            alert("error");
        } else {
            $(".delSure").click(function () {
                $.ajax({
                    url: '<%=basePath%>/device/delete.do?id=' + id,
                    type: 'POST',
                    success: function (data) {
                        $("body").html(data);
                    }
                });
            });
        }
    }


    // 编辑信息的方法
    function edit(id) {
        if (!id) {
            alert("error");
        } else {
            // 先去查询数据
            $.ajax({
                url: '<%=basePath%>/device/findById.do',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify({
                    id: id
                }),
                /*
                id,
                deviceName,
                deviceSN,
                deviceIP,
                deviceGroup,
                deviceDirection,
                deviceProperty
                 */
                success: function (data) {
                    $("#id").val(data.id);
                    $("#deviceName").val(data.deviceName);
                    $("#deviceSN").val(data.deviceSN);
                    $("#deviceIP").val(data.deviceIP);
                    $("#deviceGroup").val(data.deviceGroup);
                    $("#deviceDirection").val(data.deviceDirection);
                    $("#deviceProperty").val(data.deviceProperty);
                    $("#editModal").modal('show');
                },
                error: function () {
                    alert("错误");
                }
            });
        }
    }

    //提交表单的方法
    $(".editSure").click(function () {
        $("#form_edit").submit();
    });

</script>
</html>
