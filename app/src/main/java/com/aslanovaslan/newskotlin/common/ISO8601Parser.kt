@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.aslanovaslan.newskotlin.common

import android.annotation.SuppressLint
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

object ISO8601Parser {

    @SuppressLint("SimpleDateFormat")
    fun parse(params: String): Date {
        var inputDate = params

        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
         if (inputDate.endsWith("Z")) {
             inputDate = inputDate.substring(0, inputDate.length - 1) + "GMT-00:00"
        } else {
            val inset = 6
            val starText = inputDate.subSequence(0, inputDate.length - inset).toString()
            val endText = inputDate.substring(inputDate.length - inset, inputDate.length)


            inputDate = StringBuilder(starText).append("GMT").append(endText).toString()
        }
        return df.parse(inputDate)
    }


    @SuppressLint("SimpleDateFormat")
    fun toString(date: Date): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
        val tz = TimeZone.getTimeZone("UTC")
        df.timeZone = tz

        val output = df.format(date)
        val inset0 = 9

        val inset1 = 6

        val s0 = output.substring(0, output.length - inset0)
        val s1 = output.subSequence(output.length - inset1, output.length)

        var result = s0 + s1
        result = result.replace("UTC".toRegex(), replacement = "+00.00")
        return result
    }
}