package com.maritech.arterium.utils.args

import android.os.Bundle

fun Bundle.putIntOrNull(key: String, value: Int?) = putInt(key, value ?: -1)
fun Bundle.getIntOrNull(key: String) = getInt(key, -1).takeIf { it != -1 }
fun Bundle.putLongOrNull(key: String, value: Long?) = putLong(key, value ?: -1L)
fun Bundle.getLongOrNull(key: String) = getLong(key, -1L).takeIf { it != -1L }