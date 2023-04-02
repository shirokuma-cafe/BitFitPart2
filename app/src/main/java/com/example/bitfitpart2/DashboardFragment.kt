package com.example.bitfitpart2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max

class DashboardFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val avgAmount = activity?.findViewById<TextView>(R.id.avgAmount)
        val minAmount = activity?.findViewById<TextView>(R.id.minAmount)
        val maxAmount = activity?.findViewById<TextView>(R.id.maxAmount)

        var sum: Int = 0
        var count: Int = 0
        var min: Int = 9999
        var max: Int = 0

        lifecycleScope.launch(Dispatchers.IO){
            for(item in (activity?.application as ItemApplication).db.itemDao().getAmount()){
                count++
                sum += item
                avgAmount?.text = "Average amount of water: " + (sum/count).toString() + " mL"
                if(item > max) max = item
                if(item < min) min = item
                minAmount?.text = "Minimum amount of water: " + min.toString() + " mL"
                maxAmount?.text = "Maximum amount of water: " + max.toString() + " mL"
            }
        }
    }
}