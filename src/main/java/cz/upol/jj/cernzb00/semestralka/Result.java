package cz.upol.jj.cernzb00.semestralka;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Result extends BaseEntity implements entityInterface, Comparable<Result> {

    private Comp comp;
    private Racer racer;
    private ArrayList<Type> result;
    Result(int id, Comp comp, Racer racer, ArrayList<Type> result) {
        super(id);
        this.comp = comp;
        this.racer = racer;
        this.result = result;
    }

    @Override
    public String getStrToDb() {
        StringBuilder myResult = new StringBuilder();
        myResult.append("id:{").append(this.id).append("};compId:{").append(this.comp.getId()).append("};racerId{").append(this.racer.getId()).append("}");
        this.getResult().forEach(r -> myResult.append(";r:{").append(r.toString()).append("}"));

        return myResult + "\n";
    }


    @Override
    public String getToStr() {
         StringBuilder myResult = new StringBuilder();
         myResult.append(this.getRacer().getToStr());
         myResult.append(" Výsledky: " );
        int iter = 1;
        for (Type t: this.getResult()) {
            myResult.append(" B").append(iter++).append(": ").append(t.toString());
        }
        return myResult.toString();
    }


    public Comp getComp() {
        return comp;
    }

    public Racer getRacer() {
        return racer;
    }

    public ArrayList<Type> getResult() {
        return result;
    }

    private int getClimbedBoulder() {
        return Math.toIntExact(this.getResult().stream().filter(type -> type.equals(Type.A)).count());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result1 = (Result) o;
        return Objects.equals(comp, result1.comp) && Objects.equals(racer, result1.racer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comp, racer, result);
    }

    @Override
    public int compareTo(Result o) {
        return Integer.compare(this.getClimbedBoulder(), o.getClimbedBoulder()) * -1;
    }
}
