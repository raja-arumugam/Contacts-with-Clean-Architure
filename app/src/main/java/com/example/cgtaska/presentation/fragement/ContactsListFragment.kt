package com.example.cgtaska.presentation.fragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cgtaska.R
import com.example.cgtaska.databinding.FragmentContactslistBinding
import com.example.cgtaska.di.component.app.Injectable
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cgtaska.di.component.viewmodel.injectViewModel
import com.example.cgtaska.domain.model.ContactList
import com.example.cgtaska.presentation.activity.MainActivity
import com.example.cgtaska.presentation.adapter.ContactsListAdapter
import com.example.cgtaska.presentation.adapter.ContactsLoadStateAdapter
import com.example.cgtaska.presentation.state.DeleteContactState
import com.example.cgtaska.presentation.viewmodel.ContactsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentContactslistBinding
    private lateinit var mViewModel: ContactsViewModel
    private lateinit var mAdapter: ContactsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contactslist, container, false)

        mViewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.toolbarTitle.text = resources.getString(R.string.contact_list)

        binding .ivAddContact.setOnClickListener {
            val contactListDirection =
                ContactsListFragmentDirections.actionContactsFragmentToAddContactFragment()
            findNavController().navigate(contactListDirection)
        }

        binding.rvContactsList.apply {
            layoutManager = LinearLayoutManager(requireContext())

            mAdapter = ContactsListAdapter(requireContext(), {

                //Update user
                val action =
                    ContactsListFragmentDirections.actionContactsFragmentToUpdateFragment(
                        it,
                    )
                findNavController().navigate(action)
            }, {

                // Delete User
                showDeleteDialog(it)
            }, {

                // Navigate to detail
                val action =
                    ContactsListFragmentDirections.actionUsersFragmentToContactsDetailsFragment(
                        it,
                    )
                findNavController().navigate(action)
            })

            adapter = mAdapter.withLoadStateFooter(
                footer = ContactsLoadStateAdapter(mAdapter::retry)
            )
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

            mViewModel.getAllContacts.flowWithLifecycle(
                viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED
            )
                .collectLatest { data ->
                    mAdapter.submitData(data)
                }
        }

        return binding.root
    }

    private fun showDeleteDialog(contact: ContactList) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_user)
            .setMessage(R.string.confirm_delete)
            .setPositiveButton(R.string.yes) { dialog, _ ->

                lifecycleScope.launch(Dispatchers.IO) {
                    mViewModel.deleteSelectedContact(contact)
                }

                dialog.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()

        getDeleteUserResponse()
    }

    private fun getDeleteUserResponse() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            mViewModel.deleteContactState.collectLatest {
                when (it) {
                    is DeleteContactState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            it.throwable.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is DeleteContactState.Loading -> {

                    }

                    is DeleteContactState.Success -> {
                        withContext(Dispatchers.Main) {

                            Toast.makeText(
                                requireContext(),
                                R.string.user_deleted_success,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}