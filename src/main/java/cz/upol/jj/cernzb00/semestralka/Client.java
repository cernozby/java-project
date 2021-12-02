package cz.upol.jj.cernzb00.semestralka;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {
    private RacerDatabase racerDatabase;
    private CompDatabase compDatabase;
    private ResultDatabase resultDatabase;

    CompDatabase getDatabaseComp() {
        return this.compDatabase;
    }
    RacerDatabase getDatabaseRacer() {return this.racerDatabase;}
    ResultDatabase getDatabaseResult() {return this.resultDatabase;}


    private String readStringInput(String nameInput) {
        boolean readInput = true;
        String result = "";
        while (readInput) {
            System.out.print(nameInput + ": ");
            try {
                result = this.validName();
                readInput = false;
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    private int readBornInput() {
        boolean readInput = true;
        int result = 0;
        while (readInput) {
            System.out.print("Born: ");
            try {
                result = this.validBorn();
                readInput = false;
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    private int readIdInput(String nameInput) {
        boolean readInput = true;
        int result = 0;
        while (readInput) {
            System.out.print(nameInput + ": ");
            try {
                result = this.validId();
                readInput = false;
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }


    private String validName() {
        String name = this.readString();
        if (!this.isValidName(name)) {
            throw new InputMismatchException("Invalid input: " + name);
        }
        return name;
    }

    private int validBorn() {
        int born = this.readInt();
        if (!this.isValidBorn(born)) {
            throw new InputMismatchException("Invalid born: " + born);
        }
        return born;
    }

    private int validId() {
        int id = this.readInt();
        if (!this.isValidId(id)) {
            throw new InputMismatchException("Invalid id:");
        }
        return id;
    }


    private String readString() {
        return new Scanner(System.in).nextLine();
    }

    private Integer readInt() {
        boolean isNum = false;
        int i = 0;
        while(!isNum) {
            try {
                i = new Scanner(System.in).nextInt();
                isNum = true;
            } catch (InputMismatchException e) {
                System.out.print("Please insert integer: ");
            }
        }

        return i;
    }

    private boolean isValidName(String name) {
        String regex = "^[A-Za-z]\\w{2,29}$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(name).matches();
    }

    private boolean isValidBorn(int born) {
        return born > 1920 && born < 2020;
    }

    private boolean isValidId(int id) {
        return id > 0 && id < 999999;
    }


    private void spaceAndAction() {
        System.out.println();
        System.out.print("Action: ");
    }
    private void connectToDb() {
        System.out.print("Database name: ");
        String name = this.readString();
        this.racerDatabase = new RacerDatabase(name + "-racer.txt");
        this.compDatabase = new CompDatabase(name + "-comp.txt");
        this.resultDatabase = new ResultDatabase(name + "-result.txt");
        System.out.println("Connected!\n");
    }

    private void addRacer() {

        String firstname = this.readStringInput("Racer firstname");
        String lastname = this.readStringInput("Racer lastname");
        int born = this.readBornInput();
        System.out.println();
        this.racerDatabase.addRacer(firstname, lastname, born);
    }

    private void deleteRacer() {
        try {
            this.getDatabaseRacer().delete(this.readIdInput("Racer id"));
            System.out.println("User was deleted successfully!");
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAllRacers() {
        this.racerDatabase.printAllDb();
        System.out.println();
    }

    private void addComp() {
        System.out.print("comp date: ");
        String date = this.readString();
        System.out.print("comp address: ");
        String address = this.readString();
        System.out.print("comp description: ");
        String description = this.readString();
        int boulderCount = this.readIdInput("comp boulder count: ");
        this.compDatabase.addComp(date, address, description, boulderCount);
        System.out.println();
    }

    private void deleteComp() {
        try {
            if (this.getDatabaseComp().delete(this.readIdInput("comp id"))) {
                System.out.println("Comp was deleted successfully");
                System.out.println();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAllComp() {
        this.getDatabaseComp().printAllDb();
        System.out.println();
    }

    private void addResult() {
        Optional<Racer> racerOpt = this.racerDatabase.getById(this.readIdInput("racer id"));
        Optional<Comp> compOpt = this.compDatabase.getById(this.readIdInput("comp id"));

        if (compOpt.isEmpty() || racerOpt.isEmpty()) {
            System.out.println("Invalid comp id.");
        }

        int boulderCount = compOpt.get().getBoulderCount();

        ArrayList<Type> result = new ArrayList<>();
        for (int i = 0; i < boulderCount; i++) {
            System.out.print("boulder: " + (i + 1) + ": ");

            try {
                result.add(Type.valueOf(this.readString()));
            } catch (IllegalArgumentException e) {
                i--;
                System.out.println("Invalid choose: ");
            }
            System.out.println();

        }
        this.resultDatabase.addResult(compOpt.get(), racerOpt.get(), result);
    }

    private void deleteResult() {
        try {
            if (this.getDatabaseResult().delete(this.readIdInput("result id"))) {
                System.out.println("Result was removed!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printRawResult() {
        this.getDatabaseResult().printRawResult(this.readIdInput("comp id"));
    }

    private void printOrderResult() {
        this.getDatabaseResult().printOrderResult(this.readIdInput("comp id"));
    }

    int onlyConnect() {
        int myint = -1;

        System.out.println("Actions: 0 (exit) 1 (connect)");
        this.spaceAndAction();
        myint = this.readInt();

        switch (myint) {
            case 0 -> {}
            case 1 -> this.connectToDb();
            default -> System.out.println("Invalid action");
        }
        return myint;

    }


    int userOption () {
        int myint = -1;
        System.out.println("Actions: 0 (exit) 1 (add racer) 2 (delete racer) 3 (list of racers)");
        System.out.println("Actions: 4 (add comp) 5 (delete comp) 6 (list of comps)");
        System.out.println("Actions: 7 (add result) 8 (delete result) 9 (list of registred) 10 (order result)");
        this.spaceAndAction();
        myint = this.readInt();
        switch (myint) {
            case 0 -> {}
            case 1 -> this.addRacer();
            case 2 -> this.deleteRacer();
            case 3 -> this.printAllRacers();
            case 4 -> this.addComp();
            case 5 -> this.deleteComp();
            case 6 -> this.printAllComp();
            case 7 -> this.addResult();
            case 8 -> this.deleteResult();
            case 9 -> this.printRawResult();
            case 10 -> this.printOrderResult();
            default -> System.out.println("invalid action");

        }
        return myint;
    }


    void loop() {
        int myint = -1;
        while (myint != 0) {
            if (this.getDatabaseRacer() == null) {
                myint = this.onlyConnect();
            } else {
                myint = this.userOption();
            }
        }
    }
}

