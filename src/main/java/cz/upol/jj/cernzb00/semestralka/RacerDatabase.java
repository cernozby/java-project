package cz.upol.jj.cernzb00.semestralka;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class RacerDatabase extends BaseDatabase<Racer> implements DatabaseInterface<Racer> {

    RacerDatabase(String filename) {
        super(filename);
    }

    public boolean addRacer (String firstname, String lastName, int born)  {
        try {
            Racer racer = new Racer(firstname, lastName, born, this.getNextId());
            if (this.rowExist(racer)) {
                System.out.println("Racer already exist!");
            } else {
                return this.writeOneInstance(racer, true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;

    }

    public Racer getFromDb(String dbline) {
        String[] parts = dbline.split(";");
        if (parts.length != 4) {
            throw new IllegalArgumentException("invalid db line: " + dbline);
        }

        return new Racer(this.getDbValue(parts[1]), this.getDbValue(parts[2]), Integer.parseInt(this.getDbValue(parts[3])), Integer.parseInt(this.getDbValue(parts[0])));
    }

    public boolean delete(int id) {
        ArrayList<Racer> racers  = this.readDbData();
        if (!racers.removeIf(racer -> racer.getId() == id)) {
          throw new IllegalArgumentException("Invalid id. id:" + id);
        }

        //odestraneni zavodnika z vysledku
        this.getResultDb().readDbData().stream().
                filter(r -> r.getRacer().getId() == id).
                collect(Collectors.toList()).forEach(
                        a -> this.getResultDb().delete(a.getId())
                );

        return this.writeMultipleInstance(racers, false);

    }


}
