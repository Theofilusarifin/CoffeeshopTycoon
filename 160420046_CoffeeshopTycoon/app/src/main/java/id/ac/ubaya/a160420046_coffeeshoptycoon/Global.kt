package id.ac.ubaya.a160420046_coffeeshoptycoon

object Global {
    var playerName = ""
    var loadedSave = false

    var playerBalance = 350000
    val coffeeCost = 500
    val milkCost = 1000
    val waterCost = 200

    var dayNumber = 1
    var weatherRandomIndex = 0

    var cupprice = 0
    var cupnum = 0
    var totalExpenses = 0

    var coffeSold = 0
    var sellPrice = 0

    val location:Array<Location> = arrayOf(
        Location(1, "University", 100000),
        Location(2, "Business Distric", 350000),
        Location(3, "Beach", 500000)
    )

    val weather:Array<Weather> = arrayOf(
        Weather(1, "Sunny Day", 50),
        Weather(2, "Rainy Day", 75),
        Weather(3, "Thunderstorm", 25)
    )
}