package multiplayer.multiplayerModel.messages;

public class StatusUpdateMessage extends Message {
    int level = -1;
    int money;
    public String whoose;

    public int getLevel() {
        return level;
    }

    public int getMoney() {
        return money;
    }

    public StatusUpdateMessage(int money, int level) {
        this.money = money;
        this.level = level;
    }

    public StatusUpdateMessage(int money){
        this.money = money;
    }
}
