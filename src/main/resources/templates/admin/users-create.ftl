<#import "./master.ftl" as master>
<@master.template>
<div class="row">
    <div class="col-xs-12">
        <div class="box box-warning">
            <div class="box-header with-border">
              <h3 class="box-title">Create new user</h3>
            </div>
            <!-- /.box-header -->
            <form role="form" action="/admin/users/create" method="POST">
                <div class="box-body">
                    <!-- text input -->
                    <div class="form-group">
                    <label>Email</label>
                    <input class="form-control" type="email" name="email" />
                    <span class="help-block">This is users login name</span>
                    </div>
                    <div class="form-group">
                    <label>Display name</label>
                    <input class="form-control" type="text" name="displayName" />
                    <span class="help-block">This is users display name, if provided it will be displayed on web site instead of email.</span>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-6">
                            <label>Password</label>
                            <input class="form-control" type="password" name="password" />
                        </div>
                        <div class="form-group col-md-6">
                            <label>Confirm password</label>
                            <input class="form-control" type="password" name="passwordConfirm" />
                        </div>
                    </div>

                    <!-- textarea -->
                    <div class="form-group">
                        <label>Description</label>
                        <textarea class="form-control" rows="5" placeholder="Enter description ..." name="description"></textarea>
                        <span class="help-block">Users description, something about user.</span>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputFile">Users image</label>
                        <input id="exampleInputFile" type="file">
                        <p class="help-block">User image is displayed in admin UI and can be displayed in frontend depending on the theme</p>
                    </div>

                    <div class="form-group">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="blocked" /><em>Disable login</em>
                            </label>
                        </div>
                    </div>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <label class="checkbox-inline" style="margin-left: 10px"><input type="checkbox" name="stayOnThisPage" />Stay on this page</label>
                </div>
            </form>
          </div>
    </div>
</div>
</@master.template>
