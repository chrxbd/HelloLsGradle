package tools;

import jxl.Workbook;
import jxl.write.*;

import java.io.File;

public class ExcelWriter {

    public static boolean write(String[] header, String[][] data, String path) {
        WritableWorkbook book = null;
        try{
            book = Workbook.createWorkbook(new File(path));
            //生成名为eccif的工作表，参数0表示第一页
            WritableSheet sheet = book.createSheet("sheet", 0);
            //表头导航
            for(int j=0; j<header.length; j++){
                Label label = new Label(j, 0, header[j]);
                sheet.addCell(label);
            }
            for(int i=0; i < data.length; i++){
                for (int j = 0; j < data[i].length; j++) {
                    sheet.addCell(new Label(j, i + 1, data[i][j]));
                }
            }
            // 写入数据并关闭文件
            book.write();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }finally{
            if(book!=null){
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
