package com.github.yatatsu.samplecartclient

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    val api: AppApi by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://us-central1-pay-study-01.cloudfunctions.net/")
            .build()
            .create(AppApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        purchase_button.setOnClickListener { onClickPurchase() }
    }

    private fun onClickPurchase() {
        val amount = input_edit_amount.text.toString().toIntOrNull() ?: 0
        val email = input_edit_email.text.toString()
        // card
        val number = input_edit_card_number.text.toString()
        val month = input_edit_card_month.text.toString()
        val year = input_edit_card_year.text.toString()
        val cvc = input_edit_card_cvc.text.toString()

        if (amount > 0
            && email.matches(Patterns.EMAIL_ADDRESS.toRegex())
            && number.isNotEmpty()
            && month.isNotEmpty()
            && year.isNotEmpty()
            && cvc.isNotEmpty()
        ) {
            purchase_progress.visibility = View.VISIBLE
            purchase_button.visibility = View.GONE
            purchase(amount, email, number, month, year, cvc)
        } else {
            Snackbar.make(findViewById(android.R.id.content), "未入力の項目があります", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun purchase(
        amount: Int,
        email: String,
        number: String,
        month: String,
        year: String,
        cvc: String
    ) {
        // TODO: 1. カードからトークンを作る
        createCharge(amount, email)
    }

    private fun createCharge(amount: Int, email: String) {
        // TODO: 2. トークンと購入情報（金額とEメール）をサーバに送信する
        api.createCharge(
            amount = amount,
            email = email
        ).enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                showAlert(false, "購入に失敗しました")
            }

            override fun onResponse(call: Call<Unit>, response: retrofit2.Response<Unit>) {
                showAlert(true, "金額 $amount 円\nメールアドレス $email")
            }
        })
    }

    private fun showAlert(success: Boolean, message: String) {
        AlertDialog.Builder(this)
            .setTitle(
                if (success) {
                    "購入しました"
                } else {
                    "購入できませんでした"
                }
            )
            .setMessage(message)
            .setNegativeButton("OK", null)
            .create()
            .show()
        purchase_progress.visibility = View.GONE
        purchase_button.visibility = View.VISIBLE
    }
}
