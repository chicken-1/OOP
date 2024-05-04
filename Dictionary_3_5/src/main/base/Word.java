package base;

public class Word {
    private String word_target;
    private String word_explain;
    private String word_type;
    private String word_pronunciation;

    /**constructor. */
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word(String word_target, String word_type, String word_pronunciation, String word_explain) {
        this.word_target = word_target;
        this.word_type = word_type;
        this.word_pronunciation = word_pronunciation;
        this.word_explain = word_explain;
    }

    /**getter and setter. */
    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    /**getter and setter. */
    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWord_type() {
        return word_type;
    }

    public void setWord_type(String word_type) {
        this.word_type = word_type;
    }

    public String getWord_pronunciation() {
        return word_pronunciation;
    }

    public void setWord_pronunciation(String word_pronunciation) {
        this.word_pronunciation = word_pronunciation;
    }

}