package com.eny.legalentity

import java.io._
import java.util.zip.{ZipEntry, ZipFile, ZipOutputStream}

import scala.collection.JavaConversions._
import scala.io.Source
import org.apache.commons.io.IOUtils

class PositionCommand  {

  val TemplateName = "position/template.docx"
  val DocumentName = "document.xml"

  def generate(target:OutputStream) = {
    val zip = new ZipOutputStream(target)

    val map = Map(
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

    def replace(src:String) = {
      val res = map.foldLeft(src) {
        case (acc, (key, value)) => acc.replaceAll(key, value)
      }
      res
    }

    def substitute(stream: InputStream) = {
      Source.fromInputStream(stream, "UTF-8").getLines().foreach {
        item => IOUtils.copy(new StringReader(replace(item)), zip)
      }
    }
    val zipInput = new ZipFile(getClass.getClassLoader.getResource(TemplateName).getFile)
    zipInput.entries().toList.map {
      entry => {
        zip.putNextEntry(new ZipEntry(entry.getName))
        if(entry.getName.endsWith(DocumentName))
          substitute(zipInput.getInputStream(entry))
        else
          IOUtils.copy(zipInput.getInputStream(entry), zip)
        zip.closeEntry()
      }
    }
    zip.close()
  }
}

object PositionCommand {
  def main (args: Array[String]) {
    val command: PositionCommand = new PositionCommand()
    val out = new FileOutputStream("/home/eny/out1.docx")
    command.generate(out)
  }
}