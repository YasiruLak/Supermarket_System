package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CashierPgController {
    public AnchorPane cashierContext;

    public void goToOrderManage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderManagePg.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierContext.getChildren().clear();
        cashierContext.getChildren().add(load);
    }

    public void goToCustomerManage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerManagePg.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierContext.getChildren().clear();
        cashierContext.getChildren().add(load);
    }

    public void goToMakePayment(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakePayment.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierContext.getChildren().clear();
        cashierContext.getChildren().add(load);
    }
}
