package cz.upol.jj.cernzb00.semestralka;

public class BaseEntity {

    protected int id;

    BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
