package weather

import java.nio.file.Paths
import java.nio.file.Files

import com.typesafe.scalalogging.LazyLogging

import scala.collection.immutable.HashMap

/* Main Application
* reads the location.txt file and generates a hash map
* the hash map in turn used to generate weather data
* */

object MyWeather extends App with LazyLogging{
  logger.info("Weather Data Generation ...")

  var arrMap = new HashMap[String, Array[String]]()
  val locData = Files.newBufferedReader(Paths.get("location.txt"))
  var line:String =  locData.readLine()

  while (null != line) {
    val locationInfo:Array[String] = line.split(",")
        arrMap += locationInfo(0) -> locationInfo
        line = locData.readLine()
  }
  Utils.generateWeather(arrMap)

  logger.info("Successfully Completed!")
}