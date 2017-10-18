package Process.classify;

import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import tools.DateUtil;
import tools.ExcelWriter;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by baidu on 2017/10/18.
 */
public class MainProcess {

    public static boolean process(File file, String path) {
        try {
            HashMap<String, List<String[]>> result = new HashMap<>();
            List<String[]> contents = MainProcess.getContents(file);
            for (String[] content: contents) {
                if (content != null && content.length > 9) {
                    if (result.get(content[1]) == null) {
                        List<String[]> subRs = new LinkedList<>();
                        result.put(content[1], subRs);
                    }
                    result.get(content[1]).add(content);
                }
            }
            List<String[]> midSummary = new LinkedList<>();
            String[] header = new String[]{"公司代码","凭证类型","凭证编号","用户名称","凭证抬头文本","凭证日期","输入时间","过帐日期","冲销关于","参照","字段参考关键","货币","冲销原因"};
            Set<String> keys = result.keySet();
            for (String key : keys) {
                String[][] calssifyRs = new String[result.get(key).size()][15];
                int i = 0;
                for (String[] temp : result.get(key)) {
                    calssifyRs[i] = temp;
                    i++;
                }
                ExcelWriter.write(header, calssifyRs, path + "/" + key + ".xls");
                List<String[]> megerRs = mergeProcess(calssifyRs);
                if (megerRs != null) {
                    midSummary.addAll(megerRs);
                }
            }
            ExcelWriter.write(header, midSummary, path + "/result.xls");
            return true;
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static List<String[]> getContents(File xlsFile) throws BiffException, IOException {
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
            DateCell dc;
            // 读取数据
            for (int row = 0; row < rows; row++) {
                if (sheet.getCell(3, row).getContents().equals("LIUSHUANGH")
                        || sheet.getCell(9, row).getContents().equals("LIUSHUANGH")) {
                    String[] subRs = new String[cols];
                    for (int col = 0; col < cols; col++) {
                        if (sheet.getCell(col, row).getType() == CellType.DATE) {
                            dc = (DateCell)sheet.getCell(col, row);
                            try {
                                subRs[col] = DateUtil.formatDate(dc.getDate(), "yyyy/MM/DD");
                            } catch (ParseException e) {
                                subRs[col] = sheet.getCell(col, row).getContents();
                                e.printStackTrace();
                            }
                        } else {
                            subRs[col] = sheet.getCell(col, row).getContents();
                        }
                    }
                    result.add(subRs);
                }
            }
            System.out.println(result.size());
            return result;
        }
        workbook.close();
        return null;
    }

    private static List<String[]> mergeProcess(String[][] raw) {
        if (raw == null || raw.length <= 0) {
            return null;
        }
        List<String[]> result = new LinkedList<>();
        int rowCount = raw.length;
        String[] temp = raw[0];
        Long pzbh = Long.parseLong(temp[2]);
        Long needBh = pzbh + 1;
        String[] current;
        for (int r = 1; r < rowCount; r++) {
            current = raw[r];
            if (Long.parseLong(current[2]) == needBh) {
                needBh++;
            } else {
                if (needBh - pzbh > 1) {
                    temp[2] = pzbh + "至" + (needBh - 1);
                }
                result.add(temp);
                temp = current;
                pzbh = Long.parseLong(temp[2]);
                needBh = pzbh + 1;
            }
        }
        if (needBh - pzbh > 1) {
            temp[2] = pzbh + "至" + (needBh - 1);
        }
        result.add(temp);
        return result;
    }
}
