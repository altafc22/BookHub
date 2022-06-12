package com.altafrazzaque.bookhub.base

import android.R
import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity : AppCompatActivity() {

    abstract fun setObservers()

    override fun attachBaseContext(base: Context) {
        val configuration = base.resources.configuration

        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f
            super.attachBaseContext(base.createConfigurationContext(configuration))
            return
        }

        super.attachBaseContext(base)
    }

    override fun getResources(): Resources {
        val resources = super.getResources()

        if (resources?.configuration?.fontScale != 1.0f) {
            val configuration = resources.configuration
            configuration.fontScale = 1.0f

            val context = createConfigurationContext(configuration)
            return context.resources
        }

        return resources
    }

    fun showSnackBar(text:String){
        val view = findViewById<View>(R.id.content)
        Snackbar.make( view,text, Snackbar.LENGTH_LONG).show()
    }
}