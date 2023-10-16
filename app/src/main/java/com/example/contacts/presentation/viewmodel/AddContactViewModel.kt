package com.example.contacts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.domain.model.ContactList
import com.example.contacts.domain.usecase.UseCases
import com.example.contacts.presentation.state.AddContactState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddContactViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    private val _addContactState = MutableStateFlow<AddContactState>(AddContactState.Empty)
    val addContactState = _addContactState.asStateFlow()

    suspend fun createContact(list: List<ContactList>) {
        viewModelScope.launch(Dispatchers.IO) {

            _addContactState.update {
                AddContactState.Loading
            }

            try {
                useCases.createContactUseCase(list)
                _addContactState.emit(AddContactState.Success(list))

            } catch (e: Exception) {
                _addContactState.update {
                    AddContactState.Error(e)
                }
            }
        }
    }

}