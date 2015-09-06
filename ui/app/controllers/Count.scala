package controllers

import scala.io.Source

object Count {
  def main (args: Array[String]) {
    val map = Map(
      'а' -> 1,
      'б' -> 3,
      'в' -> 2,
      'г' -> 3,
      'д' -> 2,
      'е' -> 1,
      'ё' -> 1,
      'ж' -> 5,
      'з' -> 5,
      'и' -> 1,
      'й' -> 2,
      'к' -> 2,
      'л' -> 2,
      'м' -> 2,
      'н' -> 1,
      'о' -> 1,
      'п' -> 2,
      'р' -> 2,
      'с' -> 2,
      'т' -> 2,
      'у' -> 3,
      'ф' -> 10,
      'х' -> 5,
      'ц' -> 10,
      'ч' -> 5,
      'ш' -> 10,
      'щ' -> 10,
      'ь' -> 5,
      'ы' -> 5,
      'ъ' -> 10,
      'э' -> 10,
      'ю' -> 10,
      'я' -> 3
    )
    def count(word:String) = {
      word.foldLeft(0) {(acc, l) => acc + map.getOrElse(l, 0)}
    }
    def criteria(word:String) = {
      !word.endsWith("ий") && !word.endsWith("ый")
    }
    val grouped = Source.fromFile("/home/eny/Documents/words.txt")
      .getLines()
      .toList
      .filter(criteria)
      .groupBy {
        _.size
      }
    def show(word:String) = println(word + " " + count(word))

    for((key, value) <- grouped.toList.sortBy(_._1)) {
      println(key)
      value.sortBy(count).reverse.take(10).map(show)
      println()
    }
  }
}
