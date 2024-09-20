package org.lib.user

import org.lib.config.GenerateToken
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<User> {
        return ResponseEntity.ok(userService.save(user))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        return try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication

            val user = userService.findByUsername(loginRequest.username) ?: return ResponseEntity.notFound().build()
            val token = GenerateToken(user)

            val response = mapOf(
                "token" to token,
                "userId" to user.id
            )
            ResponseEntity.ok(response)
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or password")
        }
    }

    @GetMapping("/{id}")
    fun getSingleUser(@PathVariable id: String): ResponseEntity<User> {
        val user = userService.findUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

data class LoginRequest(val username: String, val password: String)
