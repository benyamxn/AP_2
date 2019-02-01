package multiplayer.multiplayerModel.messages;

public class BuyingValidationMessage extends Message {
    private boolean buyingValidationMessage;

    public boolean isBuyingValidationMessage() {
        return buyingValidationMessage;
    }

    public BuyingValidationMessage(boolean buyingValidationMessage) {
        this.buyingValidationMessage = buyingValidationMessage;
    }
}
