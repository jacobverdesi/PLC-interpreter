import java.util.ArrayList;
import java.util.List;

public class DFAstate {
    private int state;
    private StringBuilder token;
    private char curr;
    private char next;
    private List<String> tokens = new ArrayList<String>();

    public DFAstate (){
        state=0;
        token= new StringBuilder();
        curr=' ';
        next=' ';
    }
    public void addChar(char c){
        token.append(c);
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public char getCurr() {
        return curr;
    }
    public void setCurr(char curr) {
        this.curr = curr;
        token.append(curr);
    }
    public char getNext() {
        return next;
    }
    public void setNext(char next) {
        this.next = next;
    }

    public void reset(){
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
}
