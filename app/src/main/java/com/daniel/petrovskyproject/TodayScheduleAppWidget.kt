package com.daniel.petrovskyproject

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat


/**
 * Implementation of App Widget functionality.
 */
class TodayScheduleAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        ids = appWidgetIds

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        private val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        private var ids: IntArray? = null

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val today = Scheduler().getToday()
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.today_schedule_app_widget)
            views.setTextViewText(R.id.widget_text_view_date, simpleDateFormat.format(today.date.time))
            views.setTextViewText(R.id.widget_text_view_brigade1, today.brigade1)
            views.setTextViewText(R.id.widget_text_view_brigade2, today.brigade2)
            views.setTextViewText(R.id.widget_text_view_brigade3, today.brigade3)
            views.setTextViewText(R.id.widget_text_view_brigade4, today.brigade4)

            val intentOnClick = Intent(context, MainActivity::class.java)
            val pendingIntentOnClick = PendingIntent.getActivity(context, 1, intentOnClick, 0)
            views.setOnClickPendingIntent(R.id.widget_linear_layout_root, pendingIntentOnClick)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

