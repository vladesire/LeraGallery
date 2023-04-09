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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.vladesire.leragallery.databinding.FragmentGalleryChooseBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "GalleryChooseFragment"

class GalleryChooseFragment : Fragment() {

    private var _binding: FragmentGalleryChooseBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Cannot access binding" }

    private val galleryChooseViewModel: GalleryChooseViewModel by viewModels {
        GalleryChooseViewModelFactory(PhotoRepository.get())
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Log.i(TAG, "Permission declined")
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryChooseBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (
            requireContext().checkSelfPermission("android.permission.READ_MEDIA_IMAGES") !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch("android.permission.READ_MEDIA_IMAGES")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                galleryChooseViewModel.photos.collect { photos ->
                    Toast.makeText(context, "Saved photos size: ${photos.size}", Toast.LENGTH_SHORT).show()

                    binding.recyclerView.adapter = PhotoListAdapter(photos) { photo ->

                        photo.isChosen = true

                        viewLifecycleOwner.lifecycleScope.launch {
                            galleryChooseViewModel.savePhoto(Photo(uri = photo.uri))
                        }

                    }

                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}