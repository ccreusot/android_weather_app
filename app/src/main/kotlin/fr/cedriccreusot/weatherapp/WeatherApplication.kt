package fr.cedriccreusot.weatherapp

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        EmojiCompat.init(BundledEmojiCompatConfig(this))
    }
}