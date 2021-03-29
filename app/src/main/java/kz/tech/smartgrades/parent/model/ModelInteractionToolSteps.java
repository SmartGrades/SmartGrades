package kz.tech.smartgrades.parent.model;

public class ModelInteractionToolSteps {
    private String avatar;
    private String count;
    private String done;
    private String id;
    private String steps;
    private String target;

    private String childFullName;
    private String childAvatar;

    public ModelInteractionToolSteps() {}
    public ModelInteractionToolSteps(String avatar, String count, String done, String id, String steps, String target) {
        this.avatar = avatar;
        this.count = count;
        this.done = done;
        this.id = id;
        this.steps = steps;
        this.target = target;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getDone() {
        return done;
    }
    public void setDone(String done) {
        this.done = done;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSteps() {
        return steps;
    }
    public void setSteps(String steps) {
        this.steps = steps;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }

    public String getChildAvatar() {
        return childAvatar;
    }
    public void setChildAvatar(String childAvatar) {
        this.childAvatar = childAvatar;
    }
    public String getChildFullName() {
        return childFullName;
    }
    public void setChildFullName(String childFullName) {
        this.childFullName = childFullName;
    }
}

