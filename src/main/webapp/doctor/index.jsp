<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
    pageContext.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=this.getServletContext().getContextPath() %>/doctor/">
    <title>门诊医生</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap-responsive.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/style.css"/>
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <%--    <script type="text/javascript" src="../Js/jquery.sorted.js"></script>--%>
    <script type="text/javascript" src="../Js/bootstrap.js"></script>
    <script type="text/javascript" src="../Js/ckform.js"></script>
    <script type="text/javascript" src="../Js/common.js"></script>
    <script type="text/javascript" src="../Js/jquery-3.4.1.js"></script>

    <style type="text/css">
        body {
            padding-bottom: 40px;
        }

        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }


    </style>
    <script type="text/javascript">
        $(function () {
            $("#newNav").click(function () {
                window.location.href = "${pageContext.request.contextPath}/doctor/add.jsp"
            });
            //清空
            $("#ret").click(function () {
                $("#name").val("");
                $("#department").val("0");
            });

            //全选
            $("#checkAll").click(function () {
                $("tbody input").prop("checked",$(this).prop("checked"));
            });

            //删除
            $("#delAll").click(function () {
                var idArray = [];
                $("tbody input:checked").each(function (index,item) {
                    let id = $(item).val();
                    idArray.push(id);
                });
                if(idArray.length === 0){
                    alert("请选择要删除的数据");
                }else {
                    //发送ajax请求
                    $.ajax({
                        type:"POST",
                        url:"${pageContext.request.contextPath}/doctor/batchDelete",
                        data:{"idArray":idArray,"_method":"delete"},
                        success:function (msg) {
                            if (msg.statusCode===200){
                                window.location.href = "${pageContext.request.contextPath}/doctor/search";
                            }
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>

<form action="${pageContext.request.contextPath}/doctor/search" method="get" class="definewidth m20">
    <table class="table table-bordered table-hover definewidth m10">
        <tr>
            <td width="10%" class="tableleft">医生姓名：</td>
            <td><input type="text" id="name" name="name" value="${name}"/></td>
            <td width="10%" class="tableleft">科室：</td>
            <td>
                <select name="department" id="department">
                    <option value="0">==请选择==</option>
                    <option value="1" <c:if test="${department ==1}">selected='selected'</c:if>>急诊科</option>
                    <option value="2" <c:if test="${department ==2}">selected='selected'</c:if>>儿科</option>
                    <option value="3" <c:if test="${department ==3}">selected='selected'</c:if>>妇科</option>
                    <option value="4" <c:if test="${department ==4}">selected='selected'</c:if>>皮肤科</option>
                    <option value="5" <c:if test="${department ==5}">selected='selected'</c:if>>内分泌科</option>
                    <option value="6" <c:if test="${department ==6}">selected='selected'</c:if>>牙科</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                <center>
                    <input id="find" name="find" type="submit" class="btn btn-primary" value="查询"/>
                    <input id="ret" name="ret" type="button" class="btn btn-primary" value="清空"/>
                </center>
            </td>
        </tr>
    </table>
</form>

<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th>
            <label><input type="checkbox" id="checkAll"></label>
        </th>
        <th>医生编号</th>
        <th>医生姓名</th>
        <th>联系方式</th>
        <th>所属科室</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${doctorPage.list}" var="doctor">
        <tr>
            <td>
                <label><input type="checkbox" value="${doctor.did}"></label>
            </td>
            <td>${doctor.did}</td>
            <td>${doctor.name}</td>
            <td>${doctor.phone}</td>
            <td>
                <c:if test="${doctor.department ==1}">急诊科</c:if>
                <c:if test="${doctor.department ==2}">儿科</c:if>
                <c:if test="${doctor.department ==3}">妇科</c:if>
                <c:if test="${doctor.department ==4}">皮肤科</c:if>
                <c:if test="${doctor.department ==5}">内分泌科</c:if>
                <c:if test="${doctor.department ==6}">牙科</c:if>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/doctor/detail/${doctor.did}">详情</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                    href="${pageContext.request.contextPath}/doctor/edit/${doctor.did}">编辑</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <th colspan="5">
            <div class="inline pull-right page">
                <a href='${pageContext.request.contextPath}/doctor/search?pageNum=1${queryString}'>首页</a>

                <a href='${pageContext.request.contextPath}/doctor/search?pageNum=${doctorPage.prePage}${queryString}'>上一页</a>

                <a href='${pageContext.request.contextPath}/doctor/search?pageNum=${doctorPage.nextPage}${queryString}'>下一页</a>

                <a href='${pageContext.request.contextPath}/doctor/search?pageNum=${doctorPage.pages}${queryString}'>尾页</a>

                &nbsp;&nbsp;&nbsp;共<span class='current'> ${doctorPage.total}</span>条记录
                <span class='current'> ${doctorPage.pageNum} / ${doctorPage.pages} </span>页

            </div>
            <div>
                <button type="button" class="btn btn-success" id="newNav">添加新医生</button>
                <button type="button" class="btn btn-success" id="delAll">批量删除</button>
            </div>

        </th>
    </tr>
</table>
</body>

</html>
