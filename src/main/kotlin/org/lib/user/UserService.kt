package org.lib.user

import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) : UserDetailsService {
    private val passwordEncoder = BCryptPasswordEncoder()
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun save(user: User): User {
        logger.info("Saving user: {}", user.username)
        user.password = passwordEncoder.encode(user.password)
        val savedUser = userRepository.save(user)
        logger.info("User saved with id: {}", savedUser.id)
        return savedUser
    }

    fun findByUsername(username: String): User? {
        logger.info("Finding user by username: {}", username)
        return userRepository.findByUsername(username)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = findByUsername(username) ?: throw UsernameNotFoundException("User not found with email: $username")
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            emptyList()
        )
    }

    fun findUserById(id: String): User? {
        return userRepository.findById(id).orElse(null)
    }
}
