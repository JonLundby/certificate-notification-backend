package org.acme.domain.ports;

import org.acme.domain.model.UriDomainModel;

import java.util.List;

public interface UriOutboundPort{
    // All panacheRepository default methods must be declared here to open the usage of them in uriOutboundPort
    void persistList(List<UriDomainModel> uriDomainModel);
    void persistSingle(UriDomainModel uriDomainModel);
    UriDomainModel findByUri(String uri);
    List<UriDomainModel> findAllUris();
    void updateCertificateRelation(String uriStr, Long certMetadataEntityId);
}
