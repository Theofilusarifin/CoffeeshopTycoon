package id.ac.ubaya.a160420046_coffeeshoptycoon

data class Weather(val id:Int, var name:String, var probability:Int){
    override fun toString() = name
}
