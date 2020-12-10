package Services;


import Beans.*;
import Dao.IService;
import Utility.Global;
import Utility.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Njaimi Med Aziz
 */

public class OrderItemService implements IService<OrderItem> {
    private Connection con = Singleton.getConn();
    private ReptureStock reptureStock = new ReptureStock();
    private CrudBook crudBook = new CrudBook();
    private CrudOnlineBook crudOnlineBook = new CrudOnlineBook();
    private DaoLibraryImp serviceLibrary = new DaoLibraryImp();

    /******************************************** Interface Implementation **********************************************/
    @Override
    public void add(OrderItem item) {
        try {
            PreparedStatement preparedStmt = con.prepareStatement("insert into ORDER_ITEM values (NULL,?,?,?)");
            preparedStmt.setInt(1, item.getOrder().getId());
            preparedStmt.setInt(2, item.getBook().getId());
            preparedStmt.setInt(3, item.getQuantity());
            preparedStmt.execute();
            System.out.println("Item Inseré Avec Succes");
            crudBook.ModifierQuantitéLivre(item.getBook(), item.getQuantity() * -1);
            reptureStock.verificationStock(item.getBook());
            if (crudOnlineBook.RecupererLivreEnLigneByid(item.getBook().getId()) != null){
                OnlineBook onlineBook = new OnlineBook(item.getBook().getId());
                serviceLibrary.addToLibrary(new Library(Global.getCurrentUser(),onlineBook,null,0,0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OrderItem item) {
        try {
            OrderItem before = get(item.getId());
            PreparedStatement preparedStmt = con.prepareStatement("update ORDER_ITEM set quantity=? where id=?");
            preparedStmt.setInt(1, item.getQuantity());
            preparedStmt.setInt(2, item.getId());
            preparedStmt.execute();
            crudBook.ModifierQuantitéLivre(item.getBook(), before.getQuantity()- item.getQuantity());
            System.out.println("Item Modifié Avec Succes");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public OrderItem get(int itemId) {
        OrderItem item = null;
        try {
            PreparedStatement preparedStmt = con.prepareStatement(" select * from ORDER_ITEM where id= ?");
            preparedStmt.setInt(1, itemId);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int quantity = rs.getInt("quantity");
                Book bookId = new Book(rs.getInt("book_id"));
                item = new OrderItem(itemId, new Order(orderId), quantity, bookId);
                break;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return item;
    }

    @Override
    public void delete(int id) {
        // There is no delete in order
    }

    @Override
    public ArrayList<OrderItem> get() {
        ArrayList<OrderItem> res = new ArrayList<>();
        try {
            PreparedStatement preparedStmt = con.prepareStatement("Select * from ORDER_ITEM");
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                res.add(new OrderItem(
                        rs.getInt("id"),
                        new Order(rs.getInt("order_id")),
                        rs.getInt("quantity"),
                        new Book(rs.getInt("book_id")))
                );
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public ArrayList<OrderItem> getOrederItems(int orderId) {
        ArrayList<OrderItem> res = new ArrayList<>();
        try {
            PreparedStatement preparedStmt = con.prepareStatement("Select O.*,B.title,B.price from ORDER_ITEM O JOIN BOOK B on B.id = O.book_id where order_id=?");
            preparedStmt.setInt(1, orderId);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int quantity = rs.getInt("quantity");
                Book book = new Book(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getFloat("price"));
                OrderItem item = new OrderItem(id, new Order(orderId), quantity, book);
                res.add(item);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public ArrayList<OrderItem> getOrderItemsFromBasket(int userid) {
        ArrayList<OrderItem> res = new ArrayList<>();
        try {
            PreparedStatement preparedStmt = con.prepareStatement("SELECT book_id,B.title,BASKET.quantity,(BASKET.quantity*B.price) as price FROM BASKET JOIN BOOK B on B.id = BASKET.book_id  where user_id= ?");
            preparedStmt.setInt(1, userid);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("quantity");
                Book book = new Book(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getFloat("price"));
                OrderItem item = new OrderItem(0, null, quantity, book);
                res.add(item);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }


}
