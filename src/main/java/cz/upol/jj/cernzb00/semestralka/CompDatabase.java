package cz.upol.jj.cernzb00.semestralka;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompDatabase extends BaseDatabase<Comp> implements DatabaseInterface<Comp> {
    CompDatabase(String filename) {
        super(filename);
    }

    public boolean addComp (String date, String address, String description, int boulderCount) {
        try {
            Comp comp = new Comp(this.getNextId(), date, address, description, boulderCount);
            if (this.rowExist(comp)) {
                System.out.println("Comp already exist!");
            } else {
                return writeOneInstance(comp, true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Comp getFromDb(String dbline) {
        String[] parts = dbline.split(";");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid db line: " +dbline);
        }

        return new Comp(
                Integer.parseInt(this.getDbValue(parts[0])),
                this.getDbValue(parts[1]),
                this.getDbValue(parts[2]),
                this.getDbValue(parts[3]),
                Integer.parseInt(this.getDbValue(parts[4]))
        );
    }

    public boolean delete(int id) {
        ArrayList<Comp> comps  = this.readDbData();
        if (!comps.removeIf(comp -> comp.getId() == id)) {
            throw new IllegalArgumentException("Invalid id. id:" + id);
        }

        this.getResultDb().readDbData().stream().
                filter(result -> result.getComp().getId() == id).
                collect(Collectors.toList()).forEach( a ->
                        this.getResultDb().delete(a.getId())
                );

        return this.writeMultipleInstance(comps, false);
    }
}
