package io.konv.audiostudio.controllers

import io.konv.audiostudio.DBManager
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.chart
import scalafx.scene.chart._
import javafx.scene.input.KeyCode

import scalafx.application.Platform
import scalafxml.core.macros.sfxml

case class MonthIncome(income: Int, month: Int, year: Int)

trait PlotTabControllerTrait {
  def update(): Unit
}

@sfxml
class PlotTabController(plot: LineChart[String, Number],
                        x: CategoryAxis,
                        y: NumberAxis) extends PlotTabControllerTrait {

  plot.setTitle("Income")
  update()

  plot.focusTraversable = true

  plot.onKeyPressed = v => v.getCode match {
    case KeyCode.F5 => update()
    case _ => ()
  }

  override def update(): Unit = {
    implicit val getResult = GetResult[MonthIncome](r => MonthIncome(r.<<, r.<<, r.<<))
    val query = sql"SELECT income, month, year FROM report".as[MonthIncome]
    DBManager.db.run(query).onComplete {
      case Success(v) => Platform.runLater(populateData(v))
      case Failure(v) => ()
    }
  }

  private def populateData(data: Vector[MonthIncome]): Unit = {
    val series = new chart.XYChart.Series[String, Number]()
    val seriesData = series.data.get()
    for (v <- data) {
      seriesData add XYChart.Data[String, Number](s"${v.month}-${v.year}", v.income)
    }
    plot.data = series
  }
}
