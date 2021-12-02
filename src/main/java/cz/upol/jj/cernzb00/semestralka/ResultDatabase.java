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
            if (this.rowExist(newResult)) {
                System.out.println("Result already exist!");
            } else {
                return writeOneInstance(newResult, true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;

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
            throw new IllegalArgumentException("Invalid id. id:" + id);
        }
        return this.writeMultipleInstance(results, false);
    }


    public void printRawResult(int compId) {
        ArrayList<Result> results = this.readDbData();
        List<Result> listResult = results.stream().filter(c -> c.getComp().getId() == compId ).
                collect(Collectors.toList());
        if (listResult.isEmpty()) {
            System.out.println("Empty database.");
        } else {
            listResult.forEach(result -> System.out.print(result.getToStr()));
            System.out.println("-------------------------------------------------\n");
        }
    }

    public void printOrderResult(int compId) {
        List<Result> listResult = this.readDbData().stream()
                .filter(c -> c.getComp().getId() == compId)
                .sorted(Result::compareTo).collect(Collectors.toList());

        if (listResult.isEmpty()) {
            System.out.println("Empty database.");
        } else {
            listResult.forEach(result-> System.out.println(result.getToStr()));
            System.out.println("-------------------------------------------------\n");
        }
    };
}
