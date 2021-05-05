package ca.on.conestogac.prog3210.erasflashcards;

public class Question {
    private int id;
    private String qtext;
    private String category;

    public Question(){

    }

    public Question(int id, String qtext, String category){
        this.id = id;
        this.qtext = qtext;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String qtext) {
        this.qtext = qtext;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
