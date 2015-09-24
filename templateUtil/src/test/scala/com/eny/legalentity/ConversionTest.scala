package com.eny.legalentity

import java.io._

import org.scalatest.{FunSpec, Matchers}

class ConversionTest extends FunSpec with Matchers {

  describe("Checks if conversion is fine") {
    it("Size of resulting file matches expected") {
      val actual = new ByteArrayOutputStream()
      val buf = Array[Byte]()
      new DocxConversion(
        Map(
          "entity_name" -> "Entity NAME",
          "entity_inn" -> "ENTITY inn",
          "entity_kpp" -> "KPP",
          "order_number" -> "123",
          "entity_form_short" -> "VERY SHORT",
          "entity_form_full" -> "VERY FULL",
          "directors_last_fm" -> "Last F.M.",
          "entity_found_gen" -> "Gen",
          "order_date_gen" -> "Date gend"
        )
      ).generate(
        getClass.getClassLoader.getResourceAsStream("doc.docx"),
        actual
      )
      actual.size() should === (6073)
    }
    it("Converts xlsx documents") {
      val actual = new ByteArrayOutputStream()
      val buf = Array[Byte]()
      new DocxConversion(
        Map(
          "string11" -> "FirstFirst",
          "22" -> "112233"
        ),
        ".xml"
      ).generate(
          getClass.getClassLoader.getResourceAsStream("mark.xlsx"),
          actual
        )
      actual.size() should === (6767)
    }
  }
}
