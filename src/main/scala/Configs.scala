package weather

object Configs {
  val minTemp         = -20   //with the assumption that people can't stay below this temperature
  val maxTemp         = 50

  val minPressure     = 800
  val maxPresssure    = 1300

  val minHumidity     = 0
  val maxHumidity     = 100

  val snowTemp        = 5    // less than 5 degrees C is considered as snowy
  val rainyTemp       = 25   // temperature between 5 and 25 is considered rainy. Above 25 is considered sunny
  val dayTemp         = 25

  val dayStart        = 6.00
  val dayEnd          = 18.00
}
