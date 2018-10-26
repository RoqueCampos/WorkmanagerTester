package com.example.workmanagertester

import android.content.Context
import android.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class TestWorker1(val context: Context, params: WorkerParameters)
    : Worker(context, params) {


    override fun doWork(): Result {

        val key = inputData.getString("key")

        val logFormatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val dateString = logFormatter.format(Date())
        val log0 = preferences.getString(key, "");
        val log1 = "-->${key}: $dateString\n$log0"
        preferences.edit().putString(key, log1).apply()
        return Result.SUCCESS

    }

}