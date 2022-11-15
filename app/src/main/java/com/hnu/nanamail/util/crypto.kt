package com.hnu.nanamail.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest

/**
 * 返回字符串的 MD5
 *
 * [Stack Overflow](https://stackoverflow.com/a/56423552) ，CC BY-SA 4.0
 */
fun String.md5(): String? {
    try {
        val md = MessageDigest.getInstance("MD5")
        val array = md.digest(this.toByteArray())
        val sb = StringBuffer()
        for (i in array.indices) {
            sb.append(Integer.toHexString(array[i].toInt() and 0xFF or 0x100).substring(1, 3))
        }
        return sb.toString()
    } catch (_: java.security.NoSuchAlgorithmException) {
    } catch (_: UnsupportedEncodingException) {
    }
    return null
}