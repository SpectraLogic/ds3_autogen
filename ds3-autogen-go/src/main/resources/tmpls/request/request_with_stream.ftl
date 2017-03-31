<#include "request_body.ftl">

func (${name?uncap_first} *${name}) GetContentStream() networking.ReaderWithSizeDecorator {
    return ${name?uncap_first}.content
}
