package com.aslanovaslan.newskotlin.model.website


data class WebSiteResponse(
    val sources: List<Source>?=null,
    val status: String
)