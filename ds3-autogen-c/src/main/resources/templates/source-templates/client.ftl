
void ds3_client_register_logging(ds3_client* client, ds3_log_lvl log_lvl, void (* log_callback)(const char* log_message, void* user_data), void* user_data) {
    if (client == NULL) {
        fprintf(stderr, "Cannot configure a null ds3_client for logging.\n");
        return;
    }
    if (client->log != NULL) {
        g_free(client->log);
    }
    ds3_log* log = g_new0(ds3_log, 1);
    log->log_callback = log_callback;
    log->user_data = user_data;
    log->log_lvl = log_lvl;

    client->log = log;
}

ds3_creds* ds3_create_creds(const char* access_id, const char* secret_key) {
    ds3_creds* creds;
    if (access_id == NULL || secret_key == NULL) {
        fprintf(stderr, "Arguments cannot be NULL\n");
        return NULL;
    }

    creds = g_new0(ds3_creds, 1);
    creds->access_id = ds3_str_init(access_id);
    creds->secret_key = ds3_str_init(secret_key);

    return creds;
}

void ds3_client_register_net(ds3_client* client, ds3_error* (* net_callback)(const ds3_client* client,
                                                                             const ds3_request* _request,
                                                                             void* read_user_struct,
                                                                             size_t (*read_handler_func)(void*, size_t, size_t, void*),
                                                                             void* write_user_struct,
                                                                             size_t (*write_handler_func)(void*, size_t, size_t, void*),
                                                                             ds3_string_multimap** return_headers)) {
    if (client == NULL) {
        fprintf(stderr, "Cannot configure a null ds3_client for net_callback.\n");
        return;
    }

    client->net_callback = net_callback;
}

ds3_client* ds3_create_client(const char* endpoint, ds3_creds* creds) {
    ds3_client* client;
    if (endpoint == NULL) {
        fprintf(stderr, "Null endpoint\n");
        return NULL;
    }

    client = g_new0(ds3_client, 1);
    client->endpoint = ds3_str_init(endpoint);
    client->creds = creds;
    client->num_redirects = 5L; //default to 5 redirects before failing

    ds3_client_register_net( client, net_process_request );

    return client;
}

ds3_error* ds3_create_client_from_env(ds3_client** client) {
    ds3_creds* creds;
    ds3_client* _client;
    char* endpoint = getenv("DS3_ENDPOINT");
    char* access_key = getenv("DS3_ACCESS_KEY");
    char* secret_key = getenv("DS3_SECRET_KEY");
    char* http_proxy = getenv("http_proxy");

    if (endpoint == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "Missing enviornment variable 'DS3_ENDPOINT'");
    }

    if (access_key == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "Missing enviornment variable 'DS3_ACCESS_KEY'");
    }

    if (secret_key == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "Missing enviornment variable 'DS3_SECRET_KEY'");
    }

    creds = ds3_create_creds(access_key, secret_key);
    _client = ds3_create_client(endpoint, creds);

    if (http_proxy != NULL) {
        ds3_client_proxy(_client, http_proxy);
    }

    *client = _client;

    return NULL;
}

void ds3_client_proxy(ds3_client* client, const char* proxy) {
    client->proxy = ds3_str_init(proxy);
}

void ds3_creds_free(ds3_creds* creds) {
    if (creds == NULL) {
        return;
    }

    ds3_str_free(creds->access_id);
    ds3_str_free(creds->secret_key);
    g_free(creds);
}

void ds3_client_free(ds3_client* client) {
    if (client == NULL) {
      return;
    }

    ds3_str_free(client->endpoint);
    ds3_str_free(client->proxy);
    if (client->log != NULL) {
        g_free(client->log);
    }
    g_free(client);
}

