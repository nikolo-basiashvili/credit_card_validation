package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var cardNumView : TextView
    private lateinit var cardHolderView : TextView
    private lateinit var expiryDateView : TextView

    private lateinit var cardNumEdit : EditText
    private lateinit var nameEdit : EditText
    private lateinit var expEdit : EditText
    private lateinit var cvvEdit : EditText

    private lateinit var img : ImageView
    private lateinit var button : Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardNumView = findViewById(R.id.cardNumView)
        cardHolderView = findViewById(R.id.cardHolderView)
        expiryDateView = findViewById(R.id.expDateView)

        cardNumEdit = findViewById(R.id.cardNumberEdit)
        nameEdit = findViewById(R.id.nameEdit)
        expEdit = findViewById(R.id.expDateEdit)
        cvvEdit = findViewById(R.id.cvvEdit)

        img = findViewById(R.id.imageView)
        button = findViewById(R.id.addBtn)

        cardNumEdit.addTextChangedListener {
            if(cardNumEdit.text.isNotEmpty()) {
                val cardNumStr = cardNumEdit.text.toString()
                if (cardNumStr[0] == '4') {
                    img.visibility = ImageView.VISIBLE
                    img.setBackgroundResource(R.drawable.visa)
                }else if (cardNumStr[0] == '5') {
                    img.visibility = ImageView.VISIBLE
                    img.setBackgroundResource(R.drawable.mastercard)
                }else{
                    img.visibility = ImageView.INVISIBLE
                }
                if(cardNumStr.length <= 16) {
                    cardNumView.text = cardNumStr.chunked(4).joinToString(separator = "  ")
                }
            }else{
                img.visibility = ImageView.INVISIBLE
                cardNumView.text = ""
            }
        }

        nameEdit.addTextChangedListener {
            cardHolderView.text = nameEdit.text.toString().uppercase()
        }

        expEdit.addTextChangedListener {
            if(expEdit.length() <= 5) {
                expiryDateView.text = expEdit.text
            }
        }

        button.setOnClickListener {
            var expired: Boolean
            try {
                val simpleDateFormat = SimpleDateFormat("MM/yy")
                simpleDateFormat.isLenient = false
                val expiry = simpleDateFormat.parse(expEdit.text.toString())
                expired = expiry?.before(Date()) == true
            }catch(e: java.lang.Exception){
                expired = true
            }
            if(expired){
                Toast.makeText(this, "Insert Valid Expiry Date!", Toast.LENGTH_SHORT).show()
            }else if(cardNumEdit.text.length != 16){
                Toast.makeText(this, "Insert Correct Card Number!", Toast.LENGTH_SHORT).show()
            }else if(cvvEdit.text.length != 3){
                Toast.makeText(this, "Insert Correct CVV Number!", Toast.LENGTH_SHORT).show()
            }else if(nameEdit.text.isEmpty()){
                Toast.makeText(this, "Insert Card Holders Name!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Card Saved Successfully!", Toast.LENGTH_SHORT).show()
                expEdit.setText("")
                cvvEdit.setText("")
                cardNumEdit.setText("")
                nameEdit.setText("")
            }
        }
    }
}