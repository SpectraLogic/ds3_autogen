static ds3_paging* _parse_paging_headers(ds3_string_multimap* response_headers) {
    ds3_paging* response_paging = NULL;

    ds3_str* page_truncated_key = ds3_str_init("Page-Truncated");
    ds3_str* total_result_count_key = ds3_str_init("Total-Result-Count");

    ds3_string_multimap_entry* page_truncated_entry = ds3_string_multimap_lookup(response_headers, page_truncated_key);
    ds3_string_multimap_entry* total_result_count_entry = ds3_string_multimap_lookup(response_headers, total_result_count_key);

    if ((page_truncated_entry != NULL) || (total_result_count_entry != NULL)) {
        ds3_str* page_truncated_ds3_str = ds3_string_multimap_entry_get_value_by_index(page_truncated_entry, 0);
        ds3_str* total_result_count_ds3_str = ds3_string_multimap_entry_get_value_by_index(total_result_count_entry, 0);

        response_paging = g_new0(ds3_paging, 1);
        if (page_truncated_ds3_str != NULL) {
            response_paging->page_truncated = g_ascii_strtoll(page_truncated_ds3_str->value, NULL, 10);
            ds3_str_free(page_truncated_ds3_str);
        } else {
            response_paging->page_truncated = 0;
        }
        if (total_result_count_ds3_str != NULL) {
            response_paging->total_result_count = g_ascii_strtoll(total_result_count_ds3_str->value, NULL, 10);
            ds3_str_free(total_result_count_ds3_str);
        } else {
            response_paging->total_result_count = 0;
        }
    }

    ds3_str_free(page_truncated_key);
    ds3_str_free(total_result_count_key);
    ds3_string_multimap_entry_free(page_truncated_entry);
    ds3_string_multimap_entry_free(total_result_count_entry);

    return response_paging;
}
