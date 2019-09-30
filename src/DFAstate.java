import java.util.ArrayList;
import java.util.List;

class DFAstate {
    private int state;
    private StringBuilder token;
    private char curr;
    private char next;
    private List<String> tokens = new ArrayList<String>();

    DFAstate(){
        state=0;
        token= new StringBuilder();
        curr=' ';
        next=' ';
    }
    int getState() {
        return state;
    }
    void setState(int state) {
        this.state = state;
    }
    char getCurr() {
        return curr;
    }
    void setCurr(char curr) {
        this.curr = curr;
        token.append(curr);
    }
    char getNext() {
        return next;
    }
    void setNext(char next) {
        this.next = next;
    }

    void reset(){
        //System.out.println("Adding token: "+token);
        tokens.add(token.toString());
        token.delete(0,token.length());
        curr=' ';
        next=' ';
        state=0;
    }

    public List<String> getTokens() {
        return tokens;
    }
    void resetTokens(){
        tokens.clear();
    }
}
