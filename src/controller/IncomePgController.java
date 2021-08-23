package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.IncomeReports;
import view.tm.IncomeReportTm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomePgController {
    public TableView tblDalyIncomeReport;
    public TableColumn colDate;
    public TableColumn colOrders;
    public TableColumn colItems;
    public TableColumn colIncome;

    public void initialize(){

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrders.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItems.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("finalCost"));
        try {

            loadDalyIncomeReport();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDalyIncomeReport() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT OrderDate,count(OrderID),sum(Cost) from `Order` group by OrderDate");
        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            Date date=rst.getDate(1);
            int countOrderId=rst.getInt(2);
            double sumOfTotal= rst.getDouble(3);
            ArrayList<IncomeReports> data=getCountItems();
            loadDalyIncomeReportTableToData(date,countOrderId,sumOfTotal,data);
        }

    }
    ObservableList<IncomeReportTm> obList= FXCollections.observableArrayList();
    private void loadDalyIncomeReportTableToData(Date date, int countOrderId, double sumOfTotal, ArrayList<IncomeReports> data) {
        for (IncomeReports temp:data
        ) {
            obList.add(new IncomeReportTm(
                    date,countOrderId, temp.getNumberOfSoldItem(), sumOfTotal
            ));
        }
        tblDalyIncomeReport.setItems(obList);
    }

    private ArrayList<IncomeReports> getCountItems() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT `Order`.OrderDate,sum(`Order detail`.Qty) FROM `Order` INNER JOIN `Order Detail` ON `Order`.OrderID = `Order detail`.OrderID GROUP BY OrderDate");
        ResultSet rst = stm.executeQuery();
        ArrayList<IncomeReports> temp=new ArrayList<>();
        while (rst.next()){
            Date orderDate=rst.getDate(1);
            int sumOfItem= rst.getInt(2);
            temp.add(new IncomeReports(
                    orderDate,sumOfItem
            ));
        }
        System.out.println(temp);
        return temp;
    }
}
