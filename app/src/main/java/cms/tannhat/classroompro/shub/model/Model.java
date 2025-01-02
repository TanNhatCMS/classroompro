package cms.tannhat.classroompro.shub.model;


public class Model {

    String question, answers, nid , keywords;
    public Model(String question, String answers) {
        this.question = question;
        this.answers = answers;
        String keyword = question.trim().replaceAll("<.*?>", "").trim();
        this.keywords = keyword;
        this.nid = keyword.substring(0, 1).toUpperCase();
    }
    public String getQuestion() { return question; }

    public String getNid() {  return nid; }

    public String getAnswers() { return answers;   }

    public String getKeywords() { return keywords; }



}
