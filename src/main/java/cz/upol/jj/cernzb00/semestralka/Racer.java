package cz.upol.jj.cernzb00.semestralka;

import java.util.Objects;

public class Racer extends BaseEntity implements entityInterface {
    protected int born;
    protected String firstname;
    protected String lastname;


    Racer(String firstname, String lastname, int born, int id) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.born = born;

    }

    public String getStrToDb() {
        return  "id:{"+this.id+"};firstname:{"+this.firstname+"};lastname{"+this.lastname+"};born{"+this.born+"}\n";
    }

    public String getToStr() {
        return "full name: " + this.firstname + " " + this.lastname + " born: " + this.born;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Racer user = (Racer) o;
        return born == user.born &&
               Objects.equals(firstname, user.firstname) &&
               Objects.equals(lastname, user.lastname) &&
               id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(born, firstname, lastname);
    }
}
