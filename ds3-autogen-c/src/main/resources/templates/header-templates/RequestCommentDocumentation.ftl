<#-- ********************************************************** -->
<#-- Generate documentation to precede each init request        -->
<#--   Input: Request object                                    -->
<#-- ********************************************************** -->
<#if helper.hasContent(requestEntry.getDocumentation()) || helper.hasContent(requestEntry.getOptionalQueryParams())>
/**
<#if helper.hasContent(requestEntry.getDocumentation())>
${requestEntry.getDocumentation()}

</#if>
<#if helper.hasContent(requestEntry.getOptionalQueryParams())>
Optional Request Modifiers for ${requestEntry.getInitName()}

<#list requestEntry.getOptionalQueryParams() as queryParam>
<#if helper.hasContent(queryParam.getDocumentation())>
${queryParam.getDocumentation()}
</#if>
  ${parameterHelper.generateSetQueryParamSignature(queryParam)}
</#list>
</#if>
 */
</#if>
