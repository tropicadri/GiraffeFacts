package com.example.giraffefacts


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.provider.FontRequest
import androidx.core.text.HtmlCompat
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val TAG = "EmojiCompatApplication"
    val EMOJI = "\ud83e\udd92"
    lateinit var factTextView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
            .setReplaceAll(true)
            .registerInitCallback(object : EmojiCompat.InitCallback() {
                override fun onInitialized() {
                    Log.i(TAG, "EmojiCompat initialized")
                }

                override fun onFailed(throwable: Throwable?) {
                    Log.e(TAG, "EmojiCompat initialization failed", throwable)
                }
            })

        EmojiCompat.init(config)

        setContentView(R.layout.activity_main)

        val emojiButton: TextView = findViewById(R.id.button)
        emojiButton.text = getString(R.string.emoji_button, EMOJI)

        factTextView = findViewById(R.id.fact_text_view)



         HtmlCompat.fromHtml(getString(R.string.fact_source), HtmlCompat.FROM_HTML_MODE_LEGACY);


    }

    fun showFact(view: View) {
        val nextFact = Random.nextInt(0,10)
        val facts = resources.getStringArray(R.array.facts)
        val fact = facts[nextFact];
        factTextView.text=fact;


    }
}
