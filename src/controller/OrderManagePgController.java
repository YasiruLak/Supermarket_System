package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.ItemDetails;
import model.Order;
import view.tm.CartTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManagePgController {
    public ComboBox<String> cmbCustomerIds;
    public ComboBox<String> cmbItemCode;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtTitle;
    public TextField txtPostalCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TableView tblCart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public Label txtTtl;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public AnchorPane manageOrderContext;
    private int hour;

    int cartSelectedRowForRemove = -1;

    public void initialize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        setOrderId();

        try {

            loadCustomerIds();
            loadItemIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }
    public void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            String state = null;
            hour = currentTime.getHour();
            if (hour < 12) {
                state = "AM";
            } else {
                state = "PM";
            }
            lblTime.setText(
                    currentTime.getHour()+ ": "+currentTime.getMinute()+ ": "+currentTime.getSecond()+ " " +state
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer c1 = new CustomerController().getCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtName.setText(c1.getName());
            txtTitle.setText(c1.getTitle());
            txtAddress.setText(c1.getAddress());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtPostalCode.setText(c1.getPostalCode());
        }
    }
    private void setOrderId() {
        try {

            lblOrderId.setText(new OrderController().getOrderId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i1 = new ItemController().getItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i1.getDescription());
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));

        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = new CustomerController().getCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);

    }
    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = new ItemController().getAllItemIds();
        cmbItemCode.getItems().addAll(itemIds);
    }

    ObservableList<CartTM> obList= FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {
        try {
            String description = txtDescription.getText();
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyForCustomer = Integer.parseInt(txtQty.getText());
            double total = qtyForCustomer * unitPrice;

            if (qtyOnHand<qtyForCustomer){
                new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
                return;
            }

            CartTM tm = new CartTM(
                    cmbItemCode.getValue(),
                    description,
                    qtyForCustomer,
                    unitPrice,
                    total
            );
            int rowNumber=isExists(tm);

            if (rowNumber==-1){
                obList.add(tm);
            }else{

                CartTM temp = obList.get(rowNumber);
                CartTM newTm = new CartTM(
                        temp.getCode(),
                        temp.getDescription(),
                        temp.getQty()+qtyForCustomer,
                        unitPrice,
                        total+temp.getTotal()
                );
                if (qtyOnHand< temp.getQty()+qtyForCustomer){
                    new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
                    return;
                }

                obList.remove(rowNumber);
                obList.add(newTm);
            }
            tblCart.setItems(obList);
            calculateCost();
        }catch (Exception e){

        }


    }
    private int isExists(CartTM tm){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getCode().equals(obList.get(i).getCode())){
                return i;
            }
        }
        return -1;
    }
    void calculateCost(){
        double ttl=0;
        for (CartTM tm:obList
        ) {
            ttl+=tm.getTotal();
        }
        txtTtl.setText(ttl+" /=");
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }
    public void refreshPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderManagePg.fxml");
        Parent load = FXMLLoader.load(resource);
        manageOrderContext.getChildren().clear();
        manageOrderContext.getChildren().add(load);

    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetails> items= new ArrayList<>();
        double total=0;
        for (CartTM tempTm:obList
        ) {
            total+=tempTm.getTotal();
            items.add(new ItemDetails(tempTm.getCode(),tempTm.getUnitPrice(),
                    tempTm.getQty()));
        }

        Order order= new Order(lblOrderId.getText(), cmbCustomerIds.getValue(),
                lblDate.getText(),
                lblTime.getText(),
                total,
                items
        );
        if (new OrderController().placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            txtName.clear();txtTitle.clear();txtAddress.clear();txtCity.clear();txtProvince.clear();
            txtPostalCode.clear();txtDescription.clear();txtQtyOnHand.clear();txtUnitPrice.clear();txtQty.clear();

            setOrderId();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();

        }
    }


}




