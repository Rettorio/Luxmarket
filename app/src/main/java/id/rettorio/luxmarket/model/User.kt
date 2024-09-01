package id.rettorio.luxmarket.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val gender: String,
    val email: String,
    @SerialName("phone")
    val phoneNumber: String,
    val address: String,
    val shipping: Double,
    val photo: String
)