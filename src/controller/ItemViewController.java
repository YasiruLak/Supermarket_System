package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import view.tm.ItemTM;
import java.util.ArrayList;

public class ItemViewController {
    public TableView<ItemTM>tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;

    public void itemTableViewLoad(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        try{
            ItemController controller = new ItemController();
            ArrayList<Item> item = controller.getAllItem();
            ObservableList<ItemTM> obList = FXCollections.observableArrayList();
            item.forEach(e -> {
                obList.add(
                        new ItemTM(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getUnitPrice(), e.getQtyOnHand()));
            });
            tblItem.setItems(obList);
        }catch (Exception e){

        }
    }

    public void refreshOnAction(ActionEvent actionEvent) {
        itemTableViewLoad();
    }
}
