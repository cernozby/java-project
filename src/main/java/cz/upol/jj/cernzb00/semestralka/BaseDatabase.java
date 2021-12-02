package cz.upol.jj.cernzb00.semestralka;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

public class BaseDatabase <T extends BaseEntity & entityInterface >{

    protected String filename;
    private Supplier<T> supplier;

    BaseDatabase(String filename) {
        this.filename = filename;
    }

    public boolean rowExist(T newInstance) {
        return this.readDbData().stream().anyMatch(r -> r.equals(newInstance));
    }

    protected String getDbValue(String rawDb) {
        return rawDb.substring(rawDb.indexOf("{") + 1, rawDb.indexOf("}"));
    }

    protected RacerDatabase getRacerDb() {
        return new RacerDatabase(this.getRawFilename() + "racer.txt");
    }

    protected CompDatabase getCompDb() {
        return new CompDatabase(this.getRawFilename() + "comp.txt");
    }

    protected ResultDatabase getResultDb() {
        return new ResultDatabase(this.getRawFilename() + "result.txt");
    }

    private String getRawFilename() {
        return this.filename.substring(0, this.filename.indexOf("-")+1);
    }

    protected int getNextId() {
        ArrayList<T> data = this.readDbData();
        Optional<T> result = data.stream().max(Comparator.comparing(BaseEntity::getId));
        return result.map(r -> r.getId() + 1).orElse(1);
    }

    public ArrayList<T> readDbData() {
        ArrayList<T> result = new ArrayList<>();

        try (FileInputStream f = new FileInputStream(this.filename)) {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            while (reader.ready()) {
                T instance = this.getFromDb(reader.readLine());
                result.add(instance);
            }
        } catch (FileNotFoundException e) {
            //ok - Pro to to taky bezi
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected boolean writeOneInstance(T instance, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            f.write(instance.getStrToDb().getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    protected boolean writeMultipleInstance(ArrayList<T> instances, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            for (T i : instances) {
                f.write(i.getStrToDb().getBytes());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public T getFromDb(String DbLine) {
        return null;
    }

    public Optional<T> getById(int id) {
        return this.readDbData().stream().filter(instance -> instance.getId() == id).findAny();
    }

    public void printAllDb() {
        ArrayList<T> instances = this.readDbData();
        if (instances.isEmpty()) {
                System.out.println("Empty database.");
        } else {
            instances.forEach(instance -> System.out.print(instance.getToStr() + "\n"));
        }
    }
}
