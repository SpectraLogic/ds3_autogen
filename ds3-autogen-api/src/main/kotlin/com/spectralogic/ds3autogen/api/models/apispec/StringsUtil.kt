package com.spectralogic.ds3autogen.api.models.apispec

import java.util.Locale

fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.uppercase(Locale.getDefault())
        else it.toString()
    }
}

fun String.decapitalize(): String {
    return this.replaceFirstChar {
        if (it.isUpperCase())
            it.lowercase(Locale.getDefault())
        else it.toString()
    }
}