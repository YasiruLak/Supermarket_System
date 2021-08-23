package controller;

import db.DbConnection;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoveItemsPgController {
    public String itemCode;
    public String quntity;

    public TextField txtCode;
    public TextField txtQty;
    public TextField txtCode1;
    public TextField txtQty1;

    public void initialize() throws SQLException, ClassNotFoundException {
        MostMoveble();
        LeastMoveble();

    }

    private void LeastMoveble() throws SQLException, ClassNotFoundException {
        PreparedStatement stm  = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order Detail` ORDER BY Qty DESC");
        ResultSet rst = stm.executeQuery();

        while (rst.next()){
            itemCode=(rst.getString(1));
            quntity=(rst.getString(3));

        }
        txtCode1.setText(itemCode);
        txtQty1.setText(quntity);
    }

    private void MostMoveble() throws SQLException, ClassNotFoundException {
            PreparedStatement stm  = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order Detail` ORDER BY Qty ASC");
            ResultSet rst = stm.executeQuery();

            while (rst.next()){
                itemCode=(rst.getString(1));
                 quntity=(rst.getString(3));

            }
            txtCode.setText(itemCode);
            txtQty.setText(quntity);
    }

}
