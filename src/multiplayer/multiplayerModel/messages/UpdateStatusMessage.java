package multiplayer.multiplayerModel.messages;

import multiplayer.multiplayerModel.*;

public class UpdateStatusMessage extends Message {
    private int money;
    private int level;

    public UpdateStatusMessage(CompactProfile sender, int money, int level) {
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
