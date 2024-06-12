package org.allengueco.watcher.model.request;

public class MatchApiRequestParameter {
    long startTime;
    long endTime;
    int queue;
    String type;
    int start;
    int count;

    public MatchApiRequestParameter() {

    }
    public MatchApiRequestParameter(long startTime, long endTime, int queue, String type, int start, int count) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.queue = queue;
        this.type = type;
        this.start = start;
        this.count = count;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
