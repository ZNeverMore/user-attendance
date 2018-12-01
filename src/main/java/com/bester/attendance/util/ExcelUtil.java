package com.bester.attendance.util;

import com.bester.attendance.entity.UserAttendance;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqiang
 */
public class ExcelUtil {

    public static XSSFWorkbook exportExcel(Map<String, List<UserAttendance>> dataMap) {
        SimpleDateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatHMS = new SimpleDateFormat("HH:mm:ss");
        int rowIndex = 0;
        int cellIndex = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("考勤表");
        XSSFCellStyle cellStyle = createCellStyle(workbook);
        XSSFRow firstRow = sheet.createRow(rowIndex);
        XSSFCell firstRowFirstCell = firstRow.createCell(cellIndex);
        firstRowFirstCell.setCellValue("考勤表");
        firstRowFirstCell.setCellStyle(cellStyle);
        XSSFRow secondRow = sheet.createRow(++rowIndex);
        XSSFCell secondRowFirstCell = secondRow.createCell(cellIndex);
        secondRowFirstCell.setCellValue("姓名");
        secondRowFirstCell.setCellStyle(cellStyle);
        XSSFCell secondRowSecondCell = secondRow.createCell(cellIndex + 1);
        secondRowSecondCell.setCellValue("日期");
        secondRowSecondCell.setCellStyle(cellStyle);
        XSSFCell secondRowThirdCell = secondRow.createCell(cellIndex + 2);
        secondRowThirdCell.setCellValue("签到");
        secondRowThirdCell.setCellStyle(cellStyle);
        XSSFCell secondRowFourthCell = secondRow.createCell(cellIndex + 3);
        secondRowFourthCell.setCellValue("签退");
        secondRowFourthCell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        if (CollectionUtils.isEmpty(dataMap)) {
            return null;
        }
        int listSize = 0;
        for (Map.Entry<String, List<UserAttendance>> entry : dataMap.entrySet()) {
            for (UserAttendance userAttendance : entry.getValue()) {
                XSSFRow row = sheet.createRow(++rowIndex);
                XSSFCell firstCell = row.createCell(cellIndex);
                firstCell.setCellStyle(cellStyle);
                if (StringUtils.isBlank(firstCell.getStringCellValue())) {
                    firstCell.setCellValue(entry.getKey());
                }
                XSSFCell secondCell = row.createCell(cellIndex + 1);
                secondCell.setCellStyle(cellStyle);
                XSSFCell thirdCell = row.createCell(cellIndex + 2);
                thirdCell.setCellStyle(cellStyle);
                XSSFCell fourthCell = row.createCell(cellIndex + 3);
                fourthCell.setCellStyle(cellStyle);
                if (userAttendance.getFirstIn() != null) {
                    String[] firstInArr = userAttendance.getFirstIn().split(" ");
                    secondCell.setCellValue(firstInArr[0]);
                    thirdCell.setCellValue(firstInArr[1]);
                }
                if (userAttendance.getLastOut() != null) {
                    String[] lastOutArr = userAttendance.getLastOut().split(" ");
                    fourthCell.setCellValue(lastOutArr[1]);
                }
            }
            sheet.addMergedRegion(new CellRangeAddress(listSize, listSize + entry.getValue().size(), 0, 0));
            listSize += entry.getValue().size();
        }
        return workbook;
    }

    private static XSSFCellStyle createCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.VERTICAL_CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(XSSFFont.DEFAULT_FONT_SIZE);
        cellStyle.setFont(font);
        return cellStyle;
    }

}
