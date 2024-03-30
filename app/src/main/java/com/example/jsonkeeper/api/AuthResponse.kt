package com.example.jsonkeeper.api

sealed class AuthResponse {
    data object AcceptedIssuers : AuthResponse()
    data object NoClientTrusted : AuthResponse()
    data object NoServerTrusted : AuthResponse()
}