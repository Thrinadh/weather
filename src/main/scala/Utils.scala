package weather

import java.io.PrintWriter
import java.text.{MessageFormat, SimpleDateFormat}
import java.util.{Date, TimeZone}

import com.typesafe.scalalogging.LazyLogging

import Configs._
import MeasuresGen._


object Utils extends LazyLogging{

  /* Generates various atmospheric measures and prints to std out
    The same can be used to write it to a file
    */

  def generateWeather (tmp:Map[String, Array[String]]) : Unit = {

    val tz = TimeZone.getTimeZone("UTC")
    val df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val df2 = new SimpleDateFormat("yyyyMMdd")
    df.setTimeZone(tz)
    val dateTime = new Date()
    val nowAsISO: String = df.format(dateTime)
    val dt:String = df2.format(dateTime)

    logger.info("printing data in - City|latitude,longitude,elevation|time|Climate|temperature|pressure|humidity")
    val msgString: String = "{0}|{1}|{2}|{3}|{4}|{5}|{6}"

    val outFileName = "climateData" + "_" + dt + ".csv"
    val outFile = new PrintWriter(outFileName)

     tmp.foreach(map=> {
       val latitude = getLocation(map._2(1).toDouble, map._2(2).toDouble, map._2(3).toInt)
       val humidity = getHumidity()
       val pressure = getPressure()
       val temperature = getTemperature()

       val climate = (temperature) match {
         case (temp) if (temp <= snowTemp) => "Snow"
         case (temp) if (temp <= rainyTemp) => "Rain"
         case (temp) if (temp > rainyTemp) => "Sunny"
         case _ => "Cloud:-)"
       }

       val finalRes = MessageFormat.format(msgString, map._2(0), (latitude._1 + "," + latitude._2 + "," + latitude._3).mkString, nowAsISO, climate, temperature.toString, pressure.toString, humidity.toString)
       println(finalRes)
       outFile.write(finalRes.toString + "\n")
     })
     outFile.close()
  }


  /* gets rounded values to the nearest 1 decimal
   * p : number of decimal places required
   * n : is the number that needs rounding
   */
  def roundAt(p: Int)(n: Double): Double = {
    val s = math pow (10, p)
    (math round n * s) /s
  }
}