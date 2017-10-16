package Process;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by baidu on 2017/10/16.
 */
public class Classify {
    public boolean process(String[][] data) {
        List<String[]> result = new LinkedList<>();
        for (String[] item: data) {
            if (item[3].equals("lsh") || item[5].equals("lsh")) {
                result.add(item);
            }
        }
        return true;
    }
}
