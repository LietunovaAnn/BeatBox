package com.example.beatbox

//import androidx.lifecycle.MutableLiveData
//
//class SoundViewModel {
//    val title:MutableLiveData<String?> = MutableLiveData()
//
//    var sound: Sound? = null
//        set(sound) {
//            field = sound
//            title.postValue(sound?.name)
//        }
//}


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox): BaseObservable() {

    var playbackSpeed: Float = 1.0f

    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    @get:Bindable
    val title: String?
        get() = sound?.name

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it, playbackSpeed)
        }
    }
}