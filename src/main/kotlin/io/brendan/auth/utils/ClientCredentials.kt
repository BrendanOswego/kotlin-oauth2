package io.brendan.auth.utils

import java.lang.IllegalStateException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.Instant

object ClientCredentials {

    fun accessToken(clientId: String, code: String, redirectUri: String): String? {
        return try {
            val md5 = MessageDigest.getInstance("md5")
            val time = Instant.now().epochSecond
            md5.update(time.toByte())
            md5.update(clientId.toByteArray())
            md5.update(code.toByteArray())
            md5.update(redirectUri.toByteArray())
            val bytes = md5.digest()
            val n = BigInteger(1, bytes)
            String.format("%032x", n)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }

    }

    fun refreshToken(clientSecret: String, code: String, redirectUri: String): String? {
        return try {
            val md5 = MessageDigest.getInstance("md5")
            val time = Instant.now().epochSecond
            md5.update(time.toByte())
            md5.update(clientSecret.toByteArray())
            md5.update(code.toByteArray())
            md5.update(redirectUri.toByteArray())
            val bytes = md5.digest()
            val n = BigInteger(1, bytes)
            String.format("%032x", n)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }

    }

    fun createAuthorizationCode(clientId: String): String? {
        return try {
            val md5 = MessageDigest.getInstance("md5")
            md5.update(clientId.toByteArray())
            val bytes = md5.digest()
            val preHex = BigInteger(1, bytes)
            return String.format("%032x", preHex)
        } catch (exception: NoSuchAlgorithmException) {
            exception.printStackTrace()
            null
        }
    }

    fun createClient(app: String, url: String): Pair<String, String> {
        val clientId = createClientId(app, url) ?: throw IllegalStateException("Could not create client_id")
        val clientSecret = createClientSecret(clientId, app, url)
                ?: throw IllegalStateException("Could not create client_secret")

        return Pair(clientId, clientSecret)
    }

    private fun createClientId(app: String, url: String): String? {
        return try {
            val md5 = MessageDigest.getInstance("md5")
            md5.update(app.toByteArray())
            md5.update(url.toByteArray())
            val bytes: ByteArray = md5.digest()
            val preHex = BigInteger(1, bytes)
            String.format("%032x", preHex)
        } catch (exception: NoSuchAlgorithmException) {
            exception.printStackTrace()
            null
        }
    }

    private fun createClientSecret(clientId: String, app: String, url: String): String? {
        return try {
            val sha = MessageDigest.getInstance("sha-256")
            sha.update(clientId.toByteArray())
            sha.update(url.toByteArray())
            sha.update(url.toByteArray())
            val bytes: ByteArray = sha.digest()
            val preHex = BigInteger(1, bytes)
            String.format("%032x", preHex)
        } catch (exception: NoSuchAlgorithmException) {
            exception.printStackTrace()
            null
        }
    }

}