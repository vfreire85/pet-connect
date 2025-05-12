package com.senacgrupovinteecinco.pet_connect

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class AdminActivity : Activity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val deleteUserField = findViewById<EditText>(R.id.delete_username)
        val deleteUserButton = findViewById<Button>(R.id.delete_user_button)

        deleteUserButton.setOnClickListener {
            val usernameToDelete = deleteUserField.text.toString()
            deleteUser(usernameToDelete)
        }
    }

    private fun deleteUser(username: String) {
        val url = "http://164.152.51.239/api.php"
        val requestBody = FormBody.Builder()
            .add("usuario", username)
            .build()

        val request = Request.Builder()
            .url(url)
            .delete(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@AdminActivity, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val jsonResponse = JSONObject(responseBody ?: "")
                val status = jsonResponse.getString("status")

                runOnUiThread {
                    if (status == "success") {
                        Toast.makeText(this@AdminActivity, "Usuário deletado com sucesso!", Toast.LENGTH_LONG).show()
                    } else {
                        val message = jsonResponse.getString("message")
                        Toast.makeText(this@AdminActivity, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}