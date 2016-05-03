            <#list constructor.queryParams as arg>
            this.QueryParams.Add("${netHelper.camelToUnderscore(arg.getName())}", ${netHelper.argToString(arg)});
            </#list>