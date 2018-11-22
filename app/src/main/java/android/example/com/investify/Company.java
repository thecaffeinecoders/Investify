package android.example.com.investify;

import java.util.ArrayList;
import java.util.HashMap;


public class Company {
    String name;
    String description;
    String logoLInk ;
    HashMap <String,ArrayList<Object>> perfValues;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoLInk() {
        return logoLInk;
    }

    public HashMap<String, ArrayList<Object>> getPerfValues() {
        return perfValues;
    }

    public Company(String name, String description, String logoLInk, HashMap<String, ArrayList<Object>> perfValues) {
        this.name = name;
        this.description = description;
        this.logoLInk = logoLInk;
        this.perfValues = perfValues;
    }
    public Company(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLogoLInk(String logoLInk) {
        this.logoLInk = logoLInk;
    }

    public void setPerfValues(HashMap<String, ArrayList<Object>> perfValues) {
        this.perfValues = perfValues;
    }
}
