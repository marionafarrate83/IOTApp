package com.example.iotapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.iotapp.databinding.ActivityActionsBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ActionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActionsBinding

    //updated commit JMPN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActionsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val extras = intent.extras
        val actionAspersores = extras?.getString("actionAspersores") ?: "accion no especificada"
        val actionVentilas = extras?.getString("actionVentilas") ?: "accion no especificada"
        val actionCasasombra = extras?.getString("actionCasasombra") ?: "accion no especificada"

        binding.tvVentilas.append("Estado Ventilas: ${actionVentilas}\n")
        binding.tvAspersores.append("Estado Aspersores: ${actionAspersores}\n")
        binding.tvCasasombra.append("Estado Casasombra: ${actionCasasombra}\n")

        if (actionVentilas == "open"){
            val urlGif = "https://tartarusstorage.blob.core.windows.net/tartarusblob1/ventilaOpen.gif"
            val imageView: ImageView = findViewById(R.id.ivVentilas)
            val uri = Uri.parse(urlGif)
            Glide.with(applicationContext).load(uri).into(imageView)
        } else {
            Picasso.get()
                .load("https://tartarusstorage.blob.core.windows.net/tartarusblob1/ventilaClosed.jpg")
                .into(binding.ivVentilas, object : Callback {
                    override fun onSuccess() {
                        Log.d("Picasso", "success")
                    }
                    override fun onError(e: Exception?) {
                        Log.d("Picasso", "error")
                    }
                })
        }

        if (actionAspersores == "open"){
            val urlGif = "https://tartarusstorage.blob.core.windows.net/tartarusblob1/aspersorOpen.gif"
            val imageView: ImageView = findViewById(R.id.ivAspersores)
            val uri = Uri.parse(urlGif)
            Glide.with(applicationContext).load(uri).into(imageView)
        } else {
            Picasso.get()
                .load("https://tartarusstorage.blob.core.windows.net/tartarusblob1/aspersorClosed.jpg")
                .into(binding.ivAspersores, object : Callback {
                    override fun onSuccess() {
                        Log.d("Picasso", "success")
                    }
                    override fun onError(e: Exception?) {
                        Log.d("Picasso", "error")
                    }
                })
        }

        if (actionCasasombra == "open"){
            val urlGif = "https://tartarusstorage.blob.core.windows.net/tartarusblob1/rooftopOpen.gif"
            val imageView: ImageView = findViewById(R.id.ivCasasombra)
            val uri = Uri.parse(urlGif)
            Glide.with(applicationContext).load(uri).into(imageView)
        } else {
            Picasso.get()
                .load("https://tartarusstorage.blob.core.windows.net/tartarusblob1/rooftopClosed.jpeg")
                .into(binding.ivCasasombra, object : Callback {
                    override fun onSuccess() {
                        Log.d("Picasso", "success")
                    }
                    override fun onError(e: Exception?) {
                        Log.d("Picasso", "error")
                    }
                })
        }


        val boton = this.findViewById<View>(R.id.backBtn) as Button
        boton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            this.startActivity(intent)
        }



    }
}