package com.example.beatbox

// Создаём константу для хранения строки с расшрением звукового файла
private const val WAV = ".wav"

// Класс для хранения одного звукового объекта
// Класс принимает строку с путём до звукового актива
class Sound(val assetPath: String) {

    // Извлекаем имя звукового актива из пути к данному файлу
    val name = assetPath.split("/").last().removeSuffix(WAV)
}