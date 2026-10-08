package ua.kpi.comsys.dop;

/**
 * Текстове повідомлення.
 */
public record TextMsg(String sender, String text) implements Notification {

    public TextMsg {
        Validation.requireNotBlank(sender, "sender");
        Validation.requireNotBlank(text, "text");
    }
}
