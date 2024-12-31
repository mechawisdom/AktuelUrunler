package com.mechadev.indirim.aktuel.urunler.ui.detail

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView


import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.FileOutputStream
import java.io.IOException
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mechadev.indirim.aktuel.urunler.R
import com.mechadev.indirim.aktuel.urunler.databinding.ActivityDetailBinding
import com.mechadev.indirim.aktuel.urunler.ui.bottomsheet.ShoppingListBottomSheet
import com.mechadev.indirim.aktuel.urunler.util.RevealAnimation
import com.mechadev.indirim.aktuel.urunler.util.SharedPreferencesHelper
import com.mechadev.indirim.aktuel.urunler.util.SharedPreferencesHelper.Companion.FAVORITES
import com.mechadev.indirim.aktuel.urunler.util.extensions.loadImage
import com.mechadev.indirim.aktuel.urunler.util.extensions.loadPhotoView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import java.io.File

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var revealAnimation: RevealAnimation
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        revealAnimation = RevealAnimation(binding.detailRootLayout, intent, this)

        val receivedItemID = intent.getIntExtra("id", 0)
        val receivedPageSize = intent.getIntExtra("page_size", 0)
        val receivedMarketString = intent.getStringExtra("market")
        val receivedImageString = intent.getStringExtra("image")
        val receivedImagePathString = intent.getStringExtra("image_path")

        val file = File(filesDir, receivedImagePathString.toString() + "_default.jpg")

        binding.headerText.text = receivedMarketString
        val deviceHeight = Resources.getSystem().displayMetrics.heightPixels

        if (receivedPageSize < 2) {
            binding.detailPhotoView.scaleType = ImageView.ScaleType.FIT_CENTER
        }
        val options = RequestOptions()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_placeholder_full)
            .error(R.drawable.ic_placeholder_full)

        if (file.exists()) {
            Glide.with(applicationContext)
                .load(file)
                .apply(options)
                .into(binding.detailPhotoView)
            Toast.makeText(applicationContext, "file", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "download", Toast.LENGTH_SHORT).show()
            downloadImageAndSave(receivedImageString.toString(), file, options)
        }


        sharedPreferencesHelper = SharedPreferencesHelper(applicationContext)

        val favoriteSet = sharedPreferencesHelper.getStringSet(FAVORITES).toMutableSet()
        var isFavorited = favoriteSet.contains(receivedItemID.toString())

        updateBookmarkIcon(isFavorited)

        binding.detailBookmarkImageView.setOnClickListener {
            if (isFavorited) {
                favoriteSet.remove(receivedItemID.toString())
                sharedPreferencesHelper.saveStringSet(FAVORITES, favoriteSet)
                Toast.makeText(applicationContext, "Kaydedilenlerden silindi", Toast.LENGTH_SHORT)
                    .show()
            } else {
                favoriteSet.add(receivedItemID.toString())
                sharedPreferencesHelper.saveStringSet(FAVORITES, favoriteSet)
                Toast.makeText(applicationContext, "Kaydedildi", Toast.LENGTH_SHORT).show()
            }

            isFavorited = !isFavorited
            updateBookmarkIcon(isFavorited)
        }

        binding.detailFab.setOnClickListener {
            val bottomSheet = ShoppingListBottomSheet()
//            val bottomSheetDialog = BottomSheetDialog(this, R.style.ModalBottomSheetDialog)
//            bottomSheetDialog.setContentView(view)
//            bottomSheetDialog.show()


            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                revealAnimation.unRevealActivity()
            }
        })


        binding.detailBackImageView.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun updateBookmarkIcon(isFavorited: Boolean) {
        binding.detailBookmarkImageView.setImageResource(
            if (isFavorited) R.drawable.ic_ui_bookmark_bold else R.drawable.ic_ui_bookmark
        )
    }

    private fun downloadImageAndSave(url: String, file: File, options: RequestOptions) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .build()

                val response: Response = client.newCall(request).execute()

                val inputStream = response.body?.byteStream()
                if (inputStream != null) {
                    val outputStream = FileOutputStream(file)

                    val buffer = ByteArray(1024)
                    var byteRead: Int
                    while (inputStream.read(buffer).also { byteRead = it } != -1) {
                        outputStream.write(buffer, 0, byteRead)
                    }

                    inputStream.close()
                    outputStream.close()

                    withContext(Dispatchers.Main) {
                        Glide.with(applicationContext)
                            .load(file)
                            .apply(options)
                            .into(binding.detailPhotoView)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Glide.with(applicationContext)
                            .load(url)
                            .apply(options)
                            .into(binding.detailPhotoView)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Glide.with(applicationContext)
                        .load(url)
                        .apply(options)
                        .into(binding.detailPhotoView)
                }
            }
        }
    }
}