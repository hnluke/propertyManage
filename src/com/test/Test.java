package com.test;

import com.db.DBConnection;
import com.db.ExcelInDbo;
import com.pojo.Purchase;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        int result = 0;
        File file = null;
        String path = null;
       JFileChooser fileChooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
        System.out.println(fsv.getHomeDirectory());                //得到桌面路径
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("请选择要上传的文件...");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        result = fileChooser.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == result) {
                       path=fileChooser.getSelectedFile().getPath();
                     System.out.println("path: "+path);
              }

        List<Purchase> list = new ArrayList<Purchase>();

        //list = ExcelInDbo.getAllByExcel(path);
          /*//得到数据库表中所有的数据
          List<StuEntity> listDb=StuService.getAllByDb();*/
        Connection conn = DBConnection.getConnect("c3p0");
        String sql = "";
        PreparedStatement ps = null;
        for(Purchase purchase : list) {
            sql = "insert into purchase (p_code, p_assname, p_unit, p_num, p_type," +
                    " p_model, p_vouno, p_prices) values ('" + purchase.getP_code() +
                    "', '" + purchase.getP_assname() + "', '" + purchase.getP_unit() +
                    "', " + purchase.getP_num() + ", '" + purchase.getP_type() +
                    "', '" + purchase.getP_model() + "', '" + purchase.getP_vouno() +
                    "', " + purchase.getP_prices() + ")";
            try {
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("导入失败");
                e.printStackTrace();
            }

            System.out.println("导入成功");
        }
    }


}
