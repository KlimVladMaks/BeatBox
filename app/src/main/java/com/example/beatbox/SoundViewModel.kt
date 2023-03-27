package com.example.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

// Класс уровня модели представления (ViewModel), предназначенный для связи модели
// (классы данных) и представления (XML-макеты).
// Наследуем класс от BaseObservable, отвечающим за отслеживание и уведомление об изменении данных.
// Конструктор класса принимает экземпляр BeatBox для управления звуками
class SoundViewModel(private val beatBox: BeatBox): BaseObservable() {

    // Переменная для хранения звука (по-умолчанию содержит null)
    var sound: Sound? = null
        // Метод, срабатывающий при обновлении переменной
        set(sound) {
            // Присваиваем переданное значение данной переменной
            field = sound
            // Уводомляем класс привязки (ListItemSoundBinding) о том, что свойства данного экземпляра изменились
            notifyChange()
        }

    // Переменная для хранения имени звука
    // (Используем аннотацию "@get:Bindable" чтобы автоматически обновить переменную title при
    // изменении переменной sound)
    @get:Bindable
    val title: String?
        // При попытке получить значение данной переменной возвращаем имя звука
        get() = sound?.name

    // Функция, вызываемая при нажатии на кнопку воспроизведения звука
    fun onButtonClicked() {

        // Вызываем функцию beatBox.play(), передавая ей соответсвующий звук
        sound?.let {
            beatBox.play(it)
        }
    }
}


