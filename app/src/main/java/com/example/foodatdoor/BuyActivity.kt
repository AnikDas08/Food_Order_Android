package com.example.foodatdoor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.foodatdoor.databinding.ActivityBuyBinding
import com.help5g.uddoktapaysdk.UddoktaPay
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder

class BuyActivity : AppCompatActivity() {
    lateinit var binding:ActivityBuyBinding

    private val API_KEY = "982d381360a69d419689740d9f2e26ce36fb7a50"
    private val CHECKOUT_URL = "https://sandbox.uddoktapay.com/api/checkout-v2"
    private val VERIFY_PAYMENT_URL = "https://sandbox.uddoktapay.com/api/verify-payment"
    private val REDIRECT_URL = "https://your-site.com"
    private val CANCEL_URL = "https://your-site.com"
    private var storedFullName: String? = null
    private var storedEmail: String? = null
    private var storedAmount: String? = null
    private var storedInvoiceId: String? = null
    private var storedPaymentMethod: String? = null
    private var storedSenderNumber: String? = null
    private var storedTransactionId: String? = null
    private var storedDate: String? = null
    private var storedFee: String? = null
    private var storedChargedAmount: String? = null

    private var storedMetaKey1: String? = null
    private var storedMetaValue1: String? = null

    private var storedMetaKey2: String? = null
    private var storedMetaValue2: String? = null

    private var storedMetaKey3: String? = null
    private var storedMetaValue3: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val totalprice=intent.getStringExtra("totalPrice")?:"0.0"
        binding.totalamount.setText(totalprice)




        binding.OrderPlace.setOnClickListener(View.OnClickListener {



            val name=binding.nameEdit.text.toString()
            val address=binding.user.text.toString()
            val phone=binding.userphone.text.toString()
            val amount=binding.totalamount.text.toString()


            binding.ConstantLayout.visibility=View.GONE
            binding.WebViewLayout.visibility=View.VISIBLE

            val metadataMap = mutableMapOf(
                "CustomMetaData1" to "Meta Value 1",
                "CustomMetaData2" to "Meta Value 2",
                "CustomMetaData3" to "Meta Value 3"
            )

            val paymentCallback = object : UddoktaPay.PaymentCallback {
                override fun onPaymentStatus(
                    status: String,
                    fullName: String,
                    email: String,
                    amount: String,
                    invoiceId: String,
                    paymentMethod: String,
                    senderNumber: String,
                    transactionId: String,
                    date: String,
                    metadataValues: Map<String, String>,
                    fee: String,
                    chargeAmount: String
                ) {
                    // Store payment details
                    storedFullName = fullName
                    storedEmail = email
                    storedAmount = amount
                    storedInvoiceId = invoiceId
                    storedPaymentMethod = paymentMethod
                    storedSenderNumber = senderNumber
                    storedTransactionId = transactionId
                    storedDate = date
                    storedFee = fee
                    storedChargedAmount = chargeAmount

                    runOnUiThread {
                        // Clear previous metadata values to avoid duplication
                        storedMetaKey1 = null
                        storedMetaValue1 = null
                        storedMetaKey2 = null
                        storedMetaValue2 = null
                        storedMetaKey3 = null
                        storedMetaValue3 = null

                        // Iterate through metadata map and store key-value pairs
                        metadataValues.forEach { (metadataKey, metadataValue) ->
                            when (metadataKey) {
                                "CustomMetaData1" -> {
                                    storedMetaKey1 = metadataKey
                                    storedMetaValue1 = metadataValue
                                }
                                "CustomMetaData2" -> {
                                    storedMetaKey2 = metadataKey
                                    storedMetaValue2 = metadataValue
                                }
                                "CustomMetaData3" -> {
                                    storedMetaKey3 = metadataKey
                                    storedMetaValue3 = metadataValue
                                }
                            }
                        }

                        // Update UI based on payment status
                        when (status) {
                            "COMPLETED" -> {
                                binding.ConstantLayout.visibility = View.VISIBLE
                                binding.WebViewLayout.visibility = View.GONE
                                Toast.makeText(this@BuyActivity, "Payment status: Completed", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            "PENDING" -> {
                                binding.ConstantLayout.visibility = View.VISIBLE
                                binding.WebViewLayout.visibility = View.GONE
                                Toast.makeText(this@BuyActivity, "Payment status: Pending", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            "ERROR" -> {
                                // Handle payment error case
                            }
                        }
                    }
                }
            }

            val uddoktapay = UddoktaPay(binding.webView, paymentCallback)
            uddoktapay.loadPaymentForm(API_KEY, name, address, amount, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap)


        })

    }
}