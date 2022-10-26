package id.ac.ubaya.a160420046_coffeeshoptycoon

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_preparation.*
import kotlin.random.Random


class PreparationActivity : AppCompatActivity() {
//    Cannot go back
    override fun onBackPressed() {
        Toast.makeText(this, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }

//    Randomize Weather
    val randomIndex = Random.nextInt(Global.weather.size)

//    Intitialize variable
    var coffeenum = 0
    var milknum = 0
    var waternum = 0

    var locationCost = 0
    var coffeeCost = 0

    fun updateCoffeePrice() {
        Global.cupprice = coffeenum * Global.coffeeCost + milknum * Global.milkCost + waternum * Global.waterCost
        var strCupPrice = "%,d".format(Global.cupprice).replace(",", ".")
        txtCoffeeRevenue.setText(Global.cupnum.toString() + " cup of coffee\n@IDR "+ strCupPrice.toString())
        txtPrice.setText("cost per cup of coffee is IDR "+ strCupPrice.toString() +" \nhow many cup do you want to sell ?")
        updateCoffeeCost()
        updateTotalCost()
    }
    fun updateCoffeeCost(){
        coffeeCost = Global.cupnum * Global.cupprice
        var strTotalCoffeePrice = "%,d".format(coffeeCost).replace(",", ".")
        txtTotalRevenue.setText("IDR "+ strTotalCoffeePrice.toString())
        updateTotalCost()
    }
    fun updateLocationCost() {
        var strLocationCost = "%,d".format(locationCost).replace(",", ".")
        txtTotalExpenses.setText(" IDR " + strLocationCost.toString())
        updateTotalCost()
    }
    fun updateTotalCost(){
        Global.totalExpenses = locationCost + coffeeCost
        var strTotalCost = "%,d".format(Global.totalExpenses).replace(",", ".")
        txtProfit.setText(" IDR "+ strTotalCost.toString())

        if(Global.totalExpenses > Global.playerBalance){
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

        if (!Global.loadedSave){
            val sharedFile = "id.ac.ubaya.a160420046.coffeeshoptycoon"
            val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
//        Retrieve Shared Preference
            val savedBalance = shared.getInt("playerBalance", 0)
            val savedDayNumber = shared.getInt("dayNumber", 0)

            //        Load Data
            if (savedBalance != 0){
                Global.playerBalance = savedBalance
            }
            if (savedDayNumber != 0){
                Global.dayNumber = savedDayNumber
                Toast.makeText(this, "Saved data loaded successfuly!", Toast.LENGTH_SHORT).show()
            }

//            To make data not loaded over and over
            Global.loadedSave = true
        }

        btnSaveProgress.setOnClickListener{
            // Set Shared Preference
            val sharedFile = "id.ac.ubaya.a160420046.coffeeshoptycoon"
            val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = shared.edit()

            editor.putInt("playerBalance", Global.playerBalance)
            editor.putInt("dayNumber", Global.dayNumber)
            editor.apply()
            Toast.makeText(this, "Data saved successfuly!", Toast.LENGTH_SHORT).show()
        }

//        Player balance updated from previous profit
        val previous_profit = intent.getIntExtra("PROFIT", 0)
        Global.playerBalance += previous_profit

//        Set random weather
        Global.weatherRandomIndex = randomIndex
//        Get random weather
        val randomWeather = Global.weather[Global.weatherRandomIndex].toString()

        updateCoffeePrice()
        updateCoffeeCost()
        updateLocationCost()
        updateTotalCost()

//        Display the player name
        val playerName = Global.playerName
        txtWelcome.text = "Welcome, $playerName"
//        Display Player Balance
        var strBalance = "%,d".format(Global.playerBalance).replace(",", ".")
        txtBalance.text = "Balance: IDR $strBalance"
//        Display Day Number
        txtDay.text = "Day " + Global.dayNumber.toString()
//        Display Randomized Weather
        txtWeather.text = "$randomWeather"
//        Set spin location
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

//        Button Use Template
        btnUseTemplate.setOnClickListener{
            // Set Shared Preference
            val sharedFile = "id.ac.ubaya.a160420046.coffeeshoptycoon"
            val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
            coffeenum = shared.getInt("numCoffee", 0)
            milknum = shared.getInt("numMilk", 0)
            waternum = shared.getInt("numWater", 0)

            txtNumCoffee.setText(coffeenum.toString())
            txtNumMilk.setText(milknum.toString())
            txtNumWater.setText(waternum.toString())
            updateCoffeePrice()

            Toast.makeText(this, "Template successfuly used!", Toast.LENGTH_SHORT).show()
        }

//        Button Save Recipe
        btnSaveRecipe.setOnClickListener{
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setTitle("Confirmation")
            builder.setMessage("Saving will overwrite previous template. Are you sure?")
            builder.setPositiveButton("Confirm",
                DialogInterface.OnClickListener { dialog, which ->
                    // Set Shared Preference
                    val sharedFile = "id.ac.ubaya.a160420046.coffeeshoptycoon"
                    val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = shared.edit()
                    editor.putInt("numCoffee", coffeenum)
                    editor.putInt("numMilk", milknum)
                    editor.putInt("numWater", waternum)
                    editor.apply()
                    Toast.makeText(this, "Template Saved!", Toast.LENGTH_SHORT).show()
                })
            builder.setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                })

            val dialog: android.app.AlertDialog? = builder.create()
            if (dialog != null) {
                dialog.show()
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
                    Global.cupnum = Integer.parseInt(txtCupNumber.text.toString())
                }
                else{
                    Global.cupnum = 0
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
                    Global.sellPrice = Integer.parseInt(txtSellPrice.text.toString())
                }
                else{
                    Global.sellPrice = 0
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
            if (Global.cupnum > 0){
                if (Global.sellPrice > 0){
                    val intent = Intent(this, SimulationActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Please input minimum IDR 1 as a sell price !", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please input minimum 1 cup number to sell !", Toast.LENGTH_SHORT).show()
            }
        }

    }
}