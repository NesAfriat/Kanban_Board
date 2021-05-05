package DataLayer.Mappers;
import BuisnnesLayer.Item;
import DataLayer.PersistanceObjects.ItemPer;

import java.sql.*;


public class DataController {
    private static DataController instance = null;
    private ItemMapper itemMapper;

    public static DataController getInstance() {
        if (instance == null){
            instance = new DataController();
        }
        return instance;
    }
    private DataController(){
        itemMapper= new ItemMapper();
    }

    //Item Actions:
    public Item getItem(int product_id, int item_id){
        ItemPer ip = itemMapper.getItem(product_id, item_id);
        return new Item(ip.item_id, ip.product_id, ip.location, ip.supplied_date, ip.creation_date, ip.expiration_date);
    }
    //TODO look on this
    public void update(ItemPer obj){
        itemMapper.update(obj);
    }

}
