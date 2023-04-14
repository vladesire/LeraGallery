package com.vladesire.leragallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.vladesire.leragallery.databinding.FragmentMainMenuBinding
import com.vladesire.leragallery.databinding.FragmentSwiperBinding

class SwiperFragment : Fragment() {

    private var _binding: FragmentSwiperBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "SwiperFragment binding is null" }

    private val swiperViewModel: SwiperViewModel by viewModels {
        SwiperViewModelFactory(SavedPhotosRepository.get())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSwiperBinding.inflate(inflater, container, false)
        return binding.root
    }


}