package com.daniel.petrovskyproject

import kotlinx.android.synthetic.main.activity_main.*

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.DatePicker
import java.util.*

class MainActivity : AppCompatActivity(),
        SchedulesRecyclerViewAdapter.OnFragmentItemInteractionListener,
        DatePickerDialog.OnDateSetListener,
        View.OnClickListener{

    private lateinit var datePickerDialog: DatePickerDialog
    private var itShouldLoadMore = false
    private var recyclerDate = GregorianCalendar()
    private var recyclerModels = Scheduler().setDate(recyclerDate.clone() as Calendar)
    private var recyclerViewAdapter = SchedulesRecyclerViewAdapter(
            recyclerModels, this, recyclerDate.clone() as Calendar, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_recycler_view.layoutManager = LinearLayoutManager(this)
        activity_main_recycler_view.adapter = recyclerViewAdapter
        activity_main_recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0){// Recycle view scrolling downwards...

                    if (!recyclerView?.canScrollVertically(RecyclerView.FOCUS_DOWN)!!){// can't scroll more
                        if(itShouldLoadMore){
                            loadMoreForward()
                        }
                    }
                } else if (dy < 0){
                    if (!recyclerView?.canScrollVertically(RecyclerView.FOCUS_UP)!!){// can't scroll more
                        if(itShouldLoadMore){
                            loadMoreBackward()
                        }
                    }
                }
            }
        })
        itShouldLoadMore = true

        activity_main_button_today.setOnClickListener(this)
        activity_main_image_button_about.setOnClickListener(this)
    }

    // OnFragmentItemInteractionListener ***********************************************************
    override fun onFragmentInteraction(date: Calendar) {
        datePickerDialog = DatePickerDialog(
                this,
                this,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    // OnDateSetListener ***************************************************************************
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        recyclerDate.set(year, month, day)
        setDate()
    }

    // OnClickListener *****************************************************************************
    override fun onClick(view: View?) {
        when(view?.id){
            activity_main_button_today.id -> {
                val today = GregorianCalendar()
                recyclerDate.set(
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH))
                setDate()
            }
            activity_main_image_button_about.id -> {
                intent = Intent(this, InfoActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
    }

    // Util ****************************************************************************************
    private fun setDate() {
        itShouldLoadMore = false
        recyclerModels.clear()
        recyclerModels.addAll(Scheduler().setDate(recyclerDate.clone() as Calendar))
        recyclerViewAdapter.selectedDate = recyclerDate
        recyclerViewAdapter.notifyDataSetChanged()
        itShouldLoadMore = true
    }

    private fun loadMoreForward() {
        itShouldLoadMore = false
        activity_main_progress_bar.visibility = View.VISIBLE
        recyclerModels.addAll(
                Scheduler().getForward(recyclerModels[recyclerModels.size - 1].date.clone() as Calendar)
        )
        recyclerViewAdapter.notifyDataSetChanged()
        activity_main_progress_bar.visibility = View.GONE
        itShouldLoadMore = true
    }

    private fun loadMoreBackward() {
        itShouldLoadMore = false
        activity_main_progress_bar.visibility = View.VISIBLE
        recyclerModels.addAll(0,
                Scheduler().getBackward(recyclerModels[0].date.clone() as Calendar)
        )
        recyclerViewAdapter.notifyDataSetChanged()
        activity_main_progress_bar.visibility = View.GONE
        itShouldLoadMore = true
    }
}
