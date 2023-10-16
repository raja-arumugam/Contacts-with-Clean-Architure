package com.example.contacts.presentation.ui.fragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.contacts.R
import com.example.contacts.common.ValidateHelperLogin
import com.example.contacts.databinding.FragmentLoginBinding
import com.example.contacts.di.component.app.Injectable
import com.example.contacts.di.component.viewmodel.injectViewModel
import com.example.contacts.presentation.state.LoginState
import com.example.contacts.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var mViewModel: LoginViewModel

    private val validateHelperLogin: ValidateHelperLogin by lazy {
        ValidateHelperLogin(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        loginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        mViewModel = injectViewModel(viewModelFactory)
        loginBinding.lifecycleOwner = viewLifecycleOwner

        loginBinding.toolbarTitle.text = resources.getString(R.string.log_in)

        loginBinding.btLogin.setOnClickListener {
            login()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mViewModel.loginState.collectLatest {
                    when (it) {
                        is LoginState.Error -> {
                            loginBinding.pbLogin.isVisible = false

                            AlertDialog.Builder(requireContext())
                                .setTitle(R.string.app_name)
                                .setMessage(R.string.invalid_user_password)
                                .setPositiveButton(R.string.retry) { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .create()
                                .show()
                        }

                        is LoginState.Loading -> {
                            loginBinding.pbLogin.isVisible = true
                        }

                        is LoginState.Success -> {

                            loginBinding.pbLogin.isVisible = false

                            val loginDirection =
                                LoginFragmentDirections.actionLoginFragmentToContactsFragment()
                            findNavController().navigate(loginDirection)
                        }

                        else -> {
                            Unit
                        }
                    }
                }
            }
        }

        return loginBinding.root
    }

    private fun login() {
        val name = loginBinding.etName.text.toString()
        val password = loginBinding.etPassword.text.toString()

        if (validateHelperLogin.validateEmptyName(name) &&
            validateHelperLogin.validateName(name) &&
            validateHelperLogin.validateEmptyPassword(password) &&
            validateHelperLogin.validatePassword(password)
            && validateHelperLogin.isValidPassword(password)
        ) {
            lifecycleScope.launch(Dispatchers.IO) {
                mViewModel.login(name, password)
            }
            loginBinding.pbLogin.isVisible = true
        } else {
            Toast.makeText(requireContext(), R.string.invalid_password_pattern, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}