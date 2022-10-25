package id.ac.ubaya.a160420046_coffeeshoptycoon

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_preparation.*
import kotlin.random.Random


class PreparationActivity : AppCompatActivity() {


//    Define initial variables
    private val weatherArray = arrayOf("Sunny Day", "Rainy Day", "Thunderstorm")

//    Randomize Weather
    val randomIndex = Random.nextInt(weatherArray.size)
    val randomWeather = weatherArray[randomIndex]

    var coffeenum = 0
    var milknum = 0
    var waternum = 0
    var cupprice = 0
    var cupnum = 0

    var locationCost = 0
    var coffeeCost = 0
    var totalCost = 0
    var sellPrice = 0

    fun updateCoffeePrice() {
        cupprice = coffeenum * Global.coffeeCost + milknum * Global.milkCost + waternum * Global.waterCost
        var strCupPrice = "%,d".format(cupprice).replace(",", ".")
        txtCoffeeNum.setText(cupnum.toString() + " cup of coffee\n@IDR "+ strCupPrice.toString())
        txtPrice.setText("cost per cup of coffee is IDR "+ strCupPrice.toString() +" \nhow many cup do you want to sell ?")
        updateCoffeeCost()
        updateTotalCost()
    }
    fun updateCoffeeCost(){
        coffeeCost = cupnum * cupprice
        var strTotalCoffeePrice = "%,d".format(coffeeCost).replace(",", ".")
        txtCoffeeCost.setText("IDR "+ strTotalCoffeePrice.toString())
        updateTotalCost()
    }
    fun updateLocationCost() {
        var strLocationCost = "%,d".format(locationCost).replace(",", ".")
        txtLocationCost.setText(" IDR " + strLocationCost.toString())
        updateTotalCost()
    }
    fun updateTotalCost(){
        totalCost = locationCost + coffeeCost
        var strTotalCost = "%,d".format(totalCost).replace(",", ".")
        txtTotal.setText(" IDR "+ strTotalCost.toString())

        if(totalCost > Global.playerBalance){
            btnStartDay.setEnabled(false);
            btnStartDay.setText("Insufficient Balance")
        }
        else{
            btnStartDay.setEnabled(true);
            btnStartDay.setText("START DAY")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparation)
        updateCoffeePrice()
        updateCoffeeCost()
        updateLocationCost()
        updateTotalCost()

//        Retrieve the player name from intent
        val playerName = intent.getStringExtra(LoginActivity.PLAYER_NAME)
//        Display the player name
        txtWelcome.text = "Welcome, $playerName!"
//        Display Initial Balance
        var strBalance = "%,d".format(Global.playerBalance).replace(",", ".")
        txtBalance.text = "Balance: IDR $strBalance"

//        Display Randomized Weather
        txtWeather.text = "$randomWeather"

        val adapter = ArrayAdapter(this, R.layout.myspinner_layout, Global.location)
        adapter.setDropDownViewResource(R.layout.myspinner_item_layout)
        spinLocation.adapter = adapter

//        Coffee Button
        btnAddCoffee.setOnClickListener{
            coffeenum += 1
            txtNumCoffee.setText(coffeenum.toString())
            updateCoffeePrice()
        }
        btnMinCoffee.setOnClickListener{
            if (coffeenum > 0){
                coffeenum -= 1
                txtNumCoffee.setText(coffeenum.toString())
                updateCoffeePrice()
            }
        }

//         Milk Button
        btnAddMilk.setOnClickListener{
            milknum += 1
            txtNumMilk.setText(milknum.toString())
            updateCoffeePrice()
        }
        btnMinMilk.setOnClickListener{
            if (milknum > 0){
                milknum -= 1
                txtNumMilk.setText(milknum.toString())
                updateCoffeePrice()
            }
        }

//         Water Button
        btnAddWater.setOnClickListener{
            waternum += 1
            txtNumWater.setText(waternum.toString())
            updateCoffeePrice()
        }
        btnMinWater.setOnClickListener{
            if (waternum > 0){
                waternum -= 1
                txtNumWater.setText(waternum.toString())
                updateCoffeePrice()
            }
        }

//        Text Cup Number
        txtCupNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (txtCupNumber.text.toString() != ""){
                    cupnum = Integer.parseInt(txtCupNumber.text.toString())
                }
                else{
                    cupnum = 0
                }
                updateCoffeePrice()
                updateCoffeeCost()
            }
        })

//        Text Selling Price
        txtSellPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (txtSellPrice.text.toString() != ""){
                    sellPrice = Integer.parseInt(txtSellPrice.text.toString())
                }
                else{
                    sellPrice = 0
                }
            }
        })

//        Spinner Location
        spinLocation.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                locationCost = Global.location[p2].fee
                updateLocationCost()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                locationCost = Global.location[0].fee
                updateLocationCost()
            }
        }

//        Button Start Day
        btnStartDay.setOnClickListener{
            
        }

    }
}