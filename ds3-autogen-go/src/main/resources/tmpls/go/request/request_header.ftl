<#include "../common/copyright.ftl" />

package models

import (
    "net/url"
    "net/http"
    "ds3/networking"
    <#list imports as import>
    "${import}"
    </#list>
)
