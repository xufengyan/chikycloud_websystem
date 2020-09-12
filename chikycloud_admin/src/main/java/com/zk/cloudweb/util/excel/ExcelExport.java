package com.zk.cloudweb.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/** 
 * EXCEL报表工具类. 
 *  
 * @author Jeelon 
 */  
public class ExcelExport {  
  
    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;
  
    /** 
     * @param wb 
     * @param sheet  
     */  
    public ExcelExport(HSSFWorkbook wb, HSSFSheet sheet) {
        // super();  
        this.wb = wb;  
        this.sheet = sheet;  
    }  
  
    /** 
     * 创建通用EXCEL头部 
     *  
     * @param headString 
     *            头部显示的字符 
     * @param colSum 
     *            该报表的列数 
     */  
    @SuppressWarnings({ "deprecation", "unused" })  
    public void createNormalHead(String headString, int colSum) {  
        HSSFRow row = sheet.createRow(0);
        // 设置第一行  
        HSSFCell cell = row.createCell(0);
        // row.setHeight((short) 1000);  
  
        // 定义单元格为字符串类型  
        cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理
        cell.setCellValue(new HSSFRichTextString(headString));
  
        // 指定合并区域  
        /** 
         * public Region(int rowFrom, short colFrom, int rowTo, short colTo) 
         */  
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));
  
        // 定义单元格格式，添加单元格表样式，并添加到工作簿  
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格水平对齐类型  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行  

        // 设置单元格字体  
        HSSFFont font = wb.createFont();
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // font.setFontName("宋体");  
        // font.setFontHeight((short) 600);  
        // cellStyle.setFont(font);  
        cell.setCellStyle(cellStyle);  
    }  
  
    
    
    /** 
     * 创建通用EXCEL头部 
     *  
     * @param headString 
     *            头部显示的字符 
     * @param colSum 
     *            该报表的列数 
     */  
    @SuppressWarnings({ "deprecation", "unused" })  
    public void createNormalHead2(String headString, int colSum) {  
        HSSFRow row = sheet.createRow(0);
        // 设置第一行  
        HSSFCell cell = row.createCell(0);
        // row.setHeight((short) 1000);  
  
        // 定义单元格为字符串类型  
        cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理
        cell.setCellValue(new HSSFRichTextString(headString));
  
       
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));
  
        // 定义单元格格式，添加单元格表样式，并添加到工作簿  
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格水平对齐类型  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行  
  
        //设置背景颜色
        cellStyle.setFillForegroundColor((short)41);
        //填充背景颜色
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        // 设置单元格字体  
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");  
        font.setFontHeight((short) 200);  
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);  
        row.setHeightInPoints(35);
    } 
    
    /** 
     * 创建通用EXCEL头部 
     *  
     * @param headString 
     *            头部显示的字符 
     * @param colSum 
     *            该报表的列数 
     */  
    @SuppressWarnings({ "deprecation", "unused" })  
    public void createNormalHead3(String headString, int colSum) {  
        HSSFRow row = sheet.createRow(0);
        // 设置第一行  
        HSSFCell cell = row.createCell(0);
        // row.setHeight((short) 1000);  
  
        // 定义单元格为字符串类型  
        cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理
        cell.setCellValue(new HSSFRichTextString(headString));
  
       
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));
  
        // 定义单元格格式，添加单元格表样式，并添加到工作簿  
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格水平对齐类型  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行  
        //设置背景颜色
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        //填充背景颜色
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        setBorderStyle(1,new CellRangeAddress(0,0,0,colSum),sheet,wb);

        // 设置单元格字体  
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)14);
        font.setColor(HSSFColor.RED.index);//HSSFColor.VIOLET.index //字体颜色
//        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);  
        row.setHeightInPoints(35);

    }
    /**
     * 创建通用EXCEL头部
     *
     * @param headString
     *            头部显示的字符
     * @param colSum
     *            该报表的列数
     */
    @SuppressWarnings({ "deprecation", "unused" })
    public void createNormalHead4(String headString, int colSum) {
        HSSFRow row = sheet.createRow(0);
        // 设置第一行
        HSSFCell cell = row.createCell(0);
        // row.setHeight((short) 1000);

        // 定义单元格为字符串类型
        cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理
        cell.setCellValue(new HSSFRichTextString(headString));


        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));

        // 定义单元格格式，添加单元格表样式，并添加到工作簿
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格水平对齐类型
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行
        //设置背景颜色
//        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
        //填充背景颜色
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // 设置单元格字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        row.setHeightInPoints(35);
    }

    /**
     * 创建通用EXCEL头部
     *
     * @param headString
     *            头部显示的字符
     * @param colSum
     *            该报表的列数
     */
    @SuppressWarnings({ "deprecation", "unused" })
    public void createNormalHead5(String headString, int colSum) {
        HSSFRow row = sheet.createRow(0);
        // 设置第一行
        HSSFCell cell = row.createCell(0);
        // row.setHeight((short) 1000);

        // 定义单元格为字符串类型
        cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理
        cell.setCellValue(new HSSFRichTextString(headString));


        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));

        // 定义单元格格式，添加单元格表样式，并添加到工作簿
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格水平对齐类型
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行
        //设置背景颜色
//        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
        //填充背景颜色
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // 设置单元格字体
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short) 240);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        row.setHeightInPoints(15);
    }

    /** 
     * 创建通用报表第二行 
     *  
     * @param params 
     *            统计条件数组 
     * @param colSum 
     *            需要合并到的列索引 
     */  
    @SuppressWarnings("deprecation")  
    public void createNormalTwoRow(String[] params, int colSum) {  
        // 创建第二行  
        HSSFRow row1 = sheet.createRow(1);
  
        row1.setHeight((short) 400);  
  
        HSSFCell cell2 = row1.createCell(0);
  
        cell2.setCellType(HSSFCell.ENCODING_UTF_16);
        cell2.setCellValue(new HSSFRichTextString("时间：" + params[0] + "至"
                + params[1]));  
  
        // 指定合并区域  
        /** 
         * public Region(int rowFrom, short colFrom, int rowTo, short colTo) 
         */  
        sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) colSum));
  
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行  
  
        // 设置单元格字体  
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");  
        font.setFontHeight((short) 250);  
        cellStyle.setFont(font);  
  
        cell2.setCellStyle(cellStyle);  
    }  
  
    /** 
     * 设置报表标题 
     *  
     * @param columHeader 
     *            标题字符串数组 
     */  
    public void createColumHeader(String[] columHeader) {  
  
        // 设置列头 在第三行  
        HSSFRow row2 = sheet.createRow(2);
  
        // 指定行高  
        row2.setHeight((short) 600);  
  
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行  
  
        // 单元格字体  
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");  
        font.setFontHeight((short) 250);  
        cellStyle.setFont(font);  
  
        // 设置单元格背景色  
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
  
        HSSFCell cell3 = null;
  
        for (int i = 0; i < columHeader.length; i++) {  
            cell3 = row2.createCell(i);  
            cell3.setCellType(HSSFCell.ENCODING_UTF_16);
            cell3.setCellStyle(cellStyle);  
            cell3.setCellValue(new HSSFRichTextString(columHeader[i]));
        }  
    }  
  
    /** 
     * 创建内容单元格 
     *  
     * @param wb 
     *            HSSFWorkbook 
     * @param row 
     *            HSSFRow 
     * @param col 
     *            short型的列索引 
     * @param align 
     *            对齐方式 
     * @param val 
     *            列值 
     */  
    public void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, short align,
                           String val) {
        HSSFCell cell = row.createCell(col);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(new HSSFRichTextString(val));
        HSSFCellStyle cellstyle = wb.createCellStyle();
        cellstyle.setAlignment(align);  
        cell.setCellStyle(cellstyle);  
    }  
  
    /** 
     * 创建合计行 
     *  
     * @param colSum 
     *            需要合并到的列索引 
     * @param cellValue 
     */  
    @SuppressWarnings("deprecation")  
    public void createLastSumRow(int colSum, String[] cellValue) {  
  
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行  
  
        // 单元格字体  
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");  
        font.setFontHeight((short) 250);  
        cellStyle.setFont(font);  
        // 获取工作表最后一行  
        HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
        HSSFCell sumCell = lastRow.createCell(0);
  
        sumCell.setCellValue(new HSSFRichTextString("合计"));
        sumCell.setCellStyle(cellStyle);  
        // 合并 最后一行的第零列-最后一行的第一列  
        sheet.addMergedRegion(new Region(sheet.getLastRowNum(), (short) 0,
                sheet.getLastRowNum(), (short) colSum));// 指定合并区域  
  
        for (int i = 2; i < (cellValue.length + 2); i++) {  
            // 定义最后一行的第三列  
            sumCell = lastRow.createCell(i);  
            sumCell.setCellStyle(cellStyle);  
            // 定义数组 从0开始。  
            sumCell.setCellValue(new HSSFRichTextString(cellValue[i - 2]));
        }  
    }  
  
	/**
	 * 单元格样式生成
	* @author LiXiang  @date 2017年8月1日 下午4:55:45
	 */
	public static HSSFCellStyle getHSSFStyle(HSSFCellStyle cellStyleTitle, HSSFWorkbook wb, short color) {
		
        //设置背景颜色
        cellStyleTitle.setFillForegroundColor(color);
        //solid 填充  foreground  前景色
        cellStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置边框:
        cellStyleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        // 指定单元格居中对齐  
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 指定单元格垂直居中对齐  
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 指定当单元格内容显示不下时自动换行  
        cellStyleTitle.setWrapText(true);  
        // ------------------------------------------------------------------  
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 指定单元格居中对齐  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 指定单元格垂直居中对齐  
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 指定当单元格内容显示不下时自动换行  
        cellStyle.setWrapText(true);  
        // ------------------------------------------------------------------  
        // 设置单元格字体  
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");  
        font.setFontHeight((short) 200);  
        cellStyleTitle.setFont(font);  
		return cellStyleTitle;
	}
    /** 
     * 输入EXCEL文件 
     *  
     * @param fileName 
     *            文件名 
     */  
    public void outputExcel(String fileName) {  
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(new File(fileName));  
            wb.write(fos);  
            fos.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * @return the sheet 
     */  
    public HSSFSheet getSheet() {
        return sheet;  
    }  
  
    /** 
     * @param sheet 
     *            the sheet to set 
     */  
    public void setSheet(HSSFSheet sheet) {
        this.sheet = sheet;  
    }  
  
    /** 
     * @return the wb 
     */  
    public HSSFWorkbook getWb() {
        return wb;  
    }  
  
    /** 
     * @param wb 
     *            the wb to set 
     */  
    public void setWb(HSSFWorkbook wb) {
        this.wb = wb;  
    }
    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setBorderStyle(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){
        RegionUtil.setBorderBottom(border, region, sheet, wb);   //下边框
        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框
        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框
        RegionUtil.setBorderTop(border, region, sheet, wb);      //上边框


    }

    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setBorder(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){

        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框
        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框
        RegionUtil.setBorderTop(border, region, sheet, wb);      //上边框


    }


    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setleftringht(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){

        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框
        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框



    }

    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setleft(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){

        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框




    }

    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setringht(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){


        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框



    }

    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setringhtbutton(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){


        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框
        RegionUtil.setBorderBottom(border, region, sheet, wb);   //下边框



    }

    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setbutton(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){



        RegionUtil.setBorderBottom(border, region, sheet, wb);   //下边框



    }
    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setleftbutton(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){

        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框

        RegionUtil.setBorderBottom(border, region, sheet, wb);   //下边框



    }

    /**
     *
     * @param border
     * @param region
     * @param sheet
     * @param wb
     */
    @SuppressWarnings("warring")
    public static  void setleributton(int border, CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook wb){

        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框
        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框
        RegionUtil.setBorderBottom(border, region, sheet, wb);   //下边框


    }

    public static  HSSFCellStyle cellmiddleStyle(HSSFWorkbook wb){
        //  数据居中
        HSSFCellStyle cellStyle = wb.createCellStyle();        //表格样式
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        Font cellFont = wb.createFont();

        cellFont.setItalic(false);                     // 设置字体为斜体字
        cellFont.setColor(Font.COLOR_NORMAL);            // 将字体设置为“红色”
        cellFont.setFontHeightInPoints((short)10);    // 将字体大小设置为18px
        cellFont.setFontName("宋体");             // 字体应用到当前单元格上

        cellStyle.setFont(cellFont);
        cellStyle.setWrapText(true);//设置自动换行
        return cellStyle;
    }

    public static  HSSFCellStyle cellsongStyle(HSSFWorkbook wb){
        //        字体增粗
        HSSFCellStyle cellStyle1 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle1.setWrapText(true);//设置自动换行
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
        cellStyle1.setFont(font);
        return cellStyle1;
    }

    public static  HSSFCellStyle cellLeftStyle(HSSFWorkbook wb){
        //        数据靠左
        HSSFCellStyle cellLStyle = wb.createCellStyle();
        Font cellFont1 = wb.createFont();
        cellFont1.setFontHeightInPoints((short)10);
        cellLStyle.setFont(cellFont1);
        return cellLStyle;
    }

    public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress region, HSSFCellStyle cs) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) row = sheet.createRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                HSSFCell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                    cell.setCellValue("");
                }
                cell.setCellStyle(cs);
            }
        }
    }

}  

