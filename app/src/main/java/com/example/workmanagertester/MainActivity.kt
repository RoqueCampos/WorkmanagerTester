package com.example.workmanagertester

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    val logFormatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)

    val PREFERENCES_KEY_JOBS_DEPLOYED="jobs deployed"

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val text = sharedPreferences?.getString(key, "")
        when (key) {

            Global.key_15 -> {
                textView1.text = text
            }
            Global.key_30 -> {
                textView2.text = text
            }
            Global.key_60 -> {
                textView3.text = text
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (jobsNotDeployed()) {
            deploy(Global.key_30)
            deploy(Global.key_15)
            deploy(Global.key_60)
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(PREFERENCES_KEY_JOBS_DEPLOYED, true).apply()
        }

        paintTVS()

    }

    private fun jobsNotDeployed(): Boolean {
        return !PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREFERENCES_KEY_JOBS_DEPLOYED, false)
    }

    private fun paintTVS() {
        textView1.text = PreferenceManager.getDefaultSharedPreferences(this).getString(Global.key_15, "")
        textView2.text = PreferenceManager.getDefaultSharedPreferences(this).getString(Global.key_30, "")
        textView3.text = PreferenceManager.getDefaultSharedPreferences(this).getString(Global.key_60, "")
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
        paintTVS()
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)

    }

    fun deploy(key: String) {
        val minutes = when (key) {
            Global.key_15 -> 15
            Global.key_30 -> 30
            else -> 60
        }
        val myData: Data = Data.Builder().putString("key", key).build()
        val builder = PeriodicWorkRequest.Builder(TestWorker1::class.java, minutes.toLong(), TimeUnit.MINUTES)

        builder.addTag(key)
        val constrantBuilder = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)

        builder.setInputData(myData)
        builder.setConstraints(constrantBuilder.build())
        val workRequest = builder.build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(key, ExistingPeriodicWorkPolicy.KEEP, workRequest);
        val message = "-->$key deployed at " + logFormatter.format(Date())
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(key, message).apply()
    }


}
