package cz.upol.jj.cernzb00.semestralka;

public enum Type {
    A("A"), N("N");
    private final String name;
    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
