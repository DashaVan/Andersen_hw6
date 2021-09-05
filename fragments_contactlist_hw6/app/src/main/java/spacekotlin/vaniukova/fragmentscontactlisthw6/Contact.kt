package spacekotlin.vaniukova.fragmentscontactlisthw6

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Long,
    var name: String,
    var surname: String,
    var phoneNumber: String,
    val avatarLink: String
) : Parcelable