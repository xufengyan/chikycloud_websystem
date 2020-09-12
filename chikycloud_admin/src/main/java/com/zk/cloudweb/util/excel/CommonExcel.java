package com.zk.cloudweb.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author xf
 * @version 1.0
 * @date 2020/4/2 16:49
 */
public class CommonExcel {
    /**
     * 列表导出
     * @param response
     * @param exportName
     * @param headName
     * @param data
     * @param head
     * @param length
     * @throws Exception
     */
    public static void importExcelliebiao(HttpServletResponse response, String exportName, String headName, List<Object> data, String head[], Integer length[]
    ) throws Exception {
        String fileName = exportName;
        fileName = new String(fileName.getBytes("GBK"), "iso8859-1");
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename="
                + fileName);// 指定下载的文件名
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建表
        HSSFSheet sheet = wb.createSheet(headName);
        ExcelExport exportExcel = new ExcelExport(wb, sheet);
        // 创建报表头部
        exportExcel.createNormalHead3(headName, head.length - 1);
        //设置头部样式
        HSSFCellStyle Headstyle = headStyle(wb, wb.createCellStyle());
        Headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        Headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        Headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置数据样式
        HSSFCellStyle style = dataStyle(wb.createCellStyle());
        Integer i = 0;//用于判断是否num与相等，如果相等就换行
        Integer rows = 2;//导出的行数
        //写入第一行数据
        HSSFRow row2 = sheet.createRow(1);
        HSSFCell hssfCell2 = row2.createCell(0);
        //写入数据
        for (int y = 0; y < head.length; y++) {
            hssfCell2 = setCell(hssfCell2, row2, Headstyle, head[y], y);
            sheet.setColumnWidth(y, length[y]);
        }
        //设置高度
        row2.setHeightInPoints(30);
        //写入数据
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell hssfCell3 = row3.createCell(0);


        //写入数据
        for (Object string : data) {
            if (head.length == i) {
                //行数添加一行
                rows++;
                //换行后初始化后为1
                i = 0;
                //创建行
                row3 = sheet.createRow(rows);
                //放入数据
                hssfCell3 = setCell(hssfCell3, row3, style, string, i);
//                index++;
//                count++;
            } else {
                if (i == 0) {
                    //创建行
                    row3 = sheet.createRow(rows);

//                    count ++;

                }
//                index++;
                //放入数据
                hssfCell3 = setCell(hssfCell3, row3, style, string, i);


            }
            //设置高度
            row3.setHeightInPoints(25);
            i++;
        }
        //写入数据
        HSSFCellStyle footStyle = footStyle(wb,wb.createCellStyle());
        footStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        footStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        footStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        footStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

//        //插入合计
//        HSSFRow row4 = sheet.createRow(rows+1);
//        HSSFCell hssfCell4 = row4.createCell(0);
//        if(data2!=null){
//            for(int z=0; z<data2.size();z++){
//                hssfCell4 = setCell(hssfCell4,row4, footStyle, data2.get(z), z);
//            }
//        }
//        //设置高度
//        row4.setHeightInPoints(30);
        try {
            bufferedOutPut.flush();
            wb.write(bufferedOutPut);
            bufferedOutPut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            data.clear();//清除集合的所有内容
        }
    }

    /**
     * excel导出zip
     * @param response
     * @param exportName
     * @param headName
     * @param data
     * @param head
     * @param length
     * @throws Exception
     */
    public static ZipOutputStream importBacthExcelliebiao(HttpServletResponse response, String exportName, String headName, List<Object> data, String head[], Integer length[]
    , int num, ZipOutputStream zipOutputStream) throws Exception {
        String fileName = exportName;
        fileName = new String(fileName.getBytes("GBK"), "iso8859-1");
//        response.reset();
//        response.setHeader("Content-Disposition", "attachment;filename="
//                + fileName);// 指定下载的文件名
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        OutputStream output = response.getOutputStream();
//        ZipOutputStream zipOutputStream = new ZipOutputStream(output);
//        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建表
        HSSFSheet sheet = wb.createSheet(headName);
        ExcelExport exportExcel = new ExcelExport(wb, sheet);
        // 创建报表头部
        exportExcel.createNormalHead3(headName, head.length - 1);
        //设置头部样式
        HSSFCellStyle Headstyle = headStyle(wb, wb.createCellStyle());
        Headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        Headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        Headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置数据样式
        HSSFCellStyle style = dataStyle(wb.createCellStyle());
        Integer i = 0;//用于判断是否num与相等，如果相等就换行
        Integer rows = 2;//导出的行数
        //写入第一行数据
        HSSFRow row2 = sheet.createRow(1);
        HSSFCell hssfCell2 = row2.createCell(0);
        //写入数据
        for (int y = 0; y < head.length; y++) {
            hssfCell2 = setCell(hssfCell2, row2, Headstyle, head[y], y);
            sheet.setColumnWidth(y, length[y]);
        }
        //设置高度
        row2.setHeightInPoints(30);
        //写入数据
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell hssfCell3 = row3.createCell(0);


        //写入数据
        for (Object string : data) {
            if (head.length == i) {
                //行数添加一行
                rows++;
                //换行后初始化后为1
                i = 0;
                //创建行
                row3 = sheet.createRow(rows);
                //放入数据
                hssfCell3 = setCell(hssfCell3, row3, style, string, i);
//                index++;
//                count++;
            } else {
                if (i == 0) {
                    //创建行
                    row3 = sheet.createRow(rows);

//                    count ++;

                }
//                index++;
                //放入数据
                hssfCell3 = setCell(hssfCell3, row3, style, string, i);


            }
            //设置高度
            row3.setHeightInPoints(25);
            i++;
        }
        //写入数据
        HSSFCellStyle footStyle = footStyle(wb,wb.createCellStyle());
        footStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        footStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        footStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        footStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        try {
            ZipEntry entry = new ZipEntry(num+exportName);
            zipOutputStream.putNextEntry(entry);
            wb.write(zipOutputStream);
        }catch (IOException e){

        }finally {
            data.clear();//清除集合的所有内容

        }
        return zipOutputStream;
    }

    /**
     * 底部样式
     * @param cellStyleTitle
     * @return
     */
    public static HSSFCellStyle footStyle(HSSFWorkbook wb,HSSFCellStyle cellStyleTitle) {


        // 指定单元格居中对齐
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 指定单元格垂直居中对齐
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 指定当单元格内容显示不下时自动换行
        cellStyleTitle.setWrapText(true);
        // 设置单元格字体
        HSSFFont font = wb.createFont();

        font.setFontHeight((short) 200);

        cellStyleTitle.setFont(font);
        return cellStyleTitle;

    }

    /**
     * 头部样式
     */
    public static HSSFCellStyle headStyle(HSSFWorkbook wb, HSSFCellStyle cellStyleTitle) {
        //设置背景颜色
        cellStyleTitle.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
        //填充背景颜色
        cellStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 指定单元格居中对齐
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 指定单元格垂直居中对齐
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 指定当单元格内容显示不下时自动换行
        cellStyleTitle.setWrapText(true);
        //设置下边框
        cellStyleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        设置左边边框
        cellStyleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//设置右边框
        cellStyleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//设置上边框
        cellStyleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置单元格字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);
        return cellStyleTitle;
    }


    /**
     * 数据样式
     */
    public static HSSFCellStyle dataStyle(HSSFCellStyle cellStyleTitle) {

        // 指定单元格居中对齐
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 指定单元格垂直居中对齐
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 指定当单元格内容显示不下时自动换行
        cellStyleTitle.setWrapText(true);
        //设置下边框
        cellStyleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        设置左边边框
        cellStyleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//设置右边框
        cellStyleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//设置上边框
        cellStyleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        return cellStyleTitle;
    }



    /**
     * 简写单元格创建
     * @param cell           列
     * @param row            行
     * @param cellStyleTitle 样式
     * @param title          内容
     * @param num            列数
     * @return
     */
    public static HSSFCell setCell(HSSFCell cell, HSSFRow row, HSSFCellStyle cellStyleTitle, Object title, int num) {
        cell = row.createCell(num);
        cell.setCellStyle(cellStyleTitle);

        if (title == null || "".equals(title)) {
            cell.setCellValue(new HSSFRichTextString(""));
        } else {
            cell.setCellValue(new HSSFRichTextString(title.toString()));
        }
        return cell;
    }

}
