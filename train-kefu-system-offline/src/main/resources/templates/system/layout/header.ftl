<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Offline</title>
    <#--<link href="${ctx}/static/assets/images/favicon.ico" rel="shortcut icon" type="image/x-icon">-->

    <#--<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">-->
    <#--<link href="https://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/jquery-confirm/2.5.1/jquery-confirm.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/fancybox/2.1.5/jquery.fancybox.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/nprogress/0.2.0/nprogress.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/toastr.js/2.0.3/css/toastr.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/iCheck/1.0.2/skins/square/green.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/bootstrap-daterangepicker/2.1.24/daterangepicker.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">-->
    <#--<link href="https://cdn.bootcss.com/zTree.v3/3.5.29/css/metroStyle/metroStyle.min.css" rel="stylesheet">-->

    <link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/jquery/jquery-confirm.min.js" rel="stylesheet">
    <link href="${ctx}/static/jquery/css/jquery.fancybox.min.css" rel="stylesheet">
    <link href="${ctx}/static/moment/nprogress.min.css" rel="stylesheet">
    <link href="${ctx}/static/moment/toastr.min.css" rel="stylesheet">
    <link href="${ctx}/static/moment/green.css" rel="stylesheet">
    <link href="${ctx}/static/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/static/dataTable/daterangepicker.min.css" rel="stylesheet">
    <link href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="${ctx}/static/moment/metroStyle.min.css" rel="stylesheet">


    <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap-select.min.css">
    <link href="${ctx}/static/assets/css/zhyd.core.css" rel="stylesheet">

    <#--<style>-->
        <#--.dataTables_wrapper .row:last-child-->
        <#--{-->
            <#--overflow:hidden !important;-->
            <#--padding-bottom: 10px;-->
        <#--}-->
    <#--</style>-->

</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="/" class="site_title"><i class="fa fa-coffee"></i> <span>offline</span></a>
                </div>
                <div class="clearfix"></div>
                <div class="profile clearfix" style="text-align:center">
                    <#--<div class="profile_pic">-->
                        <#--<img src="${ctx}/static/assets/images/loading.gif" alt="..." class="img-circle profile_img">-->
                    <#--</div>-->
                    <div class="profile_info">
                        <span>welcome!</span>
                        <h2><#if empEntity?exists>${empEntity.getEmpName()}(${empEntity.getDeptName()})</#if></h2>
                    </div>
                </div>
                <br />
            <#include "sidebar.ftl"/>
            </div>
        </div>
        <#include "setting.ftl"/>
        <div class="right_col" role="main">