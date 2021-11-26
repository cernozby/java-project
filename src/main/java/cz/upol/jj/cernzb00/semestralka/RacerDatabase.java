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
            if (this.racerExist(racer)) {
                System.out.println("Zavodnik jiz existuje, vyberte nový prosim");
            } else {
                return writeRacer(racer, true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;

    }

    private boolean racerExist(Racer newRacer) {
        return this.readDbData().stream().anyMatch(racer -> racer.equals(newRacer));
    }

    private boolean writeRacer(Racer racer, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            f.write(racer.getStrToDb().getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean writeRacer(ArrayList<Racer> racers, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            for (Racer u : racers) {
                f.write(u.getStrToDb().getBytes());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void printAllRacers() {
        ArrayList<Racer> racers = this.readDbData();
        racers.forEach(racer-> System.out.print(racer.getToStr() + "\n"));
    }

    public Racer getFromDb(String dbline) {
        String[] parts = dbline.split(";");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Neplatný záznam Db {dbline}");
        }

        return new Racer(this.getDbValue(parts[1]), this.getDbValue(parts[2]), Integer.parseInt(this.getDbValue(parts[3])), Integer.parseInt(this.getDbValue(parts[0])));
    }

    public boolean delete(int id) {
        ArrayList<Racer> racers  = this.readDbData();
        if (!racers.removeIf(racer -> racer.getId() == id)) {
          throw new IllegalArgumentException("Zadané id neexistuje. id:" + id);
        }

        //odestraneni zavodnika z vysledku
        this.getResultDb().readDbData().stream().
                filter(r -> r.getRacer().getId() == id).
                collect(Collectors.toList()).forEach(
                        a -> this.getResultDb().delete(a.getId())
                );


        return this.writeRacer(racers, false);

    }

    public Optional<Racer> getById(int idRacer) {
        return this.readDbData().stream().filter(racer -> racer.getId() == idRacer).findAny();
    }
}
