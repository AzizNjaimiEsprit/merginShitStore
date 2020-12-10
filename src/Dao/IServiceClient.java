package Dao;

import javafx.collections.ObservableList;

public interface IServiceClient<C> {
    void AddClient(C t);

    ObservableList<C> GetAllClient();

    void UpdateClient(String adresse, int zip_code, int id);

    void DeleteClient(int id);

    C GetClient(int id);
}
