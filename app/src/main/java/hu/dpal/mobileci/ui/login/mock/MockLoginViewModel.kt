package hu.dpal.mobileci.ui.login.mock

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import hu.dpal.mobileci.R
import hu.dpal.mobileci.model.User
import hu.dpal.mobileci.ui.login.ILoginViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MockLoginViewModel: ViewModel(), ILoginViewModel {

    private val disposable = CompositeDisposable()

    override val username = ObservableField<String>("")
    override val password = ObservableField<String>("")

    override val result = ObservableInt()

    private val users = ArrayList<User>()

    private fun addUser(username: String, password: String): User {
        val id = UUID.randomUUID().toString()
        val u = User(id, username, password)
        this.users.add(u)
        return u
    }


    init {
        this.addUser("alice", "abc123")
        this.addUser("bob", "LfdVD=An")
        result.set(R.string.empty)
    }

    override fun signInAction() {

        val res = this.users.count {
            it.username == username.get() && it.password == password.get()
        }

        if (res > 0) {
            result.set(R.string.login_response_success)
        } else {
            result.set(R.string.login_response_fail)
        }

    }


}
