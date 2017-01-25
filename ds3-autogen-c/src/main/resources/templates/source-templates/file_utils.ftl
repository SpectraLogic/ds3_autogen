
size_t ds3_write_to_file(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return fwrite(buffer, size, nmemb, (FILE*) user_data);
}

size_t ds3_read_from_file(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return fread(buffer, size, nmemb, (FILE*) user_data);
}

size_t ds3_write_to_fd(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return write(*(int*)user_data, buffer, size * nmemb);
}

size_t ds3_read_from_fd(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return read(*(int*)user_data, buffer, size * nmemb);
}

#ifdef _MSC_VER
static void get_file_size_windows(const char* file_path, uint64_t* file_size) {
    BY_HANDLE_FILE_INFORMATION info;
    HANDLE file_handle;
    char * file_path_windows;
    gunichar2 * file_path_windows_wide;
    BOOL res;

    file_path_windows = g_strdup(file_path);

    /* Convert the path slashes to windows */
    for (char * ch = file_path_windows; *ch != '\0'; ++ch) {
        switch(*ch) {
        case '/': *ch = '\\'; break;
        }
    }

    *file_size = 0;

    /* Convert the path to UTF16 to use in windows native function */
    file_path_windows_wide = g_utf8_to_utf16(file_path_windows, -1, NULL, NULL, NULL);
    if (file_path_windows_wide == NULL) {
        fprintf(stderr, "error converting file name to wide char '%s'\n", file_path_windows);
        g_free(file_path_windows);
        return;
    }


    /* Open the file for attributes read only */
    file_handle = CreateFileW(
        (wchar_t*)file_path_windows_wide,
        FILE_READ_ATTRIBUTES,
        FILE_SHARE_READ|FILE_SHARE_WRITE|FILE_SHARE_DELETE,
        NULL,
        OPEN_EXISTING,
        FILE_ATTRIBUTE_NORMAL,
        NULL);
    g_free(file_path_windows_wide);

    if (file_handle == INVALID_HANDLE_VALUE) {
        g_free(file_path_windows);
        fprintf(stderr, "error opening file '%s'\n", file_path_windows);
        return;
    }

    res = GetFileInformationByHandle(file_handle, &info);
    CloseHandle(file_handle);

    if (!res) {
        fprintf(stderr, "error getting file attributes\n");
        g_free(file_path_windows);
        return;
    }

    /* If entry is file get the file size */
    if (!(info.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY)) {
        ULARGE_INTEGER size;
        size.LowPart = info.nFileSizeLow;
        size.HighPart = info.nFileSizeHigh;

        *file_size =  size.QuadPart;
    } else {
        *file_size = 0;
    }

    g_free(file_path_windows);

    return;
}
#endif

static void get_file_size_posix(const char* file_path, uint64_t* file_size) {
    struct stat file_info;
    int result;

    result = stat(file_path, &file_info);
    if (result != 0) {
        fprintf(stderr, "Failed to get file info for '%s' res=%d errno=%d: %s\n", file_path, result, errno, strerror(errno));
        *file_size = 0;
        return;
    }

    if (S_ISDIR(file_info.st_mode)) {
        *file_size = 0;
    } else {
        *file_size = file_info.st_size;
    }
}

static ds3_bulk_object_response* _ds3_bulk_object_from_file(const char* file_name, const char* base_path) {
    struct stat file_info;
    int result;
    ds3_bulk_object_response* obj = g_new0(ds3_bulk_object_response, 1);
    char* file_to_stat;
    memset(&file_info, 0, sizeof(struct stat));

    if (base_path != NULL) {
        file_to_stat = g_strconcat(base_path, file_name, NULL);
    } else {
        file_to_stat = g_strdup(file_name);
    }

    obj->name = ds3_str_init(file_name);

#ifdef _MSC_VER
    get_file_size_windows(file_to_stat, &obj->length);
#else
    get_file_size_posix(file_to_stat, &obj->length);
#endif

    g_free(file_to_stat);

    return obj;
}

ds3_bulk_object_list_response* ds3_convert_file_list(const char** file_list, uint64_t num_files) {
    return ds3_convert_file_list_with_basepath(file_list, num_files, NULL);
}

ds3_bulk_object_list_response* ds3_convert_file_list_with_basepath(const char** file_list, uint64_t num_files, const char* base_path) {
    size_t file_index;
    ds3_bulk_object_list_response* obj_list = ds3_init_bulk_object_list();

    GPtrArray* ds3_bulk_object_response_array = g_ptr_array_new();
    for (file_index = 0; file_index < num_files; file_index++) {
        g_ptr_array_add(ds3_bulk_object_response_array, _ds3_bulk_object_from_file(file_list[file_index], base_path));
    }

    obj_list->objects = (ds3_bulk_object_response**)ds3_bulk_object_response_array->pdata;
    obj_list->num_objects = ds3_bulk_object_response_array->len;
    g_ptr_array_free(ds3_bulk_object_response_array, FALSE);

    return obj_list;
}

ds3_bulk_object_list_response* ds3_convert_object_list(const ds3_contents_response** objects, uint64_t num_objects) {
    size_t object_index;
    ds3_bulk_object_list_response* obj_list = ds3_init_bulk_object_list();

    GPtrArray* ds3_bulk_object_response_array = g_ptr_array_new();

    for (object_index = 0; object_index < num_objects; object_index++) {
        ds3_bulk_object_response* response = g_new0(ds3_bulk_object_response, 1);
        response->name = ds3_str_dup(objects[object_index]->key);
        g_ptr_array_add(ds3_bulk_object_response_array, response);
    }

    obj_list->objects = (ds3_bulk_object_response**)ds3_bulk_object_response_array->pdata;
    obj_list->num_objects = ds3_bulk_object_response_array->len;
    g_ptr_array_free(ds3_bulk_object_response_array, FALSE);

    return obj_list;
}

ds3_bulk_object_list_response* ds3_init_bulk_object_list() {
    return g_new0(ds3_bulk_object_list_response, 1);
}
