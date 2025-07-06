package org.acme.domain.service;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.model.UriDomainModel;
import org.acme.domain.ports.UriInboundPort;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class SchedulerService {

    private static final Logger logger = Logger.getLogger(SchedulerService.class);

    @Inject
    UriInboundPort uriInboundPort;

    @Scheduled(cron = "0 32 12 * * ?") // Second Minute Hour Day DayOfWeek(1-7 or SUN-SAT)
    @Transactional
    public void scheduledUriScan() {
        LocalDateTime localDateTimeBefore = LocalDateTime.now();
        LocalTime localTimeBefore = LocalTime.now();

        logger.info("URI scanning initiated on: " + localDateTimeBefore);

        List<UriDomainModel> allUris = uriInboundPort.findAll();
        uriInboundPort.dispatchAllUris(allUris, true);

        Duration duration = Duration.between(localTimeBefore, LocalTime.now());

        logger.info("URI scanning complete on: " + localDateTimeBefore);
        logger.info("URI scan time took: " + duration.toMillis() + "ms");

    }

    // TODO: implement scheduled scan for certificateMetadata entities and delete those with no URI relations
}
