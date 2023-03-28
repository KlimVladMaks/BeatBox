package com.example.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

// Создаём тэг для отладки класса
private const val TAG = "BeatBoxTAG"

// Создаём константу для хранения названия папки со звуковыми активами
private const val SOUNDS_FOLDER = "sample_sounds"

// Создаём переменную, храняющую максимально допустимое количество воспроизводимых звуков
private const val MAX_SOUNDS = 5

// Класс для работы со звуковыми активами
// Класс прнимает экземпляр AssetManager для работы с активами
class BeatBox(private val assets: AssetManager) {

    // Создаём список для хранения звуков
    val sounds: List<Sound>

    // Используя Builder(), создаём экземпляр SoundPool, который отвечает за управление звуками на устройстве
    // Функция setMaxStreams() позволяет задать максимально возможное количество вопсроизводимых звуков
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    // Функция для инициализации класса
    init {

        // Загружаем список звуков
        sounds = loadSounds()
    }

    // Функция для вопсроизведения звука
    fun play(sound: Sound) {

        // Запускаем звук по его ID с помощью soundPool
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    // Функция для освобождения ресурсов SoundPool
    fun release() {

        // Освобождаем ресурсы SoundPool, удаляя загруженные звуки
        soundPool.release()
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

            // Отлавиливаем ошибки при загрузке звука
            try {

                // Загружаем звук в SoundPool
                load(sound)

                // Добавялем звук в соответсвующий список
                sounds.add(sound)
            }

            // При возникновении при загрузке звука ошибки IOException
            catch (ioe: IOException) {

                // Выводим отладочное сообщение об ошибке
                Log.e(TAG, "Could not load sound $filename", ioe)
            }
        }

        // Возвращаем список звуков
        return sounds
    }

    // Функция для загрузки звуков в объект для управления звуками SoundPool
    private fun load(sound: Sound) {

        // Создаём экземпляр AssetFileDescriptor, который указывает путь к звуковому файлу
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)

        // Загружаем звук в soundPool, используя путь до файла и задавая приоритет
        // В качетве результата получаем ID загруженного звука
        val soundId = soundPool.load(afd, 1)

        // Записываем в экземпляр переданного звука его ID в soundPool
        sound.soundId = soundId
    }
}


