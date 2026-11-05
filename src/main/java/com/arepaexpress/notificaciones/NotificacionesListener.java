package com.arepaexpress.notificaciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class NotificacionesListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacionesListener.class);

    @RabbitListener(queues = "notificaciones.pedidos")
    public void avisar(PedidoCreadoEvent pedido, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        if ("pedido.listo".equals(routingKey)) {
            log.info("Hola {}, tu arepa está lista", pedido.cliente());
        } else {
            log.info("📲 Hola {}, recibimos tu pedido {}. ¡Ya lo estamos preparando!",
                    pedido.cliente(), pedido.pedidoId());
        }
    }
}
