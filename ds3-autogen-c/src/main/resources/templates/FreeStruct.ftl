void ${getStructHelper().getFreeFunctionName(name)}(${getStructHelper().getResponseTypeName(name)}* response_data) {
    if (response_data == NULL) {
        return;
    }

${getStructHelper().generateFreeStructMembers(getVariables())}

    g_free(response_data);
}