package ua.kpi.comsys.dop;

/**
 * Закрита (sealed) ієрархія повідомлень. Дозволені лише три незмінні реалізації.
 */
public sealed interface Notification permits TextMsg, ImageMsg, AudioMsg {

    String sender();
}
