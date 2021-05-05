package ca.on.conestogac.prog3210.erasflashcards;

public class Score {

    private int id;
    private String fname;
    private String lname;
    private double grade;
    private String category;
    private int date;

    public Score(){

    }

    public Score(int id, String fname, String lname, double grade, String category, int date){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.grade = grade;
        this.category =category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(int date){
        this.date = date;
    }

    public int getDate(){
        return date;
    }
}
