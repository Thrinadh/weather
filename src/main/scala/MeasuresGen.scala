package weather

import java.util.Calendar

import com.typesafe.scalalogging.LazyLogging

import scala.util.Random

import Configs._

import Utils.roundAt


/* Object creating mocked data for various atomospheric measures  */

object MeasuresGen extends LazyLogging{

/* get latitude, longitude information
* x0 - x coordinate
* y0 - y coordinate
* elevation - elevation: the radius measure for the planet
* */

  def getLocation(x0:Double, y0:Double, elevation:Int)={

    // Elevation is to calculate the radius. Converting radius from meters to degrees
    val radiusInDegrees = elevation / 111000f

    val u = Random.nextDouble
    val v = Random.nextDouble
    val w = radiusInDegrees * Math.sqrt(u)
    val t = 2 * Math.PI * v
    val x = w * Math.cos(t)
    val y = w * Math.sin(t)

    /* Adjust the x-coordinate for the shrinking of the east-west distances
       X coordinate needs adjustment to count for the incline
     */
    val new_x = x / Math.cos(Math.toRadians(y0))

    val longitude = (new_x + x0) match {
      case n if (x > 80) => 80
      case n if (x < -180) => -180
      case _ => (new_x + x0)
    }

    logger.info("Longitude: %.2f".format(longitude))

    val latitude = (y + y0) match {
      case n if (x > 90) => 90
      case n if (x < -90) => -90
      case _ => (y + y0)
    }

    logger.info("latitude:  %.2f".format(latitude))

    ("%.2f".format(latitude), "%.2f".format(longitude), elevation)
  }

  /* Returns temperature rounded to 1 decimal value */
  def getTemperature(): Double = {
    val hrs = Calendar.getInstance.get(Calendar.HOUR)
    val temp = roundAt(1)(minTemp + Random.nextInt(maxTemp - minTemp) + Random.nextFloat)
    if (temp >= dayTemp) {
      if (hrs < dayStart & hrs > dayEnd) {
        roundAt(1)(temp - Random.nextInt(10) + Random.nextFloat())
      }
    }
    temp
  }

  /* Returns pressure rounded to 1 decimal value */
  def getPressure(): Double = {
    roundAt(1)(minPressure + Random.nextInt(maxPresssure -minPressure) + Random.nextFloat)
  }

  /* Returns humidity value */
  def getHumidity(): Int = {
    minHumidity + Random.nextInt(maxHumidity -minHumidity)
  }
}
