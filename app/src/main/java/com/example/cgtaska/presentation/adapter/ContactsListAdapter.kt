package com.example.cgtaska.presentation.adapter

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cgtaska.R
import com.example.cgtaska.common.loadImage
import com.example.cgtaska.databinding.ItemContactslistBinding
import com.example.cgtaska.domain.model.ContactList

class ContactsListAdapter(
    val mContext: Context,
    val onMenuUpdateClick: (Int) -> Unit,
    val onMenuDeleteClick: (ContactList) -> Unit,
    val onClick: (Int) -> Unit,
) : PagingDataAdapter<ContactList, ContactsListAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(val binding: ItemContactslistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding =
            ItemContactslistBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact = getItem(position)

        contact?.let {

            with(holder.binding) {

                tvName.text = it.first_name + " " + it.last_name
                tvEmail.text = it.email

                contact.avatar.let {
                    ivPerson.loadImage(it)
                }
            }
        }

        holder.itemView.setOnClickListener {
            contact?.id?.let { it1 -> onClick(it1) }
        }

        holder.binding.ivMenus.setOnClickListener {

            val popMenu = PopupMenu(
                mContext,
                holder.binding.ivMenus
            )

            popMenu.inflate(R.menu.option_menu)

            popMenu.setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.nav_update -> {
                        contact?.id?.let { it1 -> onMenuUpdateClick(it1) }
                        return@setOnMenuItemClickListener true
                    }

                    R.id.nav_delete -> {
                        contact?.id?.let { onMenuDeleteClick(contact) }
                        return@setOnMenuItemClickListener true
                    }
                }
                false
            }
            popMenu.show()

        }

    }

    object DiffCallBack : DiffUtil.ItemCallback<ContactList>() {

        override fun areItemsTheSame(oldItem: ContactList, newItem: ContactList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactList, newItem: ContactList): Boolean {
            return oldItem == newItem
        }
    }
}