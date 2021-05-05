package DataLayer.Mappers;
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

}
