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
                <form action="/admin/users/delete" method="POST" id="delete-users-form">
                    <div class="box-header">
                        <h3 class="box-title">Users</h3>
                        <div class="box-tools">
                            <div class="btn-group">
                                <a class="btn btn-primary" href="/admin/users/create"><i class="fa fa-plus-circle"></i></a>
                                <button type="submit" class="btn btn-danger" id="delete-button" disabled="true"><i class="fa fa-trash"></i></button>
                            </div>
                        </div>
                    </div>
                    <div class="box-body table-responsive no-padding">
                        <table class="table table-hover">
                            <tr>
                                <th><input type="checkbox" id="select-all-checkbox" /></th>
                                <th>Email</th>
                                <th>Display name</th>
                                <th>Date created</th>
                                <th>Date updated</th>
                            </tr>
                            <#list users.getContent() as user>
                                <tr>
                                    <td><input type="checkbox" name="users[]" value="${user.id?c}" class="user-row-checkbox" /></td>
                                    <td><a href="/admin/users/${user.id?c}">${user.email}</a></td>
                                    <td>${user.displayName}</td>
                                    <td>${user.created?string["dd/MM/yyyy HH:mm"]}</td>
                                    <td>${user.updated?string["dd/MM/yyyy HH:mm"]}</td>
                                </tr>
                            </#list>
                        </table>
                    </div>
                </form>
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
            <script type="text/javascript">
                $(document).ready(function() {
                    var self = this;

                    function refreshDeleteButtonState() {
                        if ($('.user-row-checkbox:checked').length > 0) {
                            $('#delete-button').prop('disabled', false);
                        } else {
                            $('#delete-button').prop('disabled', true);
                        }
                    }

                    $('.user-row-checkbox').click(function() {
                        if (!this.checked) {
                            if ($('#select-all-checkbox:checked').length == 1) {
                                $('#select-all-checkbox').prop('checked', false);
                            }
                        }

                        refreshDeleteButtonState();     
                    });

                    $('#select-all-checkbox').click(function() {
                        $('.user-row-checkbox').not(this).prop('checked', this.checked);

                        refreshDeleteButtonState();
                    });
                    
                    $('#delete-users-form').submit(function(evt) {
                        if (!confirm('Are you sure you want to delete selected users?')) {
                            evt.preventDefault();
                        }
                    });

                });
            </script>
    </#if>
        </div>
    </div>
</@master.template>
