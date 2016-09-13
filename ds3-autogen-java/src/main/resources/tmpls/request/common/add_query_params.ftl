        <#if operation??>
        this.getQueryParams().put("operation", "${operation.toString()?lower_case}");
        </#if>

        <#list constructor.getQueryParams() as param>
        ${param.toPutJavaCode()}
        </#list>