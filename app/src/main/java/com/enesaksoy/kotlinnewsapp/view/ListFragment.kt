package com.enesaksoy.kotlinnewsapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesaksoy.kotlinnewsapp.R
import com.enesaksoy.kotlinnewsapp.adapter.ListNewAdapter
import com.enesaksoy.kotlinnewsapp.databinding.ListFragmentBinding
import com.enesaksoy.kotlinnewsapp.viewmodel.ListViewModel

class ListFragment : Fragment(R.layout.list_fragment) {
    private lateinit var binding: ListFragmentBinding
    private lateinit var viewmodel : ListViewModel
    private lateinit var adapter : ListNewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ListFragmentBinding.bind(view)
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListNewAdapter()
        binding.recyclerViewNews.adapter = adapter
        viewmodel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        viewmodel.refreshdata()
        binding.swiperefreshlayout.setOnRefreshListener {
            binding.recyclerViewNews.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.NewsError.visibility = View.GONE
            viewmodel.getDataFromApi()
            binding.swiperefreshlayout.isRefreshing = false
        }
        observeOn()
    }

    fun observeOn(){
        viewmodel.getnewsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerViewNews.visibility = View.VISIBLE
                adapter.modelList = it
                adapter.notifyDataSetChanged()
            }
        })

        viewmodel.newsloading.observe(viewLifecycleOwner, Observer {
            if(it == true){
                binding.recyclerViewNews.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.NewsError.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        })

        viewmodel.newserror.observe(viewLifecycleOwner, Observer {
            if(it == true){
                binding.recyclerViewNews.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.NewsError.visibility = View.VISIBLE
            }else{
                binding.NewsError.visibility = View.GONE
            }
        })
    }
}