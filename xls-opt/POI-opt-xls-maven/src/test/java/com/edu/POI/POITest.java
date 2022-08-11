package com.edu.POI;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class POITest {
    String path="C:\\Users\\归浩乐\\Desktop\\资料\\";

    /**
     * 需求：向已经有的Excel中追加数据
     * @throws Exception
     */
    @Test
    public void writeExcel() throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = workbook.createSheet("GuiHaoLe");
        XSSFRow xssfRow = xssfSheet.createRow(0);
        XSSFCell xssfCell = xssfRow.createCell(0);
        xssfCell.setCellValue("日期");
        FileOutputStream fops = new FileOutputStream(path+"GuiHaoLeWork.xlsx");
        workbook.write(fops);
    }
    @Test
    public void appendExcel() throws Exception{
        FileInputStream fis = new FileInputStream(path + "GuiHaoLeWork.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("GuiHaoLe");
        XSSFRow rowOne = xssfSheet.createRow(2);
        XSSFCell cellOne = rowOne.createCell(2);
        cellOne.setCellValue(new Date());
        FileOutputStream fos = new FileOutputStream(path + "GuiHaoLeWork.xlsx");
        xssfWorkbook.write(fos);
        fis.close();
        fos.close();
    }
    @Test
    public void test03() throws Exception{
        // 创建新的Excel 工作簿
        Workbook workbook = new HSSFWorkbook();
        // 如要新建一名为"会员登录统计"的工作表，其语句为：
        Sheet sheet = workbook.createSheet("OPI03统计表");
        // 创建行（row 1）
        Row row = sheet.createRow(0);
        // 创建单元格（col 1-1）
        Cell rowCell11 = row.createCell(0);
        rowCell11.setCellValue("我来学习POI");
        Cell rowCell12 = row.createCell(1);
        rowCell12.setCellValue("我测试一下");
        // 创建行（row 2）
        Row row2 = sheet.createRow(1);
        // 创建单元格（col 2-1）
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        //创建单元格（第三列）
        Cell cell22 = row2.createCell(1);
        String s = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(s);
        FileOutputStream fileOutputStream = new FileOutputStream(path+"OPIO3.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("文件生成成功");
    }
    @Test
    public void testWrite07() throws IOException {

        // 创建新的Excel 工作簿, 只有对象变了
        Workbook workbook = new XSSFWorkbook();

        // 如要新建一名为"会员登录统计"的工作表，其语句为：
        Sheet sheet = workbook.createSheet("OPI03统计表");

        // 创建行（row 1）
        Row row1 = sheet.createRow(0);

        // 创建单元格（col 1-1）
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("今日新增关注");

        // 创建单元格（col 1-2）
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(666);

        // 创建行（row 2）
        Row row2 = sheet.createRow(1);

        // 创建单元格（col 2-1）
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");

        //创建单元格（第三列）
        Cell cell22 = row2.createCell(1);
        String dateTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(dateTime);

        // 新建一输出文件流（注意：要先创建文件夹）
        FileOutputStream out = new FileOutputStream(path+"OPI07.xlsx");
        // 把相应的Excel 工作簿存盘
        workbook.write(out);
        // 操作结束，关闭文件
        out.close();

        System.out.println("文件生成成功");
    }
    @Test
    public void testWrite03BigData() throws IOException {
        //记录开始时间
        long begin = System.currentTimeMillis();

        //创建一个SXSSFWorkbook
        Workbook workbook = new HSSFWorkbook();

        //创建一个sheet
        Sheet sheet = workbook.createSheet();

        for (int rowNum = 0; rowNum <65536 ; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum <10 ; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }

        System.out.println("done");
        FileOutputStream out = new FileOutputStream(path+"bigdata03.xls");
        workbook.write(out);
        // 操作结束，关闭文件
        out.close();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000);
    }
    @Test
    public void testWrite07BigData() throws IOException {
        //记录开始时间
        long begin = System.currentTimeMillis();

        //创建一个XSSFWorkbook
        Workbook workbook = new XSSFWorkbook();

        //创建一个sheet
        Sheet sheet = workbook.createSheet();

        //xls文件最大支持65536行
        for (int rowNum = 0; rowNum < 100000; rowNum++) {
            //创建一个行
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {//创建单元格
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }

        System.out.println("done");
        FileOutputStream out = new FileOutputStream(path+"bigdata07.xlsx");
        workbook.write(out);
        // 操作结束，关闭文件
        out.close();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000);

    }
    @Test
    public void testWrite07BigDataFast() throws IOException {
        //记录开始时间
        long begin = System.currentTimeMillis();

        //创建一个SXSSFWorkbook
        Workbook workbook = new SXSSFWorkbook();

        //创建一个sheet
        Sheet sheet = workbook.createSheet();

        //xls文件最大支持65536行
        for (int rowNum = 0; rowNum < 100000; rowNum++) {
            //创建一个行
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {//创建单元格
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }

        System.out.println("done");
        FileOutputStream out = new FileOutputStream(path+"bigdata07-fast.xlsx");
        workbook.write(out);
        // 操作结束，关闭文件
        out.close();

        //清除临时文件
        ((SXSSFWorkbook)workbook).dispose();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000);
    }
    //读取表格
    @Test
    public void testLearn03Data()throws Exception{
        FileInputStream fileInputStream = new FileInputStream(path + "OPIO3.xls");
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        Sheet sheetAt = workbook.getSheetAt(0);
        Row row = sheetAt.getRow(0);
        Cell cell = row.getCell(0);
        System.out.println(cell.getStringCellValue());
        fileInputStream.close();
    }
    @Test
    public void testRead07() throws Exception{
        InputStream is = new FileInputStream(path+"OPI07.xlsx");

        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取第一行第一列
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);

        // 输出单元内容
        System.out.println(cell.getStringCellValue());

        // 操作结束，关闭文件
        is.close();
    }

    @Test
    public void UtilLearnXls()throws Exception{
        FileInputStream fileInputStream = new FileInputStream(path + "会员消费商品明细表.xls");
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row rowOne = sheet.getRow(0);
        //读取标题行第一行
        if (rowOne!=null) {
            int numberOfCells = rowOne.getPhysicalNumberOfCells();
            for (int numberCell = 0; numberCell <numberOfCells ; numberCell++) {
                Cell oneCell = rowOne.getCell(numberCell);
                if (oneCell!=null) {
                    //列的类型枚举类型的数字不同数字代表不同的类型
                    int cellType = oneCell.getCellType();
                    String cellValue = oneCell.getStringCellValue();
                    System.out.print(cellValue + "|");
                }
            }
            System.out.println();
        }
        //读取其他行数据
        int numberOfRows = sheet.getPhysicalNumberOfRows();
        for (int numberRow = 1; numberRow < numberOfRows; numberRow++) {
            Row row = sheet.getRow(numberRow);
            if(row!=null){
                int numberOfCells = row.getPhysicalNumberOfCells();
                for (int numberCell = 0; numberCell < numberOfCells; numberCell++) {
                    System.out.print("【" + (numberRow + 1) + "-" + (numberCell + 1) + "】");
                    Cell cell = row.getCell(numberCell);
                    if (cell!=null) {
                        //读取列
                        int cellType = cell.getCellType();
                        String cellStr="";
                        switch (cellType){
                            case HSSFCell.CELL_TYPE_STRING:  //string
                                System.out.print("【STRING】");
                                cellStr = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:   //boolean
                                System.out.print("【BOOLEAN】");
                                cellStr = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:  //空
                                System.out.print("【BLANK】");
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:  //数字类型
                                System.out.print("【NUMERIC】");
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期
                                    System.out.print("【日期】");
                                    Date date = cell.getDateCellValue();
                                    cellStr = new DateTime(date).toString("yyyy-MM-dd");
                                } else {
                                    // 不是日期格式，则防止当数字过长时以科学计数法显示
                                    System.out.print("【转换成字符串】");
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cellStr = cell.toString();
                                }
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                System.out.print("【数据类型错误】");
                                break;
                        }
                        System.out.println(cellStr);
                    }
                }
            }
        }
        fileInputStream.close();
    }

    @Test
    public void UtilGongShiXls()throws Exception{
        InputStream is = new FileInputStream(path + "计算公式.xls");
        Workbook workbook = new HSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(4);
        Cell cell = row.getCell(0);
        //公式计算器
        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        int cellType = cell.getCellType();
        switch (cellType){
            case Cell.CELL_TYPE_FORMULA:
                //得到公式
                String formula = cell.getCellFormula();
                System.out.println(formula);
                //计算
                CellValue evaluate = formulaEvaluator.evaluate(cell);
                //String cellValue = String.valueOf(evaluate.getNumberValue());
                String cellValue = evaluate.formatAsString();
                System.out.println(cellValue);
                break;
        }
    }
}
