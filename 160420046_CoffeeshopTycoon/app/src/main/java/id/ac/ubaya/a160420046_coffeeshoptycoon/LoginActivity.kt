package id.ac.ubaya.a160420046_coffeeshoptycoon

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val PLAYER_NAME: String = "pname"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set Shared Preference
        val sharedFile = "id.ac.ubaya.a160420046.coffeeshoptycoon"
        val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putString("username", "theo")
        editor.putString("password", "password")
        editor.putString("pname", "Theo")
        editor.apply()

//        Retrieve Shared Preference
        val username = shared.getString("username", "error")
        val password = shared.getString("password", "error")
        val playerName = shared.getString("pname", "error")

        btnLogin.setOnClickListener {
            var inputtedUsername = txtUsername.text.toString()
            var inputtedPassword = txtPassword.text.toString()

            if ((inputtedUsername == username) && (inputtedPassword == password)){
                val intent = Intent(this, PreparationActivity::class.java)
                intent.putExtra(PLAYER_NAME, playerName)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "This credential does not match our records!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}