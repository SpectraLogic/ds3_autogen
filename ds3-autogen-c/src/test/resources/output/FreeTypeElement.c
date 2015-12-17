void ds3_free_get_system_information(ds3_get_system_information_response* system_info) {
    if (system_info == NULL) {
        return;
    }

    ds3_str_free(system_info->api_version);
    ds3_str_free(system_info->serial_number);
    ds3_free_build_information(system_info->build_information);

    g_free(system_info);
}
