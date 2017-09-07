package matvienko_apps.ua.todolist;


public class Task {

    private int taskID;
    private String text;
    private int ready;
    private long date;



    public Task(String text, int ready, long date) {
        this.text = text;
        this.ready = ready;
        this.date = date;
    }

    public Task(int _id, String text, int ready, long date) {
        this.taskID = _id;
        this.text = text;
        this.ready = ready;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

}
