package org.lib.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.lib.user.User
import java.util.Date

fun GenerateToken(user: User): String {
    val secretKey = "mySecretKey" // Replace with your secure secret key

    return Jwts.builder()
        .setSubject(user.username)
        .claim("userId", user.id)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + 86400000)) // Token valid for 24 hours
        .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray())
        .compact()
}
