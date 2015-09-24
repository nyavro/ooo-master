package com.eny.legalentity

import java.io._
import java.util.zip.{ZipEntry, ZipInputStream, ZipOutputStream}

import org.apache.commons.io.IOUtils

import scala.io.Source

class DocxConversion(map:Map[String, String], documentName:String)  {

  def this(map:Map[String, String]) = this(map, "document.xml")

  def generate(source:InputStream, target:OutputStream) = using(new ZipOutputStream(target)) {
    targetZip =>
      using(new ZipInputStream(source)) {
        _.map {
          case (entry, entryStream) =>
            targetZip.putNextEntry(new ZipEntry(entry.getName))
            if (entry.getName.endsWith(documentName))
              substitute(entryStream, targetZip)
            else
              IOUtils.copy(entryStream, targetZip)
            targetZip.closeEntry()
        }.toList
      }
  }

  private def using[T <: AutoCloseable](closable:T)(f:(T) => Unit) =
    try {
      f(closable)
    } finally {
      closable.close()
    }

  private implicit def asStream(input:ZipInputStream):Stream[(ZipEntry, InputStream)] =
    Option(input.getNextEntry).map(item => (item, input) #:: asStream(input)).getOrElse(Stream.empty)

  private def replace(src:String) = map.foldLeft(src) {case (acc, (key, value)) => acc.replaceAll(key, value)}

  private def substitute(source: InputStream, target:OutputStream) = Source.fromInputStream(source, "UTF-8").getLines().foreach {
    item => IOUtils.copy(new StringReader(replace(item)), target)
  }
}
