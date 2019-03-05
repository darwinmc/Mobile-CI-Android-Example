package hu.dpal.mobileci.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val id: String?,
    val username: String?,
    val password: String?
) : Parcelable
