package com.illumio;

public class Interval implements Comparable<Interval> {

    private long start;
    private long end;

    public Interval(long start, long end) {
        this.start = start;
        this.end = end;
    }
    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public boolean contains(long x){
        return x >= start && x <= end;
    }
    public boolean contains(Interval i){
        return this.start <= i.getStart() && this.end >= i.getEnd();
    }
    @Override
    public int compareTo(Interval i) {
        if(this.start == i.start){
            return (this.end - i.end) < 0? -1 : ((this.end - i.end) > 0 ? 1 : 0);
        }
        return (this.start - i.start) < 0? -1 : 1;
    }
}
