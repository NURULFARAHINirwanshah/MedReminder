package com.psm.medreminder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.view.calender.horizontal.umar.horizontalcalendarview.DayDateMonthYearModel
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarListener
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarView

class calendar : AppCompatActivity(), HorizontalCalendarListener {


    lateinit var currentMonthTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_test)
        currentMonthTextView = findViewById(R.id.month)
        val hcv = findViewById<HorizontalCalendarView>(R.id.horizontalcalendarview)
        hcv.setContext(this@calendar)
        hcv.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        hcv.showControls(false)
        hcv.setControlTint(R.color.colorAccent)
        hcv.changeAccent(R.color.white)
    }

    override fun updateMonthOnScroll(selectedDate: DayDateMonthYearModel?) {
        currentMonthTextView.text = "" + selectedDate?.month + " " + selectedDate?.year

    }

    override fun newDateSelected(selectedDate: DayDateMonthYearModel?) {

        var array = arrayOf("Melbourne", "Vienna", "Vancouver", "Toronto", "Calgary", "Adelaide", "Perth", "Auckland", "Helsinki", "Hamburg", "Munich", "New York", "Sydney", "Paris", "Cape Town", "Barcelona", "London", "Bangkok")


        val adapter = ArrayAdapter(this,
                R.layout.activity_calendar_test, array)
        val listView: ListView = findViewById(R.id.textView2)
        listView.setAdapter(adapter)


        Toast.makeText(this@calendar, selectedDate?.date + "" + selectedDate?.month + " " + selectedDate?.year, Toast.LENGTH_LONG).show()
    }

}