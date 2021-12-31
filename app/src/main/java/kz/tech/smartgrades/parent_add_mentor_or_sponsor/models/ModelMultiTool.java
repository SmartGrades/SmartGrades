package kz.tech.smartgrades.parent_add_mentor_or_sponsor.models;

public class ModelMultiTool {
    private String coins;
    private String time;
    private String money;

    private String avatar;
    private String count;
    private String done;
    private String id;
    private String steps;
    private String target;

    private String type;

    public ModelMultiTool() {

    }

    public ModelMultiTool(String coins, String time, String money, String avatar, String count, String done, String id,
                          String steps, String target) {
        this.coins = coins;
        this.time = time;
        this.money = money;

        this.avatar = avatar;
        this.count = count;
        this.done = done;
        this.id = id;
        this.steps = steps;
        this.target = target;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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
}
