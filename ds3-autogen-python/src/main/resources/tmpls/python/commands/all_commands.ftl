<#include "../common/copyright.ftl" />

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
