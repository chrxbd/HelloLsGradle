import Process.tbtoexl.HTMLTOExcel;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import tools.ExcelReader;
import tools.ExcelWriter;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

import static Process.tbtoexl.ConvertHtml2Excel.table2Excel;

public class HelloLs extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton export;
    private JButton tb;

    public HelloLs() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        tb.addActionListener(e -> {
            JFileChooser fd = new JFileChooser();
            //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fd.showOpenDialog(null);
            File f = fd.getSelectedFile();
            if (f != null) {
                ///读取classpath目录下面的路径
                String path = f.getAbsolutePath();
                try {
                    HTMLTOExcel.toExcel(path, "/Users/baidu/Desktop/testss.xls");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
