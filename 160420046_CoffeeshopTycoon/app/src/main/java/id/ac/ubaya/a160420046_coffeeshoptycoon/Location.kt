package id.ac.ubaya.a160420046_coffeeshoptycoon

data class Location(val id:Int, var name:String, var fee:Int){
    override fun toString() = name
}
