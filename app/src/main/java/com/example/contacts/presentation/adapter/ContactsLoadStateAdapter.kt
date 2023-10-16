package com.example.contacts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.ContactsLoadStateBinding

class ContactsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ContactsLoadStateAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ContactsLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MyViewHolder, loadState: LoadState) {
        with(holder) {

            if (loadState is LoadState.Error) {
                binding.tvError.text = loadState.error.localizedMessage
            }

            binding.tvError.isVisible = (loadState is LoadState.Error)
            binding.btRetry.isVisible = (loadState is LoadState.Error)

            binding.btRetry.setOnClickListener { retry() }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MyViewHolder {
        return MyViewHolder(
            ContactsLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

}