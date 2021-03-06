package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import javax.sql.DataSource


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    var currentImageUrl: String?= null
private fun loadMeme(){



    // Instantiate the RequestQueue.
    progressBar.visibility=View.VISIBLE
    val queue = Volley.newRequestQueue(this)
    currentImageUrl = "https://meme-api.herokuapp.com/gimme"


    val jsonObjectRequest = JsonObjectRequest(

        Request.Method.GET ,currentImageUrl, null,
        Response.Listener { response ->
            currentImageUrl= response.getString("url")
            Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                     progressBar.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility=View.GONE
                    return false

                }
            }).into(memeImageView)




        },
        Response.ErrorListener {
            Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_LONG).show()
        })
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    queue.add(jsonObjectRequest)
}

    fun createNextButton(view: View) {
        loadMeme()
    }
    fun createShareButton(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey check out this meme $currentImageUrl")
        val chooser=Intent.createChooser(intent,"Share this meme using:-")
        startActivity(chooser)
    }
}