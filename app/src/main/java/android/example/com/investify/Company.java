package android.example.com.investify;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Models Company as an object in the database
 */
public class Company implements Serializable {
    String name;
    String description;
    String logoLInk;
    String url;
    HashMap<String, ArrayList<Object>> perfValues;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    /**
     * the Company Class constructor
     *
     * @param name        of the company
     * @param description
     * @param logoLInk    the link where the logo is stored
     * @param perfValues  the performance value information
     *                    that is stored in a hashMap that stores years as keys and months as values
     * @param url         stores the company website address
     */
    public Company(String name, String description, String logoLInk, HashMap<String, ArrayList<Object>> perfValues, String url) {
        this.name = name;
        this.description = description;
        this.logoLInk = logoLInk;
        this.perfValues = perfValues;
        this.url = url;
    }

    public Company() {

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


    /**
     * Cast the company performance hashmap fetched from database
     *
     * @return a HashMap Key -> Integer  Value -> ArrayList <Double>
     */
    public HashMap<Integer, ArrayList<Double>> performance() {
        HashMap<Integer, ArrayList<Double>> tempPerf = new HashMap<>();
        for (Map.Entry<String, ArrayList<Object>> entry : this.perfValues.entrySet()) {
            ArrayList<Double> val = new ArrayList<>();
            for (Object obj : entry.getValue()) {
                val.add(Double.parseDouble(String.valueOf(obj)));
            }
            tempPerf.put(Integer.parseInt(entry.getKey()), val);
        }

        return tempPerf;
    }

}
