package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import view.tm.CustomerTM;
;
import java.util.ArrayList;

public class CustomerViewController {
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colTitle;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;

    public void customerTableViewLoad(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

            try {
                CustomerController controller = new CustomerController();
                ArrayList<Customer> customer = controller.getAllCustomers();
                ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
                customer.forEach(e -> {
                    obList.add(
                            new CustomerTM(e.getId(), e.getTitle(), e.getName(), e.getAddress(), e.getCity(), e.getProvince(), e.getPostalCode()));
                });
                tblCustomer.setItems(obList);
            }catch (Exception e){

            }
    }
    public void refreshOnAction(ActionEvent actionEvent)  {
        customerTableViewLoad();
    }
}
