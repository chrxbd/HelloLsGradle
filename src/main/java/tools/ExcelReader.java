package tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 *
 */
public class ExcelReader {

    public static List<String[]> read(File xlsFile) throws BiffException, IOException {
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
            List<String[]> result = new LinkedList<>();
            // 读取数据
            for (int row = 0; row < rows; row++) {
                String[] subRs = new String[cols];
                for (int col = 0; col < cols; col++) {
                    subRs[col] = sheet.getCell(col, row).getContents();
                }
                result.add(subRs);
            }
            return result;
        }
        workbook.close();
        return null;
    }
}
