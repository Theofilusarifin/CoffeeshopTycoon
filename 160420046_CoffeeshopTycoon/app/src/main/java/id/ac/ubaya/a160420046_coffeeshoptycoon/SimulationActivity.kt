package id.ac.ubaya.a160420046_coffeeshoptycoon

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_simulation.*
import kotlin.random.Random

class SimulationActivity : AppCompatActivity() {
    override fun onBackPressed() {
        Toast.makeText(this, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        var coffeeSold = 0
        var customers = mutableListOf<RandomSold>()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)
//        Display Day Number
        txtDay2.text = "Day " + Global.dayNumber.toString()

//        Display Randomized Weather
        val randomWeather = Global.weather[Global.weatherRandomIndex].toString()
        txtWeather2.text = "$randomWeather"

//        Randomize the customer
        var hour = 7
        var buy_probability = Global.weather[Global.weatherRandomIndex].probability
        for (i in 1..12) {
            var strTime = ""
            if (hour < 10){
                strTime = "0" + hour.toString() + ".00"
            }
            else{
                strTime = hour.toString() + ".00"
            }

            if (coffeeSold < Global.cupnum){
                var randomProb = Random.nextInt(1,101)
                if (randomProb <= buy_probability){
                    var customerBuy = Random.nextInt(1, Global.cupnum - coffeeSold + 1)
                    customers.add(RandomSold(strTime, customerBuy))
                    coffeeSold += customerBuy
                }
                else{
                    customers.add(RandomSold(strTime, 0))
                }
            }
            else{
                customers.add(RandomSold(strTime, -1))
            }
            hour+=1
        }

        Global.coffeSold = coffeeSold

        recyclerView.adapter = RVAdapter(customers)

        btnResults.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}