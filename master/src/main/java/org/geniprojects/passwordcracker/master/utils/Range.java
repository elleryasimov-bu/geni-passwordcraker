package org.geniprojects.passwordcracker.master.utils;

public class Range {
    public String leftBound;
    public String rightBound;
    public Range() {
        leftBound = "";
        rightBound = "";
    }
    public Range(String leftBound, String rightBound){
        if (leftBound.compareTo(rightBound) > 0) {
            this.leftBound = rightBound;
            this.rightBound = leftBound;
        } else {
            this.leftBound = leftBound;
            this.rightBound = rightBound;
        }
    }
}
