package com.example.jetpack_compose

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class CounterViewModel(
    application: Application
) : AndroidViewModel(application) {

    // 1. Membuka/membuat file SharedPreferences
    private val prefs = application.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    // 2. State awal counter dibaca dari SharedPreferences (default 0)
    private val _counter = mutableIntStateOf(
        prefs.getInt(KEY_COUNTER, 0)
    )
    val counter: State<Int> = _counter

    // State untuk text input form "Nama"
    private val _inputName = mutableStateOf("")
    val inputName: State<String> = _inputName

    // State nama yang tersimpan, dibaca dari SharedPreferences
    private val _savedName = mutableStateOf(
        prefs.getString(KEY_SAVED_NAME, "") ?: ""
    )
    val savedName: State<String> = _savedName

    // 3. Fungsi yang dipanggil dari UI untuk Counter
    fun increment() = updateCounter(_counter.intValue + 1)
    fun decrement() = updateCounter(_counter.intValue - 1)
    fun reset() = updateCounter(0)

    // 4. Ubah state counter lalu simpan ke SharedPreferences
    private fun updateCounter(newValue: Int) {
        _counter.intValue = newValue
        prefs.edit { putInt(KEY_COUNTER, newValue) }
    }

    // 5. Fungsi untuk form input & tombol Simpan Nama
    fun onInputNameChange(newName: String) {
        _inputName.value = newName
    }

    fun saveName() {
        val nameToSave = _inputName.value.trim()
        _savedName.value = nameToSave
        prefs.edit { putString(KEY_SAVED_NAME, nameToSave) }
    }

    companion object {
        private const val PREFS_NAME = "counter_prefs"
        private const val KEY_COUNTER = "counter_value"
        private const val KEY_SAVED_NAME = "saved_name"
    }
}
