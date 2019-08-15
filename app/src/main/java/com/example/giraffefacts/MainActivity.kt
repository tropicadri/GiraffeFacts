package com.example.giraffefacts


import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.provider.FontRequest
import androidx.core.text.HtmlCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val TAG = "EmojiCompatApplication"
    val EMOJI = "\ud83e\udd92"
    lateinit var factTextView: TextView
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle


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

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    fun showFact(view: View) {
        val nextFact = Random.nextInt(0, 10)
        val facts = resources.getStringArray(R.array.facts)
        val fact = facts[nextFact];
        factTextView.text = fact;
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
