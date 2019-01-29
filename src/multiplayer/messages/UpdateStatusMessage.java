package multiplayer.messages;

public class UpdateStatusMessage extends Message {
    private int money;
    private int level;

    public UpdateStatusMessage(String sender, int money, int level) {
        super(sender);
        this.money = money;
        this.level = level;
    }

    public int getMoney() {
        return money;
    }

    public int getLevel() {
        return level;
    }
}
