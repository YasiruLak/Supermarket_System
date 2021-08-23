package controller;

import db.DbConnection;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerIncomePgController {

    public TextField txtCustomerId;
    public TextField txtCost;
    String customerId;
    Double cost;

    public void initialize() throws SQLException, ClassNotFoundException {
        customerWiseIncome();
    }

    private void customerWiseIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm  = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` ORDER BY Cost ASC;");
        ResultSet rst = stm.executeQuery();

        while (rst.next()){
            customerId=(rst.getString(1));
            cost=(rst.getDouble(5));

        }
        txtCustomerId.setText(customerId);
        txtCost.setText(String.valueOf(cost));
    }
}
