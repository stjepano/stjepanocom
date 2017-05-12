<#import "./master.ftl" as master>
<@master.template>
    <div class="row">
        <div class="col-xs-12">
    <#if users.getContent()?size == 0>
            <div class="alert alert-danger">
                <h4><i class="fa fa-ban"></i> Warning</h4>
                There are no users in the database. <a href="/admin/users/create">Create an user!!</a>
            </div>
    <#else>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">Users</h3>
                    <div class="box-tools">
                        <div class="btn-group">
                            <a class="btn btn-primary" href="/admin/users/create"><i class="fa fa-plus-circle"></i></a>
                            <a class="btn btn-danger"><i class="fa fa-trash"></i></a>
                        </div>
                    </div>
                </div>
                <div class="box-body table-responsive no-padding">
                    <table class="table table-hover">
                        <tr>
                            <th><input type="checkbox" /></th>
                            <th>Email</th>
                            <th>Display name</th>
                            <th>Date created</th>
                            <th>Date updated</th>
                        </tr>
                        <#list users.getContent() as user>
                            <tr>
                                <td><input type="checkbox" /></td>
                                <td><a href="/admin/users/${user.id?c}">${user.email}</a></td>
                                <td>${user.displayName}</td>
                                <td>${user.created?string["dd/MM/yyyy HH:mm"]}</td>
                                <td>${user.updated?string["dd/MM/yyyy HH:mm"]}</td>
                            </tr>
                        </#list>
                    </table>
                </div>
                <div class="box-footer clearfix">
                    <#if users.getTotalPages() gt 1>
                    <ul class="pagination pagination-sm no-margin pull-right">
                        <li><a href="/admin/users?page=1">&laquo;</a></li>
                        <#list 1..users.getTotalPages() as pn>
                            <#if (pn == users.getNumber()+1)>
                                <li class="active"><a href="/admin/users?page=${pn?c}">${pn?c}</a></li>
                            <#else>
                                <li><a href="/admin/users?page=${pn?c}">${pn?c}</a></li>
                            </#if>

                        </#list>
                        <li><a href="/admin/users?page=${users.getTotalPages()?c}">&raquo;</a></li>
                    </ul>
                    </#if>
                </div>
            </div>
    </#if>
        </div>
    </div>
</@master.template>
