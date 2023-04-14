package com.example.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Создаём фабрику, чтобы передать из MainActivity в BeatBoxViewModel объект assets (хранит звуки приложения)
// в качестве аргумента при создании экземпляра
class BeatBoxViewModelFactory(private val assets: AssetManager) : ViewModelProvider.Factory {

    // Переопределяем функцию create() для создания экземпляра BeatBoxViewModel с нужным аргументом
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Если фабрика работает с классом BeatBoxViewModel, то возвращаем его экземпляр с аргументом assets
        if (modelClass.isAssignableFrom(BeatBoxViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeatBoxViewModel(assets) as T
        }

        // Иначе выбрасываем ошибку
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

