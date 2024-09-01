package id.rettorio.luxmarket.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rettorio.luxmarket.data.Application
import id.rettorio.luxmarket.data.Repository
import id.rettorio.luxmarket.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class ProfileViewModel(
    private val repository: Repository,
): ViewModel() {
    private var _userId = Application.selectedUser
    private val _profile: MutableStateFlow<User> = MutableStateFlow(repository.getSelectedUser())
    val profile get() = _profile.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _profile.value)


    private fun randomId(currentId: Int): Int {
        var result = currentId
        while (result == currentId) {
            result = Random.nextInt(1, 30)
        }
        return  result
    }

    fun randomUser() {
        val newUser = randomId(_userId)
        repository.updateUser(newUser)
        _profile.update { repository.getSelectedUser() }
    }
}