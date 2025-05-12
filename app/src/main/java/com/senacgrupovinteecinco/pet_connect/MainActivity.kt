package com.senacgrupovinteecinco.pet_connect

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : Activity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val usuario = usernameField.text.toString()
            val senha = passwordField.text.toString()
            login(usuario, senha)
        }
    }

    private fun login(usuario: String, senha: String) {
        val url = "http://164.152.51.239/api.php"
        val requestBody = FormBody.Builder()
            .add("usuario", usuario)
            .add("senha", senha)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP_ERROR", "Erro na conexão: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("HTTP_RESPONSE", "Resposta do servidor: $responseBody")
            }
        })

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()


                val jsonResponse = JSONObject(responseBody ?: "")
                val status = jsonResponse.getString("status")

                runOnUiThread {
                    if (status == "success") {
                        val intent = if (usuario == "admin") {
                            Intent(this@MainActivity, AdminActivity::class.java)
                        } else {
                            Intent(this@MainActivity, HomeActivity::class.java)
                        }
                        intent.putExtra("usuario", usuario)
                        startActivity(intent)
                    } else {
                        val message = jsonResponse.getString("message")
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    }
                }


            }
        })
    }
}