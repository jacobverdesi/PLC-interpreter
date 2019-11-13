
class SyntaxError extends java.lang.Exception {
    private int line;
    public SyntaxError(String message,int line){
        super(message);
        this.line=line;
    }
    int getLine() {
        return line;
    }
}
class RuntimeError extends java.lang.Exception {
    private int line;
    public RuntimeError(String message,int line){
        super(message);
        this.line=line;
    }
    int getLine() {
        return line;
    }
}
