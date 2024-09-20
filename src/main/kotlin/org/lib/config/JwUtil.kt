package org.lib.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.*

class JwtUtil {
    private val secretKey = "b7F!2o@v1k$8xZ#q5dEw&L%gH0nP*4rI" // Same secret key used to sign the token

    // Extract username from the token
    fun extractUsername(token: String): String? {
        return extractClaim(token) { it.subject }
    }

    // Extract expiration date from the token
    private fun extractExpiration(token: String): Date? {
        return extractClaim(token) { it.expiration }
    }

    // Extract claims (payload) from the token
    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    // Parse the JWT and get all claims
    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey.toByteArray())
            .parseClaimsJws(token)
            .body
    }

    // Check if the token has expired
    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractExpiration(token)
        return expiration?.before(Date()) ?: true
    }

    // Validate the token by checking the username and expiration
    fun validateToken(token: String, username: String): Boolean {
        val tokenUsername = extractUsername(token)
        return tokenUsername == username && !isTokenExpired(token)
    }
}
