package id.ac.ubaya.a160420046_coffeeshoptycoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_preparation.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class PreparationActivity : AppCompatActivity() {

//    Define initial variables
    private val weatherArray = arrayOf("Sunny Day", "Rainy Day", "Thunderstorm")
    private val coffeeCost = 500
    private val milkCost = 1000
    private val waterCost = 200

//    Randomize Weather
    val randomIndex = Random.nextInt(weatherArray.size);
    val randomWeather = weatherArray[randomIndex]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparation)

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
    }
}