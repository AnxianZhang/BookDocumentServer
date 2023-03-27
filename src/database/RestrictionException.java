package database;

public class RestrictionException extends Exception {
    private final String errorMsg;

    public RestrictionException(String msg) {
        this.errorMsg = msg;
    }

    @Override
    public String toString() {
        return errorMsg;
    }
}