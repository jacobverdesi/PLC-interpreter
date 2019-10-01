import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DFAstate {
    private int state;
    private StringBuilder token;
    private char curr;
    private char next;
    private List<Map.Entry<String, TERMINAL>> tokens= new ArrayList<>();

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
    String getToken(){
        return token.toString();
    }

    void reset(TERMINAL key){
        //System.out.println("Adding token: "+token);
        Map.Entry<String, TERMINAL> tokensMap =new AbstractMap.SimpleEntry<>(token.toString(),key) ;
        tokens.add(tokensMap);
        token.delete(0,token.length());
        curr=' ';
        next=' ';
        state=0;
    }

    public List<Map.Entry<String, TERMINAL>> getTokens() {
        return tokens;
    }
    void resetTokens(){
        tokens.clear();
    }
}
