package com.vladesire.leragallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.vladesire.leragallery.databinding.FragmentGridBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GridFragment : Fragment() {

    private var _binding: FragmentGridBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "FragmentGridBinding is null" }

    private val selectedPhotosViewModel: SelectedPhotosViewModel by viewModels {
        SelectedPhotosViewModelFactory(PhotoRepository.get())
    }

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                selectedPhotosViewModel.savedPhotos.collect {
                    binding.gridRecyclerView.adapter = PhotoListAdapter(it)
                }

            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}