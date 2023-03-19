package document;

import consolColor.Color;

import javax.swing.*;
import java.awt.*;

public class RestrictionException extends Exception {
    private String errorMsg;

    public RestrictionException(String msg) {
        this.errorMsg = msg;
    }

    @Override
    public String toString() {
        return errorMsg;
    }
}