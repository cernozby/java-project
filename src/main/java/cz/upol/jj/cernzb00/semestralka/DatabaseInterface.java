package cz.upol.jj.cernzb00.semestralka;

import java.util.ArrayList;
import java.util.Optional;

public interface DatabaseInterface <T> {
    public T getFromDb(String DbLine);
    public boolean delete(int id);
    public Optional<T> getById(int idRacer);
    public ArrayList<T> readDbData();
}

