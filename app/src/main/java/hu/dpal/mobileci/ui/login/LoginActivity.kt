package hu.dpal.mobileci.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hu.dpal.mobileci.R
import hu.dpal.mobileci.databinding.ActivityLoginBinding
import hu.dpal.mobileci.ui.login.mock.MockLoginViewModel

class LoginActivity : AppCompatActivity() {

    var viewModel: ILoginViewModel? = MockLoginViewModel()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.vm = viewModel

    }

    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).getWindowToken(), 0);
    }

}
