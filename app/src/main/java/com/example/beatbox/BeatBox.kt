package com.example.beatbox

import android.content.res.AssetManager
import android.util.Log

// Создаём тэг для отладки класса
private const val TAG = "BeatBoxTAG"

// Создаём константу для хранения названия папки со звуковыми активами
private const val SOUNDS_FOLDER = "sample_sounds"

// Класс для работы со звуковыми активами
// Класс прнимает экземпляр AssetManager для работы с активами
class BeatBox(private val assets: AssetManager) {

    // Создаём список для хранения звуков
    val sounds: List<Sound>

    // Функция для инициализации класса
    init {

        // Загружаем список звуков
        sounds = loadSounds()
    }

    // Функция для загрузки списка звуков
    private fun loadSounds(): List<Sound> {

        // Создаём переменную для хранения массива имён звуковых файлов
        val soundNames: Array<String>

        // Отлавливаем ошибки
        try {

            // Считываем массив имён звуковых файлов из assets
            // ("!!" используется чтобы указать, что ожидаемый результат не равен null)
            // (При возникновении null возникает ошибка, которая отлавливается блоком try)
            soundNames = assets.list(SOUNDS_FOLDER)!!
        }

        // Блок кода, выполняющийся при возникновении ошибки в блоке try
        catch (e: Exception) {

            // Выводим отладочное сообщение о том, что не удалось получть список звуковых активов
            Log.e(TAG, "Could not list assets", e)

            // Возвращаем пустой список
            return emptyList()
        }

        // Создаём список для храненя звуков
        val sounds = mutableListOf<Sound>()

        // Перебираем все имена звуковых файлов
        soundNames.forEach { filename ->

            // Генерируем путь до звукового файла
            val assetPath = "$SOUNDS_FOLDER/$filename"

            // Создаём экземпляр класса Sound, передавая ему путь до звукового файла
            val sound = Sound(assetPath)

            // Добавялем звук в соответсвующий список
            sounds.add(sound)
        }

        // Возвращаем список звуков
        return sounds
    }
}