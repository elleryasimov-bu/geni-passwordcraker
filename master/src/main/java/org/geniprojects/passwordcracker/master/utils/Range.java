package org.geniprojects.passwordcracker.master.utils;

public class Range {
    public String leftBound;
    public String rightBound;
    public Range() {
        leftBound = "";
        rightBound = "";
    }
    public Range(String leftBound, String rightBound) throws Exception{
        if (leftBound.compareTo(rightBound) > 0) {
            throw new Exception("left > right");
        } else {
            this.leftBound = leftBound;
            this.rightBound = rightBound;
        }
    }
}
