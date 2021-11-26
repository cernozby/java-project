package cz.upol.jj.cernzb00.semestralka;

import java.util.ArrayList;
import java.util.Optional;

public interface DatabaseInterface <T> {
    T getFromDb(String DbLine);
    boolean delete(int id);
    ArrayList<T> readDbData();
}

