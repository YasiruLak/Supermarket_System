package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Item;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ItemManagePgController {

    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TableView<ItemTM> tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TextField txtItemCode;
    ;
    public TextField txtPackSize;
    public AnchorPane itemManageContext;
    public Label labelOne;

    public void initialize() {

        try {
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

            setItemsToTable(new ItemController().getAllItem());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setItemsToTable(ArrayList<Item> allItems) {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        allItems.forEach(e -> {
            obList.add(
                    new ItemTM(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getUnitPrice(), e.getQtyOnHand()));
        });
        tblItem.setItems(obList);

    }

    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Item i1 = new Item(
                txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), Double.parseDouble(txtUnitPrice.getText()), txtQtyOnHand.getText()
        );
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

        if (new ItemController().saveItem(i1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            URL resource = getClass().getResource("../view/ItemManagePg.fxml");
            Parent load = FXMLLoader.load(resource);
            itemManageContext.getChildren().clear();
            itemManageContext.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (new ItemController().deleteItem(txtItemCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            URL resource = getClass().getResource("../view/ItemManagePg.fxml");
            Parent load = FXMLLoader.load(resource);
            itemManageContext.getChildren().clear();
            itemManageContext.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

    }

    public void searchItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = txtItemCode.getText();

        Item i1 = new ItemController().getItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(i1);
        }
    }

    void setData(Item i) {
        txtItemCode.setText(i.getItemCode());
        txtDescription.setText(i.getDescription());
        txtPackSize.setText(i.getPackSize());
        txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
        txtQtyOnHand.setText(i.getQtyOnHand());

    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Item i1 = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                txtQtyOnHand.getText()
        );

        if (new ItemController().updateItem(i1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            URL resource = getClass().getResource("../view/ItemManagePg.fxml");
            Parent load = FXMLLoader.load(resource);
            itemManageContext.getChildren().clear();
            itemManageContext.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

  public void checkingItemCode(KeyEvent keyEvent) {
//        String value ="^([A-Z0-9 ]{1,3}[-]([0-9]{4}))$";
//        Pattern pattern= Pattern.compile(value);
//        Matcher match=pattern.matcher(txtItemCode.getText());
//
//        if(!match.matches()){
//            labelOne.setText("Invalid Item Code!");
//        }else{
//            labelOne.setText("");
//        }

}
}
