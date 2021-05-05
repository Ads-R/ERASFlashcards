package ca.on.conestogac.prog3210.erasflashcards;

public class Choice {

    private int id;
    private int qid;
    private String ctext;
    private int answer;

    public Choice(){

    }

    public Choice(int id, int qid, String ctext, int answer){
        this.id = id;
        this.qid = qid;
        this.ctext = ctext;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
