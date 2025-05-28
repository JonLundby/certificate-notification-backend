package org.acme.outbound;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.ports.BookOutboundPort;

@ApplicationScoped
public class BookAdapterOutbound implements BookOutboundPort {
}
