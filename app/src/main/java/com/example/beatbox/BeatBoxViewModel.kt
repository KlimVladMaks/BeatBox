package com.example.beatbox

import android.content.Context
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Класс BeatBoxViewModel для сохранения работы BeatBox при повороте устройства
// (При создании экземпляра данного класса ему нужно передать объект assets со звуками приложения)
class BeatBoxViewModel(private val assets: AssetManager): ViewModel() {

    // Создаём экземпляр класса BeatBox, передавая ему экземпляр assets
    var beatBox = BeatBox(assets)

    // Переопределяем функцию, вызываемую при удалении экземпляра данного класса
    override fun onCleared() {
        super.onCleared()

        // Освобождаем ресурсы, удаляя загруженные звуки из SoundPool
        beatBox.release()
    }
}


