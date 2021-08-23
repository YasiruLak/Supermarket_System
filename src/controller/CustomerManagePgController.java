
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import view.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerManagePgController {
    public TextField txtCustomerId;
    public TextField txtPostalCode;
    public TextField txtProvince;
    public TextField txtCity;
    public TextField txtAddress;
    public TextField txtTitle;
    public TextField txtCustomerName;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colTitle;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public AnchorPane customerManageContext;

    public void initialize() {
        try {

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
            colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));


            setCustomersToTable(new CustomerController().getAllCustomers());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setCustomersToTable(ArrayList<Customer> allCustomers) {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        allCustomers.forEach(e -> {
            obList.add(
                    new CustomerTM(e.getId(), e.getTitle(), e.getName(), e.getAddress(), e.getCity(), e.getProvince(), e.getPostalCode()));
        });
        tblCustomer.setItems(obList);

    }

    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Customer c1 = new Customer(
                txtCustomerId.getText(), txtTitle.getText(), txtCustomerName.getText(), txtAddress.getText(),
                txtCity.getText(), txtProvince.getText(), txtPostalCode.getText()


        );
        txtCustomerId.clear();
        txtTitle.clear();
        txtCustomerName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();

        if (new CustomerController().saveCustomer(c1)) {

            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                URL resource = getClass().getResource("../view/CustomerManagePg.fxml");
                Parent load = FXMLLoader.load(resource);
                customerManageContext.getChildren().clear();
                customerManageContext.getChildren().add(load);

        } else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();

    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (new CustomerController().deleteCustomer(txtCustomerId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                URL resource = getClass().getResource("../view/CustomerManagePg.fxml");
                Parent load = FXMLLoader.load(resource);
                customerManageContext.getChildren().clear();
                customerManageContext.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtCustomerId.getText();

        Customer c1 = new CustomerController().getCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    void setData(Customer c) {
        txtCustomerId.setText(c.getId());
        txtTitle.setText(c.getTitle());
        txtCustomerName.setText(c.getName());
        txtAddress.setText(c.getAddress());
        txtCity.setText(c.getCity());
        txtProvince.setText(c.getProvince());
        txtPostalCode.setText(c.getPostalCode());

    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        Customer c1 = new Customer(
                txtCustomerId.getText(),
                txtTitle.getText(),
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()

        );


        if (new CustomerController().updateCustomer(c1)){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            URL resource = getClass().getResource("../view/CustomerManagePg.fxml");
            Parent load = FXMLLoader.load(resource);
            customerManageContext.getChildren().clear();
            customerManageContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }


    }

}





