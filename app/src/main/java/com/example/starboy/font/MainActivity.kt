package com.example.starboy.font

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val imgUrls = listOf("https://www.youredm.com/wp-content/uploads/2017/05/martin-garrix-in-the-name-of-love-artwork-1024x1024.jpg",
            "https://resources.wimpmusic.com/images/5c0dba0a/4d97/4601/82c1/fe51517d0fc5/1280x1280.jpg",
            "https://www.nationalgeographic.com/content/dam/adventure/rights-exempt/wild-horses-public-lands/part-1/wild-stallions-fighting1.adapt.1900.1.jpg",
            "https://resources.wimpmusic.com/images/808e2d19/3645/4130/a5ac/557cd8f92ad2/1280x1280.jpg",
            "https://casa-cruz.com/bulkupload_animal-wallpapers_Cats_Mountain-Lion-1.jpg",
            "https://www.sammobile.com/wp-content/uploads/2017/03/Galaxy-S4_03-540x540.png",
            "https://i.imgur.com/XQGRQKO.png",
            "https://vignette.wikia.nocookie.net/myat40/images/3/3d/The_1975_The_Sound_cover.jpg/revision/latest?cb=20160904230441",
            "https://www.sammobile.com/wp-content/uploads/2017/03/note3_fhd_14_droidviews.jpg",
            "https://vignette.wikia.nocookie.net/twenty-one-pilots/images/5/58/Blurryface.jpg/revision/latest?cb=20160105182338",
            "https://cdn01.cdn.justjaredjr.com/wp-content/uploads/2018/03/jake-snippets/jake-miller-snippets-all-tracks-on-new-album-01.jpg",
            "https://cdn01.cdn.justjaredjr.com/wp-content/uploads/2018/03/jake-snippets/jake-miller-snippets-all-tracks-on-new-album-09.jpg",
            "https://cdn01.cdn.justjaredjr.com/wp-content/uploads/2018/03/jake-snippets/jake-miller-snippets-all-tracks-on-new-album-07.jpg",
            "https://ih0.redbubble.net/image.190456686.0973/flat,800x800,075,t.u3.jpg"
    )
    private val neutralTarget = android.support.v7.graphics.Target.Builder()
            .setMinimumLightness(0.20f)
            .setTargetLightness(0.5f)
            .setMaximumLightness(0.8f)
            .setMinimumSaturation(0.1f)
            .setTargetSaturation(0.6f)
            .setMaximumSaturation(1f)
            .setPopulationWeight(0.18f)
            .setSaturationWeight(0.22f)
            .setLightnessWeight(0.60f)
            .setExclusive(false)
            .build()

    private var currimage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView.setOnClickListener { v ->
            currimage++
            if (currimage == imgUrls.size)
                currimage = 0

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mbg.setBackgroundColor(getColor(android.R.color.transparent))
                mtitleText.setTextColor(resources.getColor(android.R.color.primary_text_light))
                mbodyText.setTextColor(getColor(android.R.color.primary_text_light))
            }
            loadImage(imageView)
        }

        loadImage(imageView)
    }

    fun log(str: String) {
        Log.d(this.localClassName, str)
    }

    private fun loadImage(imgView: ImageView) {
        Glide.with(this)
                .load(imgUrls[currimage])
                .apply(RequestOptions().centerCrop())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        val bitmap = (resource as? BitmapDrawable)?.bitmap
                        bitmap?.let { getColour(it) }
                        return false
                    }
                })
                .into(imgView)
    }

    fun getColour(bitmap: Bitmap) {
        Palette.from(bitmap)
                .addTarget(neutralTarget)
                .generate { palette ->
                    palette.swatches.forEach { log("rgb = ${it.rgb} population = ${it.population}") }
                    palette.lightVibrantSwatch?.let { swatch ->
                        vlbg.setBackgroundColor(swatch.rgb)
                        vltitleText.setTextColor(swatch.titleTextColor)
                        vlbodyText.setTextColor(swatch.bodyTextColor)
                        vlbodyText.text = "Poppulation = ${swatch.population}"
                    } ?: let {
                        vlbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        vltitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        vlbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }
                    palette.vibrantSwatch?.let { swatch ->
                        vbg.setBackgroundColor(swatch.rgb)
                        vtitleText.setTextColor(swatch.titleTextColor)
                        vbodyText.setTextColor(swatch.bodyTextColor)
                        vbodyText.text = "Poppulation = ${swatch.population}"
                    } ?: let {
                        vbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        vtitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        vbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }
                    palette.darkVibrantSwatch?.let { swatch ->
                        vdbg.setBackgroundColor(swatch.rgb)
                        vdtitleText.setTextColor(swatch.titleTextColor)
                        vdbodyText.setTextColor(swatch.bodyTextColor)
                        vdbodyText.text = "Poppulation = ${swatch.population}"
                    } ?: let {
                        vdbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        vdtitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        vdbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }

                    palette.lightMutedSwatch?.let { swatch ->
                        mlbg.setBackgroundColor(swatch.rgb)
                        mltitleText.setTextColor(swatch.titleTextColor)
                        mlbodyText.setTextColor(swatch.bodyTextColor)
                        mlbodyText.text = "Poppulation = ${swatch.population}"
                    } ?: let {
                        mlbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        mltitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        mlbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }
                    palette.mutedSwatch?.let { swatch ->
                        mbg.setBackgroundColor(swatch.rgb)
                        mtitleText.setTextColor(swatch.titleTextColor)
                        mbodyText.setTextColor(swatch.bodyTextColor)
                        mbodyText.text = "Poppulation = ${swatch.population}"
                    } ?: let {
                        mbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        mtitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        mbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }
                    palette.darkMutedSwatch?.let { swatch ->
                        mdbg.setBackgroundColor(swatch.rgb)
                        mdtitleText.setTextColor(swatch.titleTextColor)
                        mdbodyText.setTextColor(swatch.bodyTextColor)
                        mdbodyText.text = "Poppulation = ${swatch.population}"
                    } ?: let {
                        mdbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        mdtitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        mdbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }

                    palette.getSwatchForTarget(neutralTarget)?.let { swatch ->
                        ctbg.setBackgroundColor(swatch.rgb)
                        cttitleText.setTextColor(swatch.titleTextColor)
                        ctbodyText.setTextColor(swatch.bodyTextColor)
                        ctbodyText.text = "Poppulation = ${swatch.population}"
                    } ?: let {
                        ctbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        cttitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        ctbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }

                    palette.dominantSwatch?.let { swatch ->
                        ddbg.setBackgroundColor(swatch.rgb)
                        ddtitleText.setTextColor(swatch.titleTextColor)
                        ddbodyText.setTextColor(swatch.bodyTextColor)
                        ddbodyText.text = "Poppulation = ${swatch.population}"

                        val window = window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.statusBarColor = swatch.rgb

                    } ?: let {
                        ddbg.setBackgroundColor(resources.getColor(android.R.color.transparent))
                        ddtitleText.setTextColor(resources.getColor(android.R.color.transparent))
                        ddbodyText.setTextColor(resources.getColor(android.R.color.transparent))
                    }
                }
    }
}
