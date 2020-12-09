package Services;


import Beans.User;
import javafx.collections.ObservableList;

public interface IServiceUser<U> {

    void AddAdmin(U t);

    void UpdateAdmin(User t, int id);

    void DeleteAdmin(int id);

    ObservableList<U> GetAllUser();

    U LoginUser(String login, String pwd);

    Boolean VerifAccountUser(int id, String verification_code);

    void AddCode(String code, int user_id);

    void UpdateRestPassword(int code, String password, int id);

    void mailling(String mail, String message);

    void sendsms(String str, int body);

    U GetUser(int id);
}
