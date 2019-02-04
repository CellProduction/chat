package services;

public interface EventBusAddresses {
    //общий адрес для получения сообщений
    String ROUTING_MESSAGE = "routing";
    //префикс адресов при роутинге сообщений
    String CELL_ADDRESS_PREFIX = "/cell/";
}
