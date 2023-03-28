package com.example.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

// Создаём класс главной активити, которая первой запускается при открытии приложения
class MainActivity : AppCompatActivity() {

    // Создаём переменную для хранения экземпляра класса BeatBox
    private lateinit var beatBox: BeatBox

    // Переопределяем функцию, вызываемую при создании активити
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаём экземпляр класса BeatBox, передавая ему экземпляр assets
        beatBox = BeatBox(assets)

        // Создаём экземпляр класса привязки, прикрепляя к нему XML-макет activity_main
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Добавляем к RecyclerView несколько свойств
        binding.recyclerView.apply {

            // Указываем, что список должен содержать три колонки
            // (context - это контекст приложения, требующийся для настройки GridLayoutManager)
            layoutManager = GridLayoutManager(context, 3)

            // Подключаем адаптер, передавая ему список звуков
            adapter = SoundAdapter(beatBox.sounds)
        }
    }

    // Переопределяем функцию, вызываемую при уничтожении MainActivity
    override fun onDestroy() {
        super.onDestroy()

        // Освобождаем ресурсы, удаляя загруженные звуки из SoundPool
        beatBox.release()
    }

    // Создаём Holder-класс, предназначенный для хранения представления одного элемента RecyclerView
    // и всех необходимых сопутсвующих данных
    private inner class SoundHolder(private val binding: ListItemSoundBinding):
        RecyclerView.ViewHolder(binding.root) {

        // Функция для инициализации класса
        init {

            // Подключаем к XML-макету модель представления (SoundViewModel) с помощью привязки данных (binding),
            // передавая SoundViewModel экземпляр beatBox для управления звуками
            binding.viewModel = SoundViewModel(beatBox)
        }

        // Функция для обновления данных, с которыми работает модель представления
        fun bind(sound: Sound) {

            // Добавляем к привязке binding некоторые свойства
            binding.apply {

                // Подключаем звук к модели представления
                viewModel?.sound = sound

                // Вызываем функцию, приказывающую макету обновить себя немедленно,
                // а не выжидая одну-две миллисекунды
                executePendingBindings()
            }
        }
    }

    // Создаём Adapter-класс, преднозначенный для связи источника данных и RecyclerView
    // Класс принимает на вход список звуков
    private inner class SoundAdapter(private val sounds: List<Sound>):
        RecyclerView.Adapter<SoundHolder>() {

        // Переопределяем функцию, используемую RecyclerView для создания представления холдера
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {

            // Создаём экземпляр класса привязки для кнопки вопроизведения звука
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )

            // Оборачиваем созданный выше экзепляр класса привязки в SoundHolder и возвращаем его
            return SoundHolder(binding)
        }

        // Переопределяем функцию, используемую RecyclerView для отображения элемента в требуемой позиции
        // (Поместить холдер на требуемую позицию в требуемом виде)
        override fun onBindViewHolder(holder: SoundHolder, position: Int) {

            // Получаем звук с нужной позицией из списка звуков
            val sound = sounds[position]

            // Обновялем данные холдера, передавая ему полученный выше звук
            holder.bind(sound)
        }

        // Переопределяем функцию, используемую RecyclerView для определения количества элементов в списке
        override fun getItemCount() = sounds.size
    }
}


