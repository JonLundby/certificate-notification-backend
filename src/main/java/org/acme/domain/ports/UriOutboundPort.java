package org.acme.domain.ports;

import org.acme.domain.model.UriDomainModel;

import java.util.List;

public interface UriOutboundPort{
    List<UriDomainModel> persistList(List<UriDomainModel> uriDomainModel);
    UriDomainModel findByUri(String uri);
    List<UriDomainModel> findAllUris();
    void updateCertificateRelation(String uriStr, Long certMetadataEntityId);
}
