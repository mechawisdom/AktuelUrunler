package com.mechadev.indirim.aktuel.urunler.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kaopiz.kprogresshud.KProgressHUD
import com.mechadev.indirim.aktuel.urunler.R
import com.mechadev.indirim.aktuel.urunler.adapter.ImageAdapter
import com.mechadev.indirim.aktuel.urunler.adapter.RecentlyAddedAdapter
import com.mechadev.indirim.aktuel.urunler.databinding.FragmentHomeBinding
import com.mechadev.indirim.aktuel.urunler.util.CenterZoomLayoutManager
import com.mechadev.indirim.aktuel.urunler.util.ZoomLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var recentlyAddedAdapter: RecentlyAddedAdapter
    private lateinit var hud: KProgressHUD


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (homeViewModel.recentlyAddedList.value.isNullOrEmpty()) {
            homeViewModel.getRecentlyAddedListLocal()
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                val snackbarLayout = binding.loadFrameLayout
                snackbarLayout.removeAllViews()
                val inflater = requireActivity().layoutInflater
                val loadingLayout = inflater.inflate(R.layout.loading_layout, snackbarLayout, false)

                snackbarLayout.addView(loadingLayout)
                snackbarLayout.isVisible = true
            } else if (binding.loadFrameLayout.isVisible) {
                binding.loadFrameLayout.removeAllViews()
                binding.loadFrameLayout.isVisible = false
            }
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.e("TAG", "" + it)
        }


        recentlyAddedAdapter = RecentlyAddedAdapter(requireContext())
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRecyclerView.setItemViewCacheSize(30)
        binding.homeRecyclerView.adapter = recentlyAddedAdapter

        homeViewModel.recentlyAddedList.observe(viewLifecycleOwner) {
            Log.e("TAG", "" + it)
            recentlyAddedAdapter.setAddedList(it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.getRecentlyAddedListLocal()
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(
                    requireContext(),
                    getString(R.string.update_completed),
                    Toast.LENGTH_SHORT
                ).show()
                binding.swipeRefreshLayout.isRefreshing = false
            }, 1500)
        }

        /*
        binding.homeRecyclerView.setItemViewCacheSize(30)
        val images = listOf(
            "https://imageupscaler.com/wp-content/uploads/2024/07/deblured-cutty-fox.jpg",
            "https://cdn.wallpaper.tn/large/4K-Wallpaper-Nature-56350.jpg",
            "https://media.istockphoto.com/id/1602458519/photo/colorful-powder-explosion-on-white-background.jpg?s=612x612&w=0&k=20&c=qP4wiTwHShIIIi4pfx2EDO-ynQ1rnt3meO2o3K47n9A=",
            "https://replicate.delivery/pbxt/JF3foGR90vm9BXSEXNaYkaeVKHYbJPinmpbMFvRtlDpH4MMk/out-0-1.png",
            "https://storage.googleapis.com/dara-c1b52.appspot.com/daras_ai/media/2a9500aa-74f9-11ee-8902-02420a000165/gooey.ai%20-%20A%20beautiful%20anime%20drawing%20of%20a%20smilin...ibli%20ponyo%20anime%20excited%20anime%20saturated%20colorsn.png",
            "https://www.wearegecko.co.uk/media/50316/mountain-3.jpg",
            "https://image-5.uhdpaper.com/wallpaper/lake-reflection-forest-nature-mountain-scenery-4k-wallpaper-uhdpaper.com-104@5@a.jpg",
            "https://www.adobe.com/acrobat/hub/media_173d13651460eb7e12c0ef4cf8410e0960a20f0ee.jpeg?width=750&format=jpeg&optimize=medium"
            // ...
        )
        binding.homeRecyclerView.layoutManager = CenterZoomLayoutManager(requireContext())
        binding.homeRecyclerView.adapter = ImageAdapter(images)

        val webView: WebView= view.findViewById(R.id.webView)


        // WebView ayarlarını yapalım
        webView.settings.apply {
            javaScriptEnabled = true   // JavaScript'i etkinleştir
            setSupportZoom(true)      // Zoom desteği
            builtInZoomControls = true // Dahili zoom kontrollerini etkinleştir
            displayZoomControls = false // Zoom kontrollerini gizle
        }


        val htmlContent = buildString {
            append("<html><head>")
            append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=20.0, user-scalable=yes\" />")
//            append("<style>")
//            append("body { margin: 0; padding: 0; }")  // Sayfa kenar boşluklarını kaldır
//            append("img { width: 100%; height: auto; display: block; margin: 0; padding: 0; }") // Resimlerin kenar boşluklarını kaldır
//            append("</style>")
            append("</head><body>")
            images.forEach { url ->
                append("<img src=\"$url\" width=\"100%\" height=\"auto\" style=\"margin-bottom: 5px;\" />")
            }
            append("</body></html>")
        }

        webView.loadData(htmlContent, "text/html", "UTF-8")
*/
    }

    override fun onResume() {
        super.onResume()
        recentlyAddedAdapter.updateSharedData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}