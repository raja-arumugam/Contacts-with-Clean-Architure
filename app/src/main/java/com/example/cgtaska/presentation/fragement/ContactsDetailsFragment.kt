package com.example.cgtaska.presentation.ui.fragement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cgtaska.R
import com.example.cgtaska.databinding.FragmentContactsDetailsBinding
import com.example.cgtaska.di.component.app.Injectable
import androidx.navigation.fragment.navArgs
import com.example.cgtaska.common.loadImage
import com.example.cgtaska.di.component.viewmodel.injectViewModel
import com.example.cgtaska.presentation.activity.MainActivity
import com.example.cgtaska.presentation.state.ContactState
import com.example.cgtaska.presentation.viewmodel.ContactsDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentContactsDetailsBinding
    private lateinit var mViewModel: ContactsDetailsViewModel
    private val navArgs: ContactsDetailsFragmentArgs by navArgs()

    private var contactID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contacts_details, container, false)

        mViewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.toolbarTitle.text = resources.getString(R.string.contact_details)

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        contactID = navArgs.userId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etFirstName.isEnabled = false
            etLastName.isEnabled = false
            etEmail.isEnabled = false
            ivUser.isEnabled = false
            btAddUpdate.isEnabled = false

            bottomCard.setBackgroundResource(R.drawable.bg_gradient)
        }

        getSelectedContact(contactID)
    }

    private fun getSelectedContact(contactID: Int) {

        lifecycleScope.launch(Dispatchers.IO) {
            mViewModel.getSelectedContact(contactID)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mViewModel.contactState.collectLatest { it ->
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