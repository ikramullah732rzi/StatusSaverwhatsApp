package com.example.statussaverwhatsapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.statussaverwhatsapp.databinding.ImageSampleBinding
import com.example.statussaverwhatsapp.presentation.activities.DetailActivity
import java.io.File
import java.io.FileOutputStream

class ImageViewAdapter(private val context: Context, private val imageList : ArrayList<Uri>): RecyclerView.Adapter<ImageViewAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val view = ImageSampleBinding.inflate(LayoutInflater.from(context),parent,false)
        return  viewHolder(view)
    }

    override fun getItemCount(): Int {
       return imageList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val uri = imageList[position]
        Glide.with(context).load(uri).into(holder.binding.statusImageview);
        holder.itemView.setOnClickListener{
              context.startActivity(Intent(context, DetailActivity::class.java).apply {
                  putExtra(IMAGE_URI,uri.toString())
              })
        }
        holder.binding.saveButton.setOnClickListener {
            val filename = uri.lastPathSegment?.substringAfterLast("/") ?: "status_file.jpg"
            val success = saveStatusToDownloads(uri,filename)
            if(success){
                Toast.makeText(context, "Download Successful", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveStatusToDownloads(uri: Uri,filename:String): Boolean{
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val desDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"SaveStatus")
            if(!desDir.exists()) desDir.mkdirs()
            val file = File(desDir,filename)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            true
        }catch (e:Exception){
            false
        }
    }
    inner class viewHolder(val binding: ImageSampleBinding): RecyclerView.ViewHolder(binding.root)
}