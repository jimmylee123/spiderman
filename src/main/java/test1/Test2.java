package test1;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;


public class Test2 {

	public static void main(String[] args) {
		 String excelPath = "C:\\Users\\Administrator\\Desktop\\12.xls";

	        try {
	            //String encoding = "GBK";
	            File excel = new File(excelPath);
	            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

	                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
	                Workbook wb;
	                //根据文件后缀（xls/xlsx）进行判断
//	                if ( "xls".equals(split[1])){
//	                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
//	                    wb = new HSSFWorkbook(fis);
//	                }else if ("xlsx".equals(split[1])){
//	                }else {
//	                    System.out.println("文件类型错误!");
//	                    return;
//	                }
	                wb = new XSSFWorkbook(excel);

	                //开始解析
	                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

	                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
	                int lastRowIndex = sheet.getLastRowNum();
	                System.out.println("firstRowIndex: "+firstRowIndex);
	                System.out.println("lastRowIndex: "+lastRowIndex);

	                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
	                    System.out.println("rIndex: " + rIndex);
	                    Row row = sheet.getRow(rIndex);
	                    if (row != null) {
	                        int firstCellIndex = row.getFirstCellNum();
	                        int lastCellIndex = row.getLastCellNum();
	                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
	                            Cell cell = row.getCell(cIndex);
	                            if (cell != null) {
	                                System.out.println(cell.toString());
	                            }
	                        }
	                    }
	                }
	            } else {
	                System.out.println("找不到指定的文件");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
