package entities;

/**
 * Сущность обобщенного сообщения для чата
 */
public class MessageEntity {
  //event bus address, отражает адрес области карты, в чат комнату которой предназначено сообщение
  private String address;
  // текстовое содержанрие сообщения
  private String text;

  public String getAddress() {
    return address;
  }

  public MessageEntity setAddress(String address) {
    this.address = address;
    return this;
  }

  public String getText() {
    return text;
  }

  public MessageEntity setText(String text) {
    this.text = text;
    return this;
  }
}
