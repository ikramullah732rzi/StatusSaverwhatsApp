package com.example.statussaverwhatsapp.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.statussaverwhatsapp.databinding.ActivityMainBinding
import com.example.statussaverwhatsapp.utils.SHARED_PREFERENCE_NAME
import com.example.statussaverwhatsapp.utils.TabLayoutAdapter
import com.example.statussaverwhatsapp.utils.URI_NAME
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MainActivity : AppCompatActivity() {
    private val folderLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val uri = result.data?.data
                if (uri != null) {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                    val sharedPreferences =
                        getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)
                    sharedPreferences.edit().putString(URI_NAME, uri.toString()).apply()
                    Toast.makeText(this, "Access  Granted", Toast.LENGTH_SHORT).show()
                    checkPermissionGranted()
                } else {
                    Toast.makeText(this, "Access Not Granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var tabLayoutAdapter: TabLayoutAdapter
    private  val imageList = ArrayList<Uri>()
    private  val videoList = ArrayList<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkingSaveData()


    }

    private fun checkingSaveData() {
        tabLayoutAdapter = TabLayoutAdapter(supportFragmentManager, lifecycle, imageList, videoList)
        binding.apply {
            tabLayout.addTab(tabLayout.newTab().setText("Images"))
            tabLayout.addTab(tabLayout.newTab().setText("Videos"))
             viewPager22.adapter=tabLayoutAdapter
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewPager22.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
            viewPager22.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }
        checkPermissionGranted()

        binding.btnAllowMe.setOnClickListener {
            openDacoment()
        }
    }

    private fun openDacoment() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)

            val waUri =
                Uri.parse("content://com.android.externalstorage.documents/tree/primary:Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses")

            putExtra(DocumentsContract.EXTRA_INITIAL_URI, waUri)
        }
        folderLauncher.launch(intent)

    }

    private fun loadMedia() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)
        val stringUri = sharedPreferences.getString(URI_NAME, null)
        if (stringUri != null) {
            val uri = Uri.parse(stringUri)
            val documentfile = DocumentFile.fromTreeUri(this, uri)
            documentfile?.let { folder ->

                for (file in folder.listFiles()) {
                    val mimeType = contentResolver.getType(file.uri)
                    when {
                        mimeType?.startsWith("image") == true -> imageList.add(file.uri)
                        mimeType?.startsWith("video") == true -> videoList.add(file.uri)
                    }
                }

                imageList.forEach {
                    Log.w("loading", "________${it.path} ")
                }
            }
        }
    }

    private fun checkPermissionGranted() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)
        val stringUri = sharedPreferences.getString(URI_NAME, null)
        if (stringUri != null) {
            binding.btnAllowMe.visibility = View.GONE
            binding.viewPager22.visibility = View.VISIBLE
            loadMedia()
        } else {
            binding.btnAllowMe.visibility = View.VISIBLE
            binding.viewPager22.visibility = View.GONE
        }
    }
}