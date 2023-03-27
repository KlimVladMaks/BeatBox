package com.example.beatbox

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

// Класс для модульного тестирования класса SoundViewModel
// (Основной принцип модульного тестирования - изолированная проверка работы отдельных классов вне
// контекста работы других компонентов приложения)
class SoundViewModelTest {

    // Создаём переменную для хранения экземпляра класса BeatBox
    private lateinit var beatBox: BeatBox

    // Создаём переменную для хранения звука
    private lateinit var sound: Sound

    // Создаём переменную для хранения экземпляра класса SoundViewModel, который является объектом тестирования
    private lateinit var subject: SoundViewModel

    // Функция внутри которой мы конструируем экземпляр SoundViewModel для тестирования.
    // Аннотация @Before сигнализирует о том, что код, содержащийся в данной функции, будет выполнен
    // один раз перед выполнением каждого теста
    @Before
    fun setUp() {

        // Создаём имитацию класса BeatBox, которая имеет все те же функции, но которые ничего не делают
        beatBox = mock(BeatBox::class.java)

        // Создаём экземляр звука
        sound = Sound("assetPath")

        // Создаём экземпляр SoundViewModel, передавая ему экземпляр beatBox
        subject = SoundViewModel(beatBox)

        // Загружаем созданый звук в SoundViewModel
        subject.sound = sound
    }

    // Тест для проверки наличия связи между именем и заголовком звука
    @Test
    fun exposesSoundNameAsTitle() {

        // Проверяем, что subject.title и sound.name имеют одинаковые значения
        // (Если значения не совпадают, то тест не будет пройден)
        assertThat(subject.title, `is`(sound.name))
    }

    // Тест для проверки того, вызывает ли функция onButtonClicked() функцию BeatBox.play()
    @Test
    fun callsBeatBoxPlayOnButtonClicked() {

        // Вызываем функцию нажатия кнопки воспроизведения звука
        subject.onButtonClicked()

        // Проверяем, что у экземпляра beatBox была вызвана функция play() с объектом sound
        verify(beatBox).play(sound)
    }
}


