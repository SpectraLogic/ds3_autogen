
struct _ds3_metadata {
    GHashTable* metadata;
};

static void _ds3_metadata_entry_free(gpointer pointer) {
    ds3_metadata_entry* entry;
    if (pointer == NULL) {
        return; // do nothing
    }

    entry = (ds3_metadata_entry*) pointer;

    ds3_metadata_entry_free(entry);
}

/*
 * This copies all the header values in the ds3_string_multimap_entry struct so that they may be safely returned to the user
 * without having to worry about if the data is freed internally.
 */
static const char* METADATA_PREFIX = "x-amz-meta-";
static ds3_metadata_entry* ds3_metadata_entry_init(ds3_string_multimap_entry* header_entry) {
    guint i;
    ds3_str* header_value;
    GPtrArray* values = g_ptr_array_new();
    ds3_str* key_name;
    ds3_str* full_key;
    ds3_metadata_entry* response = g_new0(ds3_metadata_entry, 1);
    int metadata_prefix_length = strlen(METADATA_PREFIX);

    unsigned int num_values = ds3_string_multimap_entry_get_num_values(header_entry);
    for (i = 0; i < num_values; i++) {
        header_value = ds3_string_multimap_entry_get_value_by_index(header_entry, i);
        g_ptr_array_add(values, header_value);
    }

    full_key = ds3_string_multimap_entry_get_key(header_entry);
    key_name = ds3_str_init(full_key->value + metadata_prefix_length);
    ds3_str_free(full_key);

    response->num_values = num_values;
    response->name = key_name;
    response->values = (ds3_str**) g_ptr_array_free(values, FALSE);
    fprintf(stderr, "creating metadata entry of: %s\n", key_name->value);
    return response;
}

/* The headers hash table contains all the response headers which have the following types:
 * Key - char*
 * Value - ds3_response_header
 *
 * All values should be copied from the struct to avoid memory issues
 */
static ds3_metadata* _init_metadata(ds3_string_multimap* response_headers) {
    struct _ds3_metadata* metadata = g_new0(struct _ds3_metadata, 1);
    GHashTableIter iter;
    gpointer _key, _value;
    ds3_str* key;
    ds3_metadata_entry* entry;
    metadata->metadata = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, _ds3_metadata_entry_free);

    if (response_headers == NULL) {
        fprintf(stderr, "response headers was null\n");
    }

    g_hash_table_iter_init(&iter, ds3_string_multimap_get_hashtable(response_headers));
    while(g_hash_table_iter_next(&iter, &_key, &_value)) {
        key = (ds3_str*) _key;
        if (g_str_has_prefix(key->value, "x-amz-meta-")) {
            ds3_string_multimap_entry* mm_entry = ds3_string_multimap_lookup(response_headers, key);
            entry = ds3_metadata_entry_init(mm_entry);
            g_hash_table_insert(metadata->metadata, g_strdup(entry->name->value), entry);
            ds3_string_multimap_entry_free(mm_entry);
        }
    }

    return (ds3_metadata*) metadata;
}

ds3_metadata_entry* ds3_metadata_get_entry(const ds3_metadata* _metadata, const char* name) {
    ds3_metadata_entry* copy;
    ds3_metadata_entry* orig;
    ds3_str** metadata_copy;
    uint64_t i;
    struct _ds3_metadata* metadata = (struct _ds3_metadata*) _metadata;

    if (_metadata == NULL) {
        return NULL;
    }

    orig = (ds3_metadata_entry*) g_hash_table_lookup(metadata->metadata, name);
    if (orig == NULL) {
        return NULL;
    }
    copy = g_new0(ds3_metadata_entry, 1);
    metadata_copy = g_new0(ds3_str*, (gsize)orig->num_values);

    for (i = 0; i < orig->num_values; i++) {
        metadata_copy[i] = ds3_str_dup(orig->values[i]);
    }

    copy->num_values = orig->num_values;
    copy->name = ds3_str_dup(orig->name);
    copy->values = metadata_copy;

    return copy;
}

unsigned int ds3_metadata_size(const ds3_metadata* _metadata) {
    struct _ds3_metadata* metadata = (struct _ds3_metadata*) _metadata;
    if (metadata == NULL) {
        return 0;
    }
    return g_hash_table_size(metadata->metadata);
}

ds3_metadata_keys_result* ds3_metadata_keys(const ds3_metadata* _metadata) {
    GPtrArray* return_keys;
    ds3_metadata_keys_result* result;
    struct _ds3_metadata* metadata;
    GList* keys;
    GList* tmp_key;

    if (_metadata == NULL) {
        return NULL;
    }

    return_keys = g_ptr_array_new();
    result = g_new0(ds3_metadata_keys_result, 1);
    metadata = (struct _ds3_metadata*) _metadata;
    keys = g_hash_table_get_keys(metadata->metadata);
    tmp_key = keys;

    while(tmp_key != NULL) {
        g_ptr_array_add(return_keys, ds3_str_init((char*)tmp_key->data));
        tmp_key = tmp_key->next;
    }

    g_list_free(keys);
    result->num_keys = return_keys->len;
    result->keys = (ds3_str**) g_ptr_array_free(return_keys, FALSE);
    return result;
}

static void _cleanup_hash_value(gpointer value) {
    g_free(value);
}

static GHashTable* _create_hash_table(void) {
    GHashTable* hash =  g_hash_table_new_full(g_str_hash, g_str_equal, _cleanup_hash_value, _cleanup_hash_value);
    return hash;
}

void ds3_metadata_free(ds3_metadata* _metadata) {
    struct _ds3_metadata* metadata;
    if (_metadata == NULL) return;

    metadata = (struct _ds3_metadata*) _metadata;

    if (metadata->metadata == NULL) return;

    g_hash_table_destroy(metadata->metadata);

    g_free(metadata);
}

void ds3_metadata_entry_free(ds3_metadata_entry* entry) {
    if (entry == NULL) {
        return;
    }

    int value_index;
    ds3_str* value;
    if (entry->name != NULL) {
        ds3_str_free(entry->name);
    }

    if (entry->values != NULL) {
        for (value_index = 0; value_index < entry->num_values; value_index++) {
            value = entry->values[value_index];
            ds3_str_free(value);
        }
        g_free(entry->values);
    }
    g_free(entry);
}

void ds3_metadata_keys_free(ds3_metadata_keys_result* metadata_keys) {
    uint64_t key_index;
    if (metadata_keys == NULL) {
        return;
    }

    if (metadata_keys->keys != NULL) {
        for (key_index = 0; key_index < metadata_keys->num_keys; key_index++) {
            ds3_str_free(metadata_keys->keys[key_index]);
        }
        g_free(metadata_keys->keys);
    }
    g_free(metadata_keys);
}
