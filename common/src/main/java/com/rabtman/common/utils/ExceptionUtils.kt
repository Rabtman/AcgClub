package com.rabtman.common.utils

object ExceptionUtils {

    fun errToStr(t: Throwable): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(t.toString())
        stringBuilder.append("\n")
        val stack = t.stackTrace
        for (aStack in stack) {
            stringBuilder.append(aStack.toString())
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}