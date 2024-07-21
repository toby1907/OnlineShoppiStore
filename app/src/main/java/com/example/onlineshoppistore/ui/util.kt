package com.example.onlineshoppistore.ui

import java.net.URLDecoder
import java.net.URLEncoder

enum class ShoeBrand {
    Nike,
    Adidas,
    Gucci,
    Jordan,
    Reebok,
    NewBalance,
    Puma,
    All
}

fun encodeText(text: String): String {
    return URLEncoder.encode(text, "UTF-8")
}

/**
 * Decodes the given URL-encoded text.
 */
fun decodeText(encodedText: String): String {
    return URLDecoder.decode(encodedText, "UTF-8")
}