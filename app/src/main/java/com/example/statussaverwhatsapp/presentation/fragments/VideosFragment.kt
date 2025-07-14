package com.example.statussaverwhatsapp.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.statussaverwhatsapp.R
import com.example.statussaverwhatsapp.databinding.FragmentVideosBinding
import com.example.statussaverwhatsapp.utils.ImageViewAdapter
import com.example.statussaverwhatsapp.utils.VIDEO_LIST
import com.example.statussaverwhatsapp.utils.VideoViewAdapter

class VideosFragment : Fragment() {

    private var videoList  = ArrayList<Uri>()
    private lateinit var videoViewAdapter: VideoViewAdapter
    private val binding by lazy {
        FragmentVideosBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        videoList= it.getParcelableArrayList(VIDEO_LIST)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("msg", "onViewCreated: ${videoList.size}")
        videoViewAdapter = VideoViewAdapter(requireContext(),videoList)
        binding.videoRecyclerview.adapter = videoViewAdapter
        binding.videoRecyclerview.layoutManager= GridLayoutManager(requireContext(),3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(videos: ArrayList<Uri>): VideosFragment {
            val fragment = VideosFragment()
            val args = Bundle()
            args.putParcelableArrayList(VIDEO_LIST, videos)
            fragment.arguments = args
            return fragment
        }
    }
}