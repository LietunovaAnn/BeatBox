package com.example.beatbox

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class BeatBoxViewModel(application: Application): AndroidViewModel(application) {

    val beatBox: BeatBox = BeatBox(application.assets)

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }
}