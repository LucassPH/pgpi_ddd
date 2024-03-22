package es.mycloset.back;


import com.google.gson.annotations.SerializedName;

public class NationalDatafileJson {


    public NationalDatafileJson(String id, String mscode, String year, String estcode, Float estimate, Float se, Float lowercib, Float uppercib, String flag) {
        this.id = id;
        this.mscode = mscode;
        this.year = year;
        this.estcode = estcode;
        this.estimate = estimate;
        this.se = se;
        this.lowercib = lowercib;
        this.uppercib = uppercib;
        this.flag = flag;
    }
    private String id;
    private String mscode;
    private String year;
    private String estcode;
    private Float estimate;
    private Float se;
    private Float lowercib;
    private Float uppercib;
    private String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMscode() {
        return mscode;
    }

    public void setMscode(String mscode) {
        this.mscode = mscode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEstcode() {
        return estcode;
    }

    public void setEstcode(String estcode) {
        this.estcode = estcode;
    }

    public Float getEstimate() {
        return estimate;
    }

    public void setEstimate(Float estimate) {
        this.estimate = estimate;
    }

    public Float getSe() {
        return se;
    }

    public void setSe(Float se) {
        this.se = se;
    }

    public Float getLowercib() {
        return lowercib;
    }

    public void setLowercib(Float lowercib) {
        this.lowercib = lowercib;
    }

    public Float getUppercib() {
        return uppercib;
    }

    public void setUppercib(Float uppercib) {
        this.uppercib = uppercib;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public NationalDatafileJson() {
    }
}
