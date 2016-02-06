package dao;

import java.util.List;

public interface Dao <T> {
		Long create(T t);
		T read(Long id);
	    void update(T t);
	    void delete(T t);
	    List<T> findAll();
	    List<T> findUsersByBeginString(String begin);
}
