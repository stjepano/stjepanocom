<#import "./master.ftl" as master>
<#import "./macros.ftl" as m>
<@master.template>
<div class="row">
    <div class="col-xs-12">
        <div class="box box-warning">
            <div class="box-header with-border">
              <h3 class="box-title">Create new user</h3>
            </div>
            <!-- /.box-header -->
            <form role="form" action="/admin/users/${dto.id}" method="POST">
                <div class="box-body">
                    <!-- text input -->
                    <@m.formgroup name="email">
                        <label>Email</label>
                        <input class="form-control" type="email" name="email" value='${(dto.email)!""}' />
                        <span class="help-block">This is users login name</span>
                    </@m.formgroup>
                    <!--
                    <div class="form-group <#if validation??>${validation.hasError('email')?then('has-error', '')}</#if>">
                        <label>Email</label>
                        <input class="form-control" type="email" name="email" value='${(dto.email)!""}' />
                        <span class="help-block">This is users login name</span>
                    </div>
                    -->
                    <@m.formgroup name="displayName">
                        <label>Display name</label>
                        <input class="form-control" type="text" name="displayName" value='${(dto.displayName)!""}' />
                        <span class="help-block">This is users display name, if provided it will be displayed on web site instead of email.</span>
                    </@m.formgroup>

                    <div class="row">
                        <@m.formgroup name="password" classes="col-md-6">
                            <label>Password</label>
                            <input class="form-control" type="password" name="password" />
                        </@m.formgroup>
                        <@m.formgroup name="passwordConfirm" classes="col-md-6">
                            <label>Confirm password</label>
                            <input class="form-control" type="password" name="passwordConfirm" />
                        </@m.formgroup>
                    </div>

                    <!-- textarea -->
                    <@m.formgroup name="description">
                        <label>Description</label>
                        <textarea class="form-control" rows="5" placeholder="Enter description ..." name="description">${(dto.description)!""}</textarea>
                        <span class="help-block">Users description, something about user.</span>
                    </@m.formgroup>
                    <@m.formgroup name="userImage">
                        <label for="userImage">Users image</label>
                        <input id="userImage" name="userImage" type="file" />
                        <p class="help-block">User image is displayed in admin UI and can be displayed in frontend depending on the theme</p>
                    </@m.formgroup>

                    <@m.formgroup name="blocked">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="blocked" ${dto.blocked?then('checked', '')} /><em>Disable login</em>
                            </label>
                        </div>
                    </@m.formgroup>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
          </div>
    </div>
</div>
</@master.template>