package com.example.airqualityandroid.ui.measurements

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.data.MeasurementPair
import com.example.airqualityandroid.R
import com.example.airqualityandroid.api.MeasurementKey
import com.example.airqualityandroid.utils.MeasurementResolver.Companion.resolveMeasurementDescription
import com.example.airqualityandroid.utils.MeasurementResolver.Companion.resolveMeasurementName

class MeasurementsAdapter: RecyclerView.Adapter<MeasurementsAdapter.ViewHolder>() {
    private lateinit var context: Context
    var measurements = mutableListOf<MeasurementPair>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val measurementIcon: ImageView
        val measurementName: TextView
        val measurementDescription: TextView
        val measurementIndex: TextView
        val measurementValue: TextView

        init {
            //FIXME: elements are in different layouts, so this may be issue here.
            measurementIcon = view.findViewById(R.id.measurement_icon)
            measurementName = view.findViewById(R.id.measurement_name)
            measurementDescription = view.findViewById(R.id.measurement_description)
            measurementIndex = view.findViewById(R.id.measurement_index)
            measurementValue = view.findViewById(R.id.measurement_value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        //FIXME: getting only measurement_item, not layouts for other data outside card
        val view = LayoutInflater.from(context)
            .inflate(R.layout.measurement_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measurement = measurements[position]

        holder.measurementName.text = resolveMeasurementName(context, measurement.key)
        holder.measurementDescription.text = resolveMeasurementDescription(context, measurement.key)
        holder.measurementIndex.text = context.getString(R.string.index_name) + " " + measurement.indexName

        if(measurement.measurementValue == null){
            if(measurement.key == MeasurementKey.ST_INDEX){
                holder.measurementValue.text = context.getString(R.string.VAL_ST_INDEX)

            }else {
                holder.measurementValue.text = context.getString(R.string.VAL_NA)
            }
        }
        else holder.measurementValue.text = String.format("%.2f", measurement.measurementValue)

        //-1 – Brak indeksu, 0 – Bardzo dobry, 1 – Dobry, 2 – Umiarkowany, 3 - Dostateczny, 4 – Zły, 5 – Bardzo zły
        when(measurement.index) {
            -1 -> {
                holder.measurementIcon.setImageResource(R.drawable.ic_baseline_remove_circle_24)
                holder.measurementIcon.setColorFilter(Color.parseColor("#EED202"))
            }
            0,1 -> {
                holder.measurementIcon.setImageResource(R.drawable.ic_baseline_check_circle_24)
                holder.measurementIcon.setColorFilter(Color.parseColor("#4BB543"))
            }
            2,3 -> {
                holder.measurementIcon.setImageResource(R.drawable.ic_baseline_error_24)
                holder.measurementIcon.setColorFilter(Color.parseColor("#EED202"))
            }
            4,5 -> {
                holder.measurementIcon.setImageResource(R.drawable.ic_baseline_error_24)
                holder.measurementIcon.setColorFilter(Color.parseColor("#CC0000"))
            }
            else -> {
                holder.measurementIcon.setImageResource(R.drawable.ic_baseline_check_circle_24)
                holder.measurementIcon.setColorFilter(Color.parseColor("#4BB543"))
            }
        }
    }

    override fun getItemCount() = measurements.size

    @SuppressLint("NotifyDataSetChanged")
    fun setMeasurementPairsList(measurements: List<MeasurementPair>){
        this.measurements = measurements.toMutableList()
        notifyDataSetChanged()
    }
}