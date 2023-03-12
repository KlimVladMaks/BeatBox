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

    // Переопределяем функцию, вызываемую при создании активити
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаём экземпляр класса привязки, прикрепляя к нему XML-макет activity_main
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Добавляем к RecyclerView несколько свойств
        binding.recyclerView.apply {

            // Указываем, что список должен содержать три колонки
            // (context - это контекст приложения, требующийся для настройки GridLayoutManager)
            layoutManager = GridLayoutManager(context, 3)

            // Подключаем адаптер
            adapter = SoundAdapter()
        }
    }

    // Создаём Holder-класс, предназначенный для хранения представления одного элемента RecyclerView
    // и всех необходимых сопутсвующих данных
    private inner class SoundHolder(private val binding: ListItemSoundBinding):
        RecyclerView.ViewHolder(binding.root) {}

    // Создаём Adapter-класс, преднозначенный для связи источника данных и RecyclerView
    private inner class SoundAdapter(): RecyclerView.Adapter<SoundHolder>() {

        // Переопределяем функцию, используемую RecyclerView для создания представления холдера
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {

            // Создаём экземпляр класса привязки для кнопки вопроизведения звука
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )

            // Оборачиваем созданный выше экзепляр класса привязки в SoundHolder и возвращаем
            return SoundHolder(binding)
        }

        // Переопределяем функцию, используемую RecyclerView для отображения элемента в требуемой позиции
        override fun onBindViewHolder(holder: SoundHolder, position: Int) {}

        // Переопределяем функцию, используемую RecyclerView для определения количества элементов в списке
        override fun getItemCount() = 0
    }
}


