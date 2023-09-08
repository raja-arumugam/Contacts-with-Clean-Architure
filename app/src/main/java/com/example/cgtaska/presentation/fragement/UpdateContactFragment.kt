package com.example.cgtaska.presentation.fragement

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cgtaska.R
import com.example.cgtaska.common.ValidateHelper
import com.example.cgtaska.common.circularProgressDrawable
import com.example.cgtaska.common.loadImage
import com.example.cgtaska.databinding.FragmentUpdateContactBinding
import com.example.cgtaska.di.component.app.Injectable
import com.example.cgtaska.di.component.viewmodel.injectViewModel
import com.example.cgtaska.domain.model.ContactList
import com.example.cgtaska.presentation.state.ContactState
import com.example.cgtaska.presentation.state.UpdateContactState
import com.example.cgtaska.presentation.viewmodel.UpdateContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class UpdateContactFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentUpdateContactBinding
    private lateinit var mViewModel: UpdateContactViewModel
    private val navArgs: UpdateContactFragmentArgs by navArgs()

    private var userImage: Uri? = null
    private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

//    private var galleryActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    private val validateHelper: ValidateHelper by lazy { ValidateHelper(requireContext()) }
    private var contactID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_contact, container, false)

        mViewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        with(binding) {
            bottomCard.setBackgroundResource(R.drawable.bg_gradient)

            binding.toolbarTitle.text = resources.getString(R.string.update_contact_detail)

            ivBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        contactID = navArgs.userId
        getSelectedContactDetails(contactID)

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

        /* binding.ivUser.setOnClickListener {

             val galleryIntent =
                 Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
             galleryActivityResultLauncher?.launch(galleryIntent)
         }

         galleryActivityResultLauncher =
             registerForActivityResult(
                 ActivityResultContracts.StartActivityForResult()
             ) { result ->
                 if (result.resultCode === Activity.RESULT_OK) {
                     val imageUri: Uri? = result.data?.data
                     binding.ivUser.setImageURI(imageUri)
                     userImage = imageUri
                 }
             }*/

        binding.btAddUpdate.setOnClickListener {
            updateUser(userImage)
        }

        getUpdateContactResponse()
        return binding.root
    }

    private fun updateUser(image: Uri?) {

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()

        if (
            validateHelper.validateFirstName(firstName) &&
            validateHelper.validateLastName(lastName) &&
            validateHelper.validateEmail(email) &&
            validateHelper.validateUserImage(image)
        ) {
            val contact: List<ContactList> =
                listOf(
                    ContactList(
                        image.toString(), email, firstName, contactID, lastName
                    )
                )

            mViewModel.updateContact(contact)
        }
    }

    private fun getUpdateContactResponse() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mViewModel.updateContactState.collectLatest {
                    when (it) {
                        is UpdateContactState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.throwable.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                            binding.pbDetails.isVisible = false

                        }

                        is UpdateContactState.Loading -> {
                            binding.pbDetails.isVisible = true
                        }

                        is UpdateContactState.Success -> {
                            binding.pbDetails.isVisible = false

                            Toast.makeText(
                                requireContext(),
                                R.string.user_updated_success,
                                Toast.LENGTH_SHORT
                            ).show()

                            val direction =
                                UpdateContactFragmentDirections.actionUpdateFragmentToContactsFragment()
                            findNavController().navigate(direction)

                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun getSelectedContactDetails(contactID: Int) {

        lifecycleScope.launch(Dispatchers.IO) {
            mViewModel.getSelectedContact(contactID)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mViewModel.contactState.collectLatest {
                    when (it) {
                        is ContactState.Error -> {
                            with(binding) {
                                pbDetails.isVisible = false
                                cvImage.isVisible = false
                                bottomCard.isVisible = false
                            }

                            Toast.makeText(
                                requireContext(),
                                it.throwable.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is ContactState.Loading -> {
                            with(binding) {
                                pbDetails.isVisible = true
                                cvImage.isVisible = false
                                bottomCard.isVisible = false
                            }
                        }

                        is ContactState.Success -> {
                            it.data.let { contact ->
                                with(binding) {
                                    pbDetails.isVisible = false
                                    cvImage.isVisible = true
                                    bottomCard.isVisible = true

                                    contact.avatar.let {
                                        ivUser.loadImage(it)
                                    }

                                    userImage = contact.avatar.toUri()

                                    etFirstName.setText(contact.first_name)
                                    etLastName.setText(contact.last_name)
                                    etEmail.setText(contact.email)
                                }
                            }
                        }

                        else -> {
                            Unit
                        }
                    }
                }
            }
        }
    }

}