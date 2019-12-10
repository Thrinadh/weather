package weather

import Utils._
import MeasuresGen._

import org.scalatest.funsuite.AnyFunSuite

class testMyWeather extends AnyFunSuite {
  test("Check temperatuer range") {
    val a = getTemperature()
    assert( a > -20 && a < 50 )
  }

  test("roundAt Check"){
    val v = roundAt(1)(25.3334)
    assert(v === 25.3)
  }

  test("Check humidity values"){
    val h = getHumidity()
    assert(h > 0 & h <= 100)
  }
}