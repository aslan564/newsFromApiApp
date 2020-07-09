package com.aslanovaslan.newskotlin.model.website


import com.google.gson.annotations.SerializedName

data class Source(
    val category: String?=null,
    val country: String?=null,
    val description: String?=null,
    val id: String?=null,
    val language: String?=null,
    val name: String?=null,
    val url: String?=null
)