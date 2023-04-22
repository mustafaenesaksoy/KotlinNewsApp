package com.enesaksoy.kotlinnewsapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enesaksoy.kotlinnewsapp.R
import com.enesaksoy.kotlinnewsapp.databinding.DetailsFragmentBinding
import com.enesaksoy.kotlinnewsapp.viewmodel.DetailsViewModel

class DetailsFragment : Fragment(R.layout.details_fragment) {
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var viewmodel : DetailsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(DetailsViewModel::class.java)

        arguments?.let {
            val id = DetailsFragmentArgs.fromBundle(it).id
            viewmodel.getNew(id)
        }

        observeOn()

    }

    fun observeOn(){
        viewmodel.selectednew.observe(viewLifecycleOwner, Observer {
            binding.newUrl.text = it.url
        })
    }
}