package com.example.bitfitpart2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemLogFragment : Fragment() {
    private val consumptions = mutableListOf<Item>()
    private lateinit var consumptionsRecyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_log, container, false)

        val layoutManager = LinearLayoutManager(context)
        consumptionsRecyclerView = view.findViewById(R.id.consumptions)
        consumptionsRecyclerView.layoutManager = layoutManager
        consumptionsRecyclerView.setHasFixedSize(true)
        itemAdapter = ItemAdapter(view.context, consumptions)
        consumptionsRecyclerView.adapter = itemAdapter

        return view
    }

    private fun fetchItems(){
        consumptions.clear()
        lifecycleScope.launch{
            (activity?.application as ItemApplication).db.itemDao().getAll().collect { databaseList ->
                databaseList.map { mappedList ->
                    consumptions.addAll(listOf(mappedList))
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }
//        lifecycleScope.launch(Dispatchers.IO){
//            (requireActivity().application as ItemApplication).db.itemDao().deleteAll()
//        }
//        consumptions.clear()
//        itemAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchItems()
    }

//    companion object {
//        fun newInstance(): ItemLogFragment {
//            return ItemLogFragment()
//        }
//    }
}