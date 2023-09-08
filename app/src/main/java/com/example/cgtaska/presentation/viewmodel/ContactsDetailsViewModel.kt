package com.example.cgtaska.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cgtaska.domain.model.ContactList
import com.example.cgtaska.domain.usecase.UseCases
import com.example.cgtaska.presentation.state.ContactState
import com.example.cgtaska.presentation.state.UpdateContactState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsDetailsViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    private val _contactsState = MutableStateFlow<ContactState>(ContactState.Empty)
    val contactState = _contactsState.asStateFlow()

    suspend fun getSelectedContact(contactID: Int) {

        _contactsState.update {
            ContactState.Loading
        }

        useCases.getSelectedContactUseCase(contactID).onEach { contact ->
            _contactsState.update {
                ContactState.Success(contact)
            }
        }.catch { throwable: Throwable ->
            _contactsState.update {
                ContactState.Error(throwable)
            }
        }.launchIn(viewModelScope)
    }

}