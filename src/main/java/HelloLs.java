import Process.classify.MainProcess;
import Process.tbtoexl.HTMLTOExcel;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import tools.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

import Process.ExportProcess;

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
                MainProcess.process(f.getAbsoluteFile(), fd.getCurrentDirectory().toString());
                JOptionPane.showMessageDialog(null, "操作完成");
                //HTMLTOExcel.toExcel(path, "/Users/baidu/Desktop/testss.xls");
            }
        });
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fd = new JFileChooser();
                fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fd.showOpenDialog(null);
                File f = fd.getSelectedFile();
                File[] files = f.listFiles();
                String[] contents = new String[files.length];
                int i = 0;
                for (File file : files) {
                    contents[i] = tools.FileReader.fileGetContents(file.getAbsolutePath());
                    i++;
                    //System.out.println(file.getAbsolutePath());
                }

                ExportProcess.process(contents, f.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "操作完成");

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
