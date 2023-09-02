package com.vinibarros.mvvmcomposetemplate.util.extensions

import android.util.Patterns

val accents = mapOf(
    'ª' to 'A',
    'º' to 'O',
    '§' to 'S',
    '³' to '3',
    '²' to '2',
    '¹' to '1',
    'à' to 'a',
    'á' to 'a',
    'â' to 'a',
    'ã' to 'a',
    'ä' to 'a',
    'è' to 'e',
    'é' to 'e',
    'ê' to 'e',
    'ë' to 'e',
    'í' to 'i',
    'ì' to 'i',
    'î' to 'i',
    'ï' to 'i',
    'ù' to 'u',
    'ú' to 'u',
    'û' to 'u',
    'ü' to 'u',
    'ò' to 'o',
    'ó' to 'o',
    'ô' to 'o',
    'õ' to 'o',
    'ö' to 'o',
    'ñ' to 'n',
    'ç' to 'c'
)

fun String?.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this.toString()).matches()
}

fun String.removeAccents(): String {
    val sb = java.lang.StringBuilder(this)
    for (i in this.indices) {
        val c = accents[sb[i]]
        if (c != null) {
            sb.setCharAt(i, c)
        }
    }
    return sb.toString()
}

fun String?.containsIgnoreCase(query: String): Boolean {
    return this?.lowercase()?.removeAccents()?.contains(query) == true
}