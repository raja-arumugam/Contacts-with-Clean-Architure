package com.example.contacts.presentation.fragement

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.contacts.R
import com.example.contacts.common.ValidateHelper
import com.example.contacts.databinding.FragmentAddContactBinding
import com.example.contacts.di.component.app.Injectable
import com.example.contacts.di.component.viewmodel.injectViewModel
import com.example.contacts.domain.model.ContactList
import com.example.contacts.presentation.state.AddContactState
import com.example.contacts.presentation.viewmodel.AddContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class AddContactFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: FragmentAddContactBinding
    lateinit var mViewModel: AddContactViewModel

    private var userImage: Uri? = null
    private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    private val validateHelper: ValidateHelper by lazy { ValidateHelper(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)

        mViewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        with(binding) {

            toolbarTitle.text = resources.getString(R.string.add_contact)

            ivBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            bottomCard.setBackgroundResource(R.drawable.bg_gradient)

            btAddUpdate.setOnClickListener {
                createUser()
            }
        }

        binding.ivUser.setOnClickListener {
            pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.ivUser.setImageURI(uri)
                    userImage = uri
                }
            }

        getAddContactResponse()
        return binding.root

    }

    private fun createUser() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()

        if (
            validateHelper.validateFirstName(firstName) &&
            validateHelper.validateLastName(lastName) &&
            validateHelper.validateEmail(email) &&
            validateHelper.validateUserImage(userImage)
        ) {
            val user: List<ContactList> =
                listOf(
                    ContactList(
                        userImage.toString(), email, firstName, 0, lastName
                    )
                )

            lifecycleScope.launch(Dispatchers.IO) {
                mViewModel.createContact(user)
            }

            binding.pbDetails.isVisible = true

        }
    }

    private fun getAddContactResponse() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mViewModel.addContactState.collectLatest {
                    when (it) {
                        is AddContactState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.throwable.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                            binding.pbDetails.isVisible = false

                        }

                        is AddContactState.Loading -> {
                            binding.pbDetails.isVisible = true
                        }

                        is AddContactState.Success -> {
                            binding.pbDetails.isVisible = false

                            Toast.makeText(
                                requireContext(),
                                R.string.user_added_success,
                                Toast.LENGTH_SHORT
                            ).show()

                            val direction =
                                AddContactFragmentDirections.actionAddContactFragmentToContactsFragment()
                            findNavController().navigate(direction)

                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}