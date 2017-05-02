package com.example.wuzp.secondworld.stats;

/**
 * Created by apple on 16-3-21.
 */
public class Event {
    public String event;
    public int position;
    public String rid;
    public long happen_time;
    public String extra;
    public String count;

    public Event(String event) {
        this(event, 0, null);
    }

    public Event(String event, int position, String rid) {
        this(event, position, rid, null);
    }

    public Event(String event, int position, String rid, String extra) {
        this.event = event;
        this.position = position;
        this.rid = rid;
        this.extra = extra;
        init();
    }

    private void init() {
        happen_time = System.currentTimeMillis();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public long getHappen_time() {
        return happen_time;
    }

    public void setHappen_time(long happen_time) {
        this.happen_time = happen_time;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event='" + event + '\'' +
                ", position=" + position +
                ", rid='" + rid + '\'' +
                ", happen_time=" + happen_time +
                ", extra='" + extra + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
