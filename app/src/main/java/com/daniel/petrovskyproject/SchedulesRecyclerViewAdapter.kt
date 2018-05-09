package com.daniel.petrovskyproject

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class SchedulesRecyclerViewAdapter (
        var list: List<DaySchedule>,
        var listener: OnFragmentItemInteractionListener,
        var selectedDate: Calendar,
        var context: Context)
    : RecyclerView.Adapter<SchedulesRecyclerViewAdapter.ViewHolder>() {

    init {
        Log.i("recycler adapter", "init")
    }

    private val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_day_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (list[position].date.compareTo(selectedDate) == 0){
            val d = ContextCompat.getDrawable(context, R.drawable.selected_list_item_background)
            holder.rootView.background = d
        } else {
            holder.rootView.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.item = list[position]
        holder.dateView.text = simpleDateFormat.format(holder.item!!.date.time)
        holder.brigade1.text = holder.item!!.brigade1
        holder.brigade2.text = holder.item!!.brigade2
        holder.brigade3.text = holder.item!!.brigade3
        holder.brigade4.text = holder.item!!.brigade4
        holder.rootView.setOnClickListener { listener.onFragmentInteraction(holder.item!!.date) }

        if (list[position].date.compareTo(selectedDate) == 0){
            val d = ContextCompat.getDrawable(context, R.drawable.selected_list_item_background)
            holder.rootView.background = d
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var item: DaySchedule? = null
        val rootView: ConstraintLayout
        val dateView: TextView
        val brigade1: TextView
        val brigade2: TextView
        val brigade3: TextView
        val brigade4: TextView

        init{
            rootView = view.findViewById(R.id.fragment_day_schedule_constraint_schedule_root)
            dateView = view.findViewById(R.id.fragment_day_schedule_text_view_date)
            brigade1 = view.findViewById(R.id.fragment_day_schedule_text_view_brigade1)
            brigade2 = view.findViewById(R.id.fragment_day_schedule_text_view_brigade2)
            brigade3 = view.findViewById(R.id.fragment_day_schedule_text_view_brigade3)
            brigade4 = view.findViewById(R.id.fragment_day_schedule_text_view_brigade4)
        }

        override fun toString(): String {
            return super.toString() + "Date: ${dateView.text}"
        }
    }

    interface OnFragmentItemInteractionListener{
        fun onFragmentInteraction(date: Calendar)
    }
}