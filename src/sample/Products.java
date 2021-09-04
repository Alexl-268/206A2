package sample;

public class Products {
    private String word;
    private int master, fault, fail;

    //this product class stores each word data so that it can be displayed in the table
    public Products(String word, int master, int fault, int fail){
        this.word = word;
        this.master = master;
        this.fail = fail;
        this.fault = fault;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getMaster() {
        return master;
    }

    public void setMaster(int master) {
        this.master = master;
    }

    public int getFault() {
        return fault;
    }

    public void setFault(int fault) {
        this.fault = fault;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

}
