package com.db;

import com.pojo.Purchase;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelInDbo {

//    public static List<Purchase> getAllByExcel(String file) {
//        List<Purchase> list = new ArrayList<Purchase>();
//        try {
//            Workbook rwb = Workbook.getWorkbook(new File(file));
//            Sheet rs = rwb.getSheet("采购单");// 或者rwb.getSheet(0)
//            int clos = rs.getColumns();// 得到所有的列
//            int rows = rs.getRows();// 得到所有的行
//            System.out.println(clos + " rows:" + rows);
//            for (int i = 1; i < rows; i++) {
//                for (int j = 0; j < clos; j++) {
//                    // 第一个是列数，第二个是行数
//                    //j = j + 1;
//                    String p_code = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
//                    //if (userId == null || "".equals(userId))userId = "0";
//                    // 所以这里得j++
//                    String p_assname = rs.getCell(j++, i).getContents();
//                    String p_unit = rs.getCell(j++, i).getContents();
//                    String p_num = rs.getCell(j++, i).getContents();
//                    String p_type = rs.getCell(j++, i).getContents();
//                    String p_model = rs.getCell(j++, i).getContents();
//                    String p_vouno = rs.getCell(j++, i).getContents();
//                    String p_prices = rs.getCell(j++, i).getContents();
//                    Purchase purchase = new Purchase();
//                    purchase.setP_code(p_code);
//                    purchase.setP_assname(p_assname);
//                    purchase.setP_unit(p_unit);
//                    purchase.setP_num(Integer.parseInt(p_num));
//                    purchase.setP_type(p_type);
//                    purchase.setP_model(p_model);
//                    purchase.setP_vouno(p_vouno);
//                    purchase.setP_prices(Double.parseDouble(p_prices));
//
//                    list.add(purchase);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
