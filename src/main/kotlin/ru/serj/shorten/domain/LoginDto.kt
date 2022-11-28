package ru.serj.shorten.domain

data class LoginDto (
    var email: String?,
    var password: String?
){
    constructor() : this(null, null)
}