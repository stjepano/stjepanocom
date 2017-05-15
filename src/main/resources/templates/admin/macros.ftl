<#macro formgroup name classes=''>
    <div class="form-group ${classes} <#if validation??>${validation.hasError('${name}')?then('has-error', '')}</#if>">
        <#nested>
        <#if validation??>
            <#if validation.hasError('${name}')>
                <p class="text-red"><i class="fa fa-exclamation-circle"></i> ${validation.getError('${name}')}</p>
            </#if>
        </#if>
    </div>
</#macro>