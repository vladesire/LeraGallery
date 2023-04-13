package com.vladesire.leragallery

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.vladesire.leragallery.databinding.FragmentMainMenuBinding
import kotlinx.coroutines.launch

private const val TAG = "MainMenuFragment"

class MainMenuFragment: Fragment() {
    private var _binding: FragmentMainMenuBinding? = null

    private val binding
        get() = checkNotNull(_binding) { "Cannot access binding" }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.galleryChoose.setOnClickListener {
            findNavController().navigate(
                MainMenuFragmentDirections.choosePhotosGallery()
            )
        }

        binding.gridButton.setOnClickListener {
            findNavController().navigate(
                MainMenuFragmentDirections.showPhotosGrid()
            )
        }

        binding.eraseButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                SavedPhotosRepository.get().eraseSavedPhotos() {
                    Toast.makeText(context, "Erased all photos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}