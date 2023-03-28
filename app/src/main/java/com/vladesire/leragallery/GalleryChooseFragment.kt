package com.vladesire.leragallery

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.vladesire.leragallery.databinding.FragmentGalleryChooseBinding

private const val TAG = "GalleryChooseFragment"

class GalleryChooseFragment : Fragment() {

    private var _binding: FragmentGalleryChooseBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Cannot access binding" }

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

        val repository = PhotoRepository.get()

        val photos = repository.getPhotos(400)
        binding.recyclerView.adapter = PhotoListAdapter(photos) { repository.savePhoto(it) }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}