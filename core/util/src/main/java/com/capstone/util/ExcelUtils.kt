package com.capstone.util

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.BufferedInputStream
import java.io.InputStream
import java.util.zip.ZipInputStream

object ExcelUtils {

    fun readExcelAsString(inputStream: InputStream): String {
        val bufferedStream = BufferedInputStream(inputStream)
        bufferedStream.mark(8192)  // 충분히 큰 버퍼 크기 설정

        val isXLSX = try {
            val zip = ZipInputStream(bufferedStream)
            zip.nextEntry != null
        } catch (e: Exception) {
            false
        } finally {
            bufferedStream.reset()
        }

        return if (isXLSX) {
            readXLSX(bufferedStream)
        } else {
            readXLS(bufferedStream)
        }
    }

    private fun readXLSX(inputStream: InputStream): String {
        val workbook = XSSFWorkbook(inputStream)
        val sheet = workbook.getSheetAt(0)
        val builder = StringBuilder()

        for (row in sheet) {
            for (cell in row) {
                builder.append(cell.toString()).append("\t")
            }
            builder.append("\n")
        }

        workbook.close()
        return builder.toString()
    }

    private fun readXLS(inputStream: InputStream): String {
        val workbook = HSSFWorkbook(inputStream)
        val sheet = workbook.getSheetAt(0)
        val builder = StringBuilder()

        for (row in sheet) {
            for (cell in row) {
                builder.append(cell.toString()).append("\t")
            }
            builder.append("\n")
        }

        workbook.close()
        return builder.toString()
    }
}
