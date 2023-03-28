package com.vladesire.leragallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.vladesire.leragallery.databinding.FragmentGridBinding

class GridFragment : Fragment() {

    private var _binding: FragmentGridBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "FragmentGridBinding is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGridBinding.inflate(inflater, container, false)
        binding.gridRecyclerView.layoutManager = GridLayoutManager(context, 2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PhotoRepository.get()

        val photos = repository.getSavedPhotos()

        binding.gridRecyclerView.adapter = PhotoListAdapter(photos)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}