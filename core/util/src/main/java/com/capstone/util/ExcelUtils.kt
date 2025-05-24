package com.capstone.util

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream

object ExcelUtils {

    fun readExcelAsString(inputStream: InputStream): String {
        val workbook = XSSFWorkbook(inputStream)
        val sheet = workbook.getSheetAt(0)
        val stringBuilder = StringBuilder()

        for (row in sheet) {
            for (cell in row) {
                val value = when (cell.cellType) {
                    CellType.STRING -> cell.stringCellValue
                    CellType.NUMERIC -> cell.numericCellValue.toString()
                    else -> ""
                }
                stringBuilder.append(value).append("\t")
            }
            stringBuilder.append("\n")
        }

        workbook.close()
        return stringBuilder.toString()
    }
}
