package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class ViewReportController {
    public AnchorPane viewReportContext;
    public AnchorPane viewReportContextTwo;

    public void goToViewCustomer(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerView.fxml");
        Parent load = FXMLLoader.load(resource);
        viewReportContextTwo.getChildren().clear();
        viewReportContextTwo.getChildren().add(load);
    }

    public void goToViewItem(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ItemView.fxml");
        Parent load = FXMLLoader.load(resource);
        viewReportContextTwo.getChildren().clear();
        viewReportContextTwo.getChildren().add(load);

    }

    public void goToViewOrder(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderView.fxml");
        Parent load = FXMLLoader.load(resource);
        viewReportContextTwo.getChildren().clear();
        viewReportContextTwo.getChildren().add(load);
    }
}
