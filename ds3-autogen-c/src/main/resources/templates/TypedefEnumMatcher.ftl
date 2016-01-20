static ds3_${getEnumHelper().getNameUnderscores(name)} _match_ds3_${getEnumHelper().getNameUnderscores(name)}(const ds3_log* log, const xmlChar* text) {
${getEnumHelper().generateMatcher(values)}
}