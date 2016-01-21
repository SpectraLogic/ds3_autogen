GPtrArray* ${type}s_array = _parse_${type}s(log, doc, child_node);
                response->${type}s = (ds3_bucket**)buckets_array->pdata;
                response->num_${type}s = ${type}s_array->len;
                g_ptr_array_free(${type}s_array, FALSE);
