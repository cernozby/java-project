package cz.upol.jj.cernzb00.semestralka;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ResultDatabase extends BaseDatabase <Result> implements DatabaseInterface<Result> {
    ResultDatabase(String filename) {
        super(filename);
    }

    public boolean addResult (Comp comp, Racer racer, ArrayList<Type> result)  {
        try {
            Result newResult = new Result(this.getNextId(), comp, racer, result);
            if (this.resultExist(newResult)) {
                System.out.println("Zaznam jiz existuje!");
            } else {
                return writeResult(newResult, true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;

    }

    private boolean writeResult(Result result, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            f.write(result.getStrToDb().getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean writeResult(ArrayList<Result> results, boolean append) {
        try(FileOutputStream f = new FileOutputStream(this.filename, append)) {
            for (Result r : results) {
                f.write(r.getStrToDb().getBytes());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean resultExist(Result newResult) {
        return this.readDbData().stream().anyMatch(result -> result.equals(newResult));
    }

    public Result getFromDb(String dbline) {
        String[] parts = dbline.split(";");
        ArrayList<Type> result = new ArrayList<>();

        for (int i = 3; i < parts.length; i++) {
            result.add(Type.valueOf(this.getDbValue(parts[i])));
        }

        int idComp = Integer.parseInt(this.getDbValue(parts[1]));
        int idRacer = Integer.parseInt(this.getDbValue(parts[2]));

        String name = this.filename.substring(0, this.filename.indexOf("-")+1);

        CompDatabase compDb = new CompDatabase(name + "comp.txt");
        RacerDatabase racerDb = new RacerDatabase(name +"racer.txt");

        return new Result(
                Integer.parseInt(this.getDbValue(parts[0])),
                compDb.getById(idComp).get(),
                racerDb.getById(idRacer).get(),
                result
        );
    }

    public boolean delete(int id) {
        ArrayList<Result> results  = this.readDbData();
        if (!results.removeIf(result -> result.getId() == id)) {
            throw new IllegalArgumentException("Zadané id neexistuje. id:" + id);
        }

        return this.writeResult(results, false);

    }

   public Optional<Result> getById(int id) {
        return this.readDbData().stream().filter(result-> result.getId() == id).findAny();
    }

    public void printRawResult(int compId) {
        ArrayList<Result> results = this.readDbData();
        results.forEach(result-> System.out.print(result.getToStr()));
    }

    public void printOrderResult(int compId) {
        ArrayList<Result> results = this.readDbData();
        results.sort(Result::compareTo);
        results.forEach(result-> System.out.println(result.getToStr()));
        System.out.println("-------------------------------------------------\n");
    };
}
