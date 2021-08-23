package controller;

import db.DbConnection;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemManage{
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
    @Override
    public boolean saveItem(Item i) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Item VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,i.getItemCode());
        stm.setObject(2,i.getDescription());
        stm.setObject(3,i.getPackSize());
        stm.setObject(4,i.getUnitPrice());
        stm.setObject(5,i.getQtyOnHand());
        return stm.executeUpdate()>0;
    }

    @Override
    public Item getItem(String itemCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
        stm.setObject(1, itemCode);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5)

            );

        } else {
            return null;
        }
    }

    @Override
    public boolean deleteItem(String Code) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE ItemCode='"+Code+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?");
        stm.setObject(1,i.getDescription());
        stm.setObject(2,i.getPackSize());
        stm.setObject(3,i.getUnitPrice());
        stm.setObject(4,i.getQtyOnHand());
        stm.setObject(5,i.getItemCode());
        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Item> getAllItem() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item");
        ResultSet rst = stm.executeQuery();
        ArrayList<Item> item = new ArrayList<>();
        while (rst.next()) {
            item.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5)

            ));
        }
        return item;
    }


}
