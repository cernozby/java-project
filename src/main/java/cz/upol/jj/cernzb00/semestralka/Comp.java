package cz.upol.jj.cernzb00.semestralka;

import java.util.Objects;

public class Comp extends BaseEntity implements entityInterface {
    private String date;
    private String address;
    private String description;
    private int boulderCount;


    Comp(int id, String date, String address, String description, int boulderCount) {
        super(id);
        this.date = date;
        this.address = address;
        this.description = description;
        this.boulderCount = boulderCount;
    }

    public int getBoulderCount() {
        return boulderCount;
    }

    public String getStrToDb() {
        return "id:{" + this.id + "};date:{" + this.date + "};address:{" + this.address + "};description{" + this.description + "};boulderCount{" + this.boulderCount + "}\n";
    }

    public String getToStr() {
        return "datum zavodu: " + this.date +
                "\nadresa zavodu: " + this.address +
                "\npopis: " + this.description +
                "\npocet bouldru: " + this.boulderCount +
                "\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comp comp = (Comp) o;
        return boulderCount == comp.boulderCount && Objects.equals(date, comp.date) && Objects.equals(address, comp.address) && Objects.equals(description, comp.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, address, description, boulderCount);
    }
}
