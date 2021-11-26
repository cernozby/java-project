package cz.upol.jj.cernzb00.semestralka;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompDatabase extends BaseDatabase <Comp> implements DatabaseInterface<Comp> {
    CompDatabase(String filename) {
        super(filename);
    }

    public boolean addComp (String date, String address, String description, int boulderCount) {
        try {
            Comp comp = new Comp(this.getNextId(), date, address, description, boulderCount);
            if (this.compExist(comp)) {
                System.out.println("Zavod jiz existuje");
            } else {
                return writeComp(comp, true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private boolean compExist(Comp newComp) {
        return this.readDbData().stream().anyMatch(comp -> comp.equals(newComp));
    }

    private boolean writeComp(Comp comp, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            f.write(comp.getStrToDb().getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean writeComp(ArrayList<Comp> comps, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            for (Comp c : comps) {
                f.write(c.getStrToDb().getBytes());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public Comp getFromDb(String dbline) {
        String[] parts = dbline.split(";");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Neplatný záznam Db {dbline}");
        }

        return new Comp(
                Integer.parseInt(this.getDbValue(parts[0])),
                this.getDbValue(parts[1]),
                this.getDbValue(parts[2]),
                this.getDbValue(parts[3]),
                Integer.parseInt(this.getDbValue(parts[4]))
        );
    }

    public void printAllComps() {
        ArrayList<Comp> comps = this.readDbData();
        comps.forEach(racer-> System.out.print(racer.getToStr() + "\n"));
    }

    public boolean delete(int id) {
        ArrayList<Comp> comps  = this.readDbData();
        if (!comps.removeIf(comp -> comp.getId() == id)) {
            throw new IllegalArgumentException("Zadané id neexistuje. id:" + id);
        }

        this.getResultDb().readDbData().stream().
                filter(result -> result.getComp().getId() == id).
                collect(Collectors.toList()).forEach( a ->
                        this.getResultDb().delete(a.getId())
                );

        return this.writeComp(comps, false);
    }

    public Optional<Comp> getById(int idComp) {
        return this.readDbData().stream().filter(comp -> comp.getId() == idComp).findAny();
    }

}
