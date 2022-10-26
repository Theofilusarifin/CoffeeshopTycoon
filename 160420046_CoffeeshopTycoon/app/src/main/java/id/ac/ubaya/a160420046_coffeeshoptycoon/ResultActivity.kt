package id.ac.ubaya.a160420046_coffeeshoptycoon

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.txtCoffeeRevenue
import kotlinx.android.synthetic.main.activity_result.txtProfit
import kotlinx.android.synthetic.main.activity_result.txtTotalExpenses
import kotlinx.android.synthetic.main.activity_result.txtTotalRevenue

class ResultActivity : AppCompatActivity() {
    override fun onBackPressed() {
        Toast.makeText(this, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }

    fun ResetDaily() {
        Global.weatherRandomIndex = 0

        Global.cupprice = 0
        Global.cupnum = 0
        Global.totalExpenses = 0

        Global.coffeSold = 0
        Global.sellPrice = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        txtBankrupt.setText("")

        var totalRevenue = Global.coffeSold * Global.sellPrice
        var profit = totalRevenue - Global.totalExpenses

        //        Display Day Number
        txtDay3.text = "Result Day " + Global.dayNumber.toString()

        var strSellPrice = "%,d".format(Global.sellPrice).replace(",", ".")
        txtCoffeeRevenue.setText(Global.coffeSold.toString() + " cup of coffee\n@IDR "+ strSellPrice)

        var strTotalRevenue = "%,d".format(totalRevenue).replace(",", ".")
        txtTotalRevenue.text = "IDR $strTotalRevenue"

        txtCoffeeRevenue.setText(Global.coffeSold.toString() + " cup of coffee\n@IDR "+ strSellPrice)

//        Display Total Expenses
        var strTotalExpenses = "%,d".format(Global.totalExpenses).replace(",", ".")
        txtTotalExpenses.text = "IDR $strTotalExpenses"

        var strProfit = "%,d".format(profit).replace(",", ".")
        txtProfit.text = "IDR $strProfit"

        if (Global.playerBalance + profit < 100700){
            txtBankrupt.setText("Due to inssuficient balance to start a new day, \nLet's start a new game!")
            btnStartNew.setText("PLAY NEW GAME")
        }
        else{
            txtBankrupt.setText("Let's start a new day!")
            btnStartNew.setText("START NEW DAY")
        }

        btnStartNew.setOnClickListener {
            if (Global.playerBalance + profit < 100700) {
//                Reset Game
                Global.playerBalance = 350000
                Global.dayNumber = 1
                ResetDaily()
                val intent = Intent(this, PreparationActivity::class.java)
                intent.putExtra("PROFIT", 0)
                startActivity(intent)
            }
            else{
                Global.dayNumber += 1
                ResetDaily()
                val intent = Intent(this, PreparationActivity::class.java)
                intent.putExtra("PROFIT", profit)
                startActivity(intent)
            }
        }
    }
}