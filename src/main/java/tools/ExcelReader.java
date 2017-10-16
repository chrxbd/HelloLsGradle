package tools;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 *
 */
public class ExcelReader {

    public static String[][] read(File xlsFile) throws BiffException, IOException {
        if (xlsFile == null) {
            return null;
        }
        // 获得工作簿对象
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        // 获得工作表
        Sheet sheet = workbook.getSheet(0);
        // 遍历工作表
        if (sheet != null) {
            // 获得行数
            int rows = sheet.getRows();
            // 获得列数
            int cols = sheet.getColumns();
            String[][] result = new String[rows][cols];
            // 读取数据
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    result[row][col] = sheet.getCell(col, row).getContents();
                }
            }
            return result;
        }
        workbook.close();
        return null;
    }
}
