package com.daniel.petrovskyproject

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


class AlarmBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val ids = intent.getIntArrayExtra("ids")
        // There may be multiple widgets active, so update all of them

        val appWidgetManager = AppWidgetManager.getInstance(context)


        for (id in ids) {
            TodayScheduleAppWidget.updateAppWidget(context, appWidgetManager, id)
        }

    }
}