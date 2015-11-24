static ds3_job_status _match_job_status(const ds3_log* log, const xmlChar* text) {
    if (xmlStrcmp(text, (const xmlChar*) "IN_PROGRESS") == 0) {
        return IN_PROGRESS;
    } else if (xmlStrcmp(text, (const xmlChar*) "COMPLETED") == 0) {
        return COMPLETED;
    } else if (xmlStrcmp(text, (const xmlChar*) "CANCELED") == 0) {
        return CANCELED;
    } else {
        ds3_log_message(log, DS3_ERROR, "ERROR: Unknown job status value of '%s'.  Returning IN_PROGRESS for safety.\n", text);
        return IN_PROGRESS;
    }
}