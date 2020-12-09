package Services;

import Beans.User;
import javafx.collections.ObservableList;


public interface DaoLibrary<T> {
    public void addToLibrary(T t);
    public void removeLibraryBook(T t);
    public ObservableList<T> getLibraryitems(int userId);
    public T getLibraryitem(T t);
    public String updateReachedPage(T t);
    public void updateQuizScore(T t);
    public void sendSms(User user,T t);
}
