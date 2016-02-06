package dao;

import java.util.List;

import data.Contructor;

public interface ContructorDao {
    Long create(Contructor contructor);
    Contructor read(Long id);
    void update(Contructor contructor);
    void delete(Contructor contructor);
    List<Contructor> findAll();
    List<Contructor> findContructorsByBeginString(String begin);
}
