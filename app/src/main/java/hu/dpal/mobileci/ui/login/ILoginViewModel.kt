package hu.dpal.mobileci.ui.login

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

interface ILoginViewModel {

    val username: ObservableField<String>
    val password: ObservableField<String>

    val result: ObservableInt

    fun signInAction()

}
