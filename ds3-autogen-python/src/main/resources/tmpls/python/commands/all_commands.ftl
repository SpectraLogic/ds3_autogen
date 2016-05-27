<#include "../common/copyright.ftl" />

# Type Descriptors

<#include "types/type_descriptor.ftl" />

<#include "types/parser_function.ftl" />

# Request Handlers

class AbstractRequest(object):
    __metaclass__ = ABCMeta
    def __init__(self):
        self.path = '/'
        self.http_verb = HttpVerb.GET
        self.query_params = {}
        self.headers = {}
        self.body = None

<#include "requests/request.ftl" />
