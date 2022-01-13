package com.example.airqualityandroid.ui.measurements

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.airqualityandroid.R

class MeasurementsFragment : Fragment() {

    companion object {
        fun newInstance() = MeasurementsFragment()
    }

    private lateinit var viewModel: MeasurementsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.measurements_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MeasurementsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}