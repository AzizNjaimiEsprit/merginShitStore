package Services;


import javafx.collections.ObservableList;

public interface ChatDao<T> {
    public void ajouterElementChat(T t);
    public String afficherReponse(T t);
    public void modifierChatItem(T t);
    public void supprimerChatItem(T t);
    public ObservableList<T> afficherChat();
    public T getChatItem(T t);
}
