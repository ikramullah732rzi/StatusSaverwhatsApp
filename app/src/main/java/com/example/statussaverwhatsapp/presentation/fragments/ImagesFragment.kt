package com.example.statussaverwhatsapp.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.statussaverwhatsapp.R
import com.example.statussaverwhatsapp.databinding.FragmentImagesBinding
import com.example.statussaverwhatsapp.utils.IMAGES_LIST
import com.example.statussaverwhatsapp.utils.ImageViewAdapter


class ImagesFragment : Fragment() {

    private var imageList = ArrayList<Uri>()
    private lateinit var imageViewAdapter: ImageViewAdapter
    private val binding by lazy {
        FragmentImagesBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageList = it.getParcelableArrayList(IMAGES_LIST)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("msg", "onViewCreated: ${imageList.size}")
        imageViewAdapter = ImageViewAdapter(requireContext(),imageList)
        binding.imagesRecyclerView.adapter = imageViewAdapter
        binding.imagesRecyclerView.layoutManager=GridLayoutManager(requireContext(),3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(imagesList: ArrayList<Uri>): ImagesFragment {
            val fragment = ImagesFragment()
            val args = Bundle()
            args.putParcelableArrayList(IMAGES_LIST, imagesList)
            fragment.arguments = args
            return fragment
        }
    }
}