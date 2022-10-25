package id.ac.ubaya.a160420046_coffeeshoptycoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //        Display Day Number
        txtDay3.text = "Result Day " + Global.dayNumber.toString()

        var strSellPrice = "%,d".format(Global.sellPrice).replace(",", ".")
        txtCoffeeRevenue.setText(Global.coffeSold.toString() + " cup of coffee\n@IDR "+ strSellPrice)

        var totalRevenue = Global.coffeSold * Global.sellPrice
        var strTotalRevenue = "%,d".format(totalRevenue).replace(",", ".")
        txtTotalRevenue.text = "IDR $strTotalRevenue"

        txtCoffeeRevenue.setText(Global.coffeSold.toString() + " cup of coffee\n@IDR "+ strSellPrice)

//        Display Total Expenses
        var strTotalExpenses = "%,d".format(Global.totalExpenses).replace(",", ".")
        txtTotalExpenses.text = "IDR $strTotalExpenses"

        var profit = totalRevenue - Global.totalExpenses
        var strProfit = "%,d".format(profit).replace(",", ".")
        txtProfit.text = "IDR $strProfit"

        Global.playerBalance += profit

        btnStartNew.setOnClickListener {
            Global.dayNumber += 1
            val intent = Intent(this, PreparationActivity::class.java)
            startActivity(intent)
        }
    }
}