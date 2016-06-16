<#-- ********************************************************** -->
<#-- Generate documentation to precede each init request        -->
<#--   Input: Request object                                    -->
<#-- ********************************************************** -->
<#if requestEntry.getOptionalQueryParams()??>
<#list requestEntry.getOptionalQueryParams() as queryParam>
// ${queryParam.toString()}
</#list>
</#if>
