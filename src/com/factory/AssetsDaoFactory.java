package com.factory;

import com.db.DBConnection;
import com.service.impl.AssetsServiceImpl;
import com.service.impl.PurchaseServiceImpl;
import com.service.impl.UsersServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class AssetsDaoFactory {
    static Connection conn = null;
    static {
        conn = DBConnection.getConnect("C3p0");
    }
    public static UsersServiceImpl getUsersServiceImpl() {
        return new UsersServiceImpl(conn);
    }

    public static PurchaseServiceImpl getPurchaseServiceImpl() {
        return new PurchaseServiceImpl(conn);
    }

    public static AssetsServiceImpl getAssetsServiceImpl() {
        return new AssetsServiceImpl(conn);
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
