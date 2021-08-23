package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class AdminPgController {
    public AnchorPane adminContext;

    public void goToIncome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/Incomepg.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void goToMoveItem(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MoveItemsPg.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void goToCustomerIncome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerIncomePg.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void goToItemManage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ItemManagePg.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }

    public void goToViewReport(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewReport.fxml");
        Parent load = FXMLLoader.load(resource);
        adminContext.getChildren().clear();
        adminContext.getChildren().add(load);
    }
}
