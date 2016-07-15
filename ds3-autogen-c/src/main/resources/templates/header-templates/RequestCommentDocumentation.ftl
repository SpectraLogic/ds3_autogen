<#-- ********************************************************** -->
<#-- Generate documentation to precede each init request        -->
<#--   Input: Request object                                    -->
<#-- ********************************************************** -->
<#if (requestEntry.getOptionalQueryParams()??) && (requestEntry.getOptionalQueryParams()?size > 0)>

/** Optional Request Modifiers for ${requestEntry.getInitName()}
<#list requestEntry.getOptionalQueryParams() as queryParam>
 * ${parameterHelper.generateSetQueryParamSignature(queryParam)}
</#list>
 */
</#if>
