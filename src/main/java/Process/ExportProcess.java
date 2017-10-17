package Process;

import tools.ExcelWriter;

/**
 * Created by baidu on 2017/10/17.
 */
public class ExportProcess {

    public static boolean process(String[] contents, String path) {
        String[][] result = new String[contents.length][5];

        for (int i = 0; i < contents.length; i++) {
            String[] temp = contents[i].split("[<>]");
            for (int j = 0; j < temp.length - 1; j++) {
                switch (temp[j]) {
                    case "BuyerName":
                        result[i][0] = temp[j + 1];
                        break;
                    case "SellerName":
                        result[i][1] = temp[j + 1];
                        break;
                    case "GoodsTaxRate":
                        result[i][2] = temp[j + 1];
                        break;
                    case "GoodsSE":
                        result[i][3] = temp[j + 1];
                        break;
                    case "ReqBillNo":
                        result[i][4] = temp[j + 1];
                        break;
                }
            }
        }
        String[] header = new String[]{"buyerName", "SellerName", "GoodsTaxRate", "税额"};
        ExcelWriter.write(header, result, path + "/result.xls");
        return true;
    }
}
