package com.example.cgtaska.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cgtaska.domain.model.ContactList
import com.example.cgtaska.domain.usecase.UseCases
import com.example.cgtaska.presentation.state.DeleteContactState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    // Deleted Contacts State
    private val _deleteContactsState =
        MutableStateFlow<DeleteContactState>(DeleteContactState.Empty)
    val deleteContactState = _deleteContactsState.asStateFlow()

    val getAllContacts: Flow<PagingData<ContactList>> = useCases.getContactsUseCase()
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.IO)


    suspend fun deleteSelectedContact(contact: ContactList) {
        viewModelScope.launch(Dispatchers.IO) {

            _deleteContactsState.update {
                DeleteContactState.Loading
            }

            try {
                useCases.deleteSelectedContactUseCase(contact)
                _deleteContactsState.emit(DeleteContactState.Success(contact))
            } catch (e: Exception) {
                _deleteContactsState.update {
                    DeleteContactState.Error(e)
                }
            }
        }

    }
}
