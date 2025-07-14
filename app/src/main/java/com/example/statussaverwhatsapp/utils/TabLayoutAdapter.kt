package com.example.statussaverwhatsapp.utils

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.statussaverwhatsapp.presentation.fragments.ImagesFragment
import com.example.statussaverwhatsapp.presentation.fragments.VideosFragment

class TabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,
                       private val imagesList : ArrayList<Uri>, private val videoList : ArrayList<Uri>) : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0-> ImagesFragment.newInstance(imagesList)
            1->VideosFragment.newInstance(videoList)
            else -> {
                throw IllegalArgumentException("Invalid tab position")
            }
        }

    }
}


