package ru.serj.shorten.domain

data class LoginDTO (
    var email: String?,
    var password: String?
){
    constructor() : this(null, null)
}