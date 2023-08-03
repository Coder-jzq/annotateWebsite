package com.data.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.excel.EasyExcel;
import com.data.pojo.BzRecord;
import com.data.pojo.BzRecordPause;
import com.data.service.IBzRecordPauseService;
import com.data.service.IBzRecordService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jia Zhenqi
 * @Date: 2023/6/2  23:34
 * @Version 1.0
 */
@Controller
public class ExcelController {
    @Autowired
    private IBzRecordService bzRecordService;

    @Autowired
    private IBzRecordPauseService bzRecordPauseService;
    //@GetMapping("/api/export-excel")
    //public void exportExcel(HttpServletResponse response) throws IOException {
    //    List<BzRecord> list = bzRecordService.list();
    //    ExportParams params = new ExportParams("标注记录", "标注记录", ExcelType.HSSF);
    //    Workbook workbook = ExcelExportUtil.exportExcel(params, BzRecord.class, list);
    //    ServletOutputStream out = null;
    //    try {
    //        // 流形式
    //        response.setHeader("content-type", "application/octet-stream");
    //        response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("标注记录.xls", "UTF-8"));
    //        out = response.getOutputStream();
    //        System.out.println(workbook);
    //        workbook.write(out);
    //    }catch (IOException e) {
    //        e.printStackTrace();
    //    }finally {
    //        if (null!=out) {
    //            try {
    //                out.close();
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    }
    //}

    @GetMapping("/api/export-excel-emotion")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("数据情感标注记录.xlsx", "UTF-8"));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("数据情感标注记录");

        int batchSize = 10000; // 每个批次的大小
        int rowIndex = 0; // 当前行索引

        List<BzRecord> list = bzRecordService.list(); // 获取数据

        // 遍历数据并分批写入 Excel 文件
        for (int i = 0; i < list.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, list.size());
            List<BzRecord> batchList = list.subList(i, endIndex);

            // 将批次数据写入 Excel
            writeBatchToExcel(sheet, batchList, rowIndex);

            // 更新当前行索引
            rowIndex += batchList.size();

        }

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        workbook.close();
    }

    private void writeBatchToExcel(Sheet sheet, List<BzRecord> batchList, int rowIndex) {

        // 根据批次数据写入 Excel 的逻辑
        for (int i = 0; i < batchList.size(); i++) {
            Row row = sheet.createRow(rowIndex + i);
            BzRecord record = batchList.get(i);
            // 设置单元格的值
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(record.getRecordIndex());

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(record.getRecordText());

            // 因为user1用户初始化的时候就是null，所以user1为null就表示这一行为null

            Cell cell3 = row.createCell(2);
            String s1 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser1());
            cell3.setCellValue(s1);

            Cell cell4 = row.createCell(3);
            String s2 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser2());
            cell4.setCellValue(s2);

            Cell cell5 = row.createCell(4);
            String s3 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser3());
            cell5.setCellValue(s3);

            Cell cell6 = row.createCell(5);
            String s4 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser4());
            cell6.setCellValue(s4);

            Cell cell7 = row.createCell(6);
            String s5 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser5());
            cell7.setCellValue(s5);

            Cell cell8 = row.createCell(7);
            String s6 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser6());
            cell8.setCellValue(s6);

            Cell cell9 = row.createCell(8);
            String s7 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser7());
            cell9.setCellValue(s7);

            Cell cell10 = row.createCell(9);
            String s8 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser8());
            cell10.setCellValue(s8);

            Cell cell11 = row.createCell(10);
            String s9 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser9());
            cell11.setCellValue(s9);

            Cell cell12 = row.createCell(11);
            String s10 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser10());
            cell12.setCellValue(s10);

            // 设置单元格样式等
        }
    }


    @GetMapping("/api/export-excel-pause")
    public void exportExcelAccent(HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("数据停顿标注记录.xlsx", "UTF-8"));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("数据停顿标注记录");

        int batchSize = 10000; // 每个批次的大小
        int rowIndex = 0; // 当前行索引

        List<BzRecordPause> list = bzRecordPauseService.list(); // 获取数据

        // 遍历数据并分批写入 Excel 文件
        for (int i = 0; i < list.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, list.size());
            List<BzRecordPause> batchList = list.subList(i, endIndex);

            // 将批次数据写入 Excel
            writeBatchToExcelAccent(sheet, batchList, rowIndex);

            // 更新当前行索引
            rowIndex += batchList.size();

        }

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        workbook.close();
    }

    private void writeBatchToExcelAccent(Sheet sheet, List<BzRecordPause> batchList, int rowIndex) {

        // 根据批次数据写入 Excel 的逻辑
        for (int i = 0; i < batchList.size(); i++) {
            Row row = sheet.createRow(rowIndex + i);
            BzRecordPause record = batchList.get(i);
            // 设置单元格的值
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(record.getRecordIndex());

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(record.getRecordText());

            // 因为user1用户初始化的时候就是null，所以user1为null就表示这一行为null

            Cell cell3 = row.createCell(2);
            String s1 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser1());
            cell3.setCellValue(s1);

            Cell cell4 = row.createCell(3);
            String s2 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser2());
            cell4.setCellValue(s2);

            Cell cell5 = row.createCell(4);
            String s3 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser3());
            cell5.setCellValue(s3);

            Cell cell6 = row.createCell(5);
            String s4 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser4());
            cell6.setCellValue(s4);

            Cell cell7 = row.createCell(6);
            String s5 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser5());
            cell7.setCellValue(s5);

            Cell cell8 = row.createCell(7);
            String s6 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser6());
            cell8.setCellValue(s6);

            Cell cell9 = row.createCell(8);
            String s7 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser7());
            cell9.setCellValue(s7);

            Cell cell10 = row.createCell(9);
            String s8 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser8());
            cell10.setCellValue(s8);

            Cell cell11 = row.createCell(10);
            String s9 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser9());
            cell11.setCellValue(s9);

            Cell cell12 = row.createCell(11);
            String s10 = record.getRecordUser1()==null?"": String.valueOf(record.getRecordUser10());
            cell12.setCellValue(s10);

            // 设置单元格样式等
        }
    }
}
