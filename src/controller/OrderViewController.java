package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import view.tm.OrderTM;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderViewController {
    public TableView<OrderTM>tblOrder;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colOrderDate;
    public TableColumn colOrderTime;
    public TableColumn colCost;

    public void orderTableViewLoad(){

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        try{
            ArrayList<OrderTM> List = OrderController.getAllOrder();
            tblOrder.setItems(FXCollections.observableArrayList(List));
        } catch (SQLException throwables) {
            //JOptionPane.showMessageDialog(null, "Driver not found !!!");
        } catch (ClassNotFoundException e) {
            //JOptionPane.showMessageDialog(null, "Class not found !!!");
        }

    }

    public void refreshOnAction(ActionEvent actionEvent) {
        orderTableViewLoad();
    }
}
