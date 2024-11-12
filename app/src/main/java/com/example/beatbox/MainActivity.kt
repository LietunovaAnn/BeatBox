package com.example.beatbox

import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var beatBoxViewModel: BeatBoxViewModel
    private lateinit var binding: ActivityMainBinding
    private var playbackSpeed: Float = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBoxViewModel = ViewModelProvider(this)[BeatBoxViewModel::class.java]
        val beatBox = beatBoxViewModel.beatBox

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }

        binding.speedSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                playbackSpeed = progress/100.0f
                updatePlaybackSpeed(playbackSpeed)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updatePlaybackSpeed(playbackSpeed: Float) {
        val adapter = binding.recyclerView.adapter as SoundAdapter
        for (i in 0 until adapter.itemCount) {
            val holder = binding.recyclerView.findViewHolderForAdapterPosition(i) as? SoundHolder
            holder?.binding?.viewModel?.playbackSpeed = playbackSpeed
        }
    }

    private inner class SoundHolder(val binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

            init {
                binding.viewModel = SoundViewModel(beatBoxViewModel.beatBox)
            }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }

    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )

            binding.lifecycleOwner = this@MainActivity

            return SoundHolder(binding)
        }

        override fun getItemCount(): Int = sounds.size

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }
    }
}