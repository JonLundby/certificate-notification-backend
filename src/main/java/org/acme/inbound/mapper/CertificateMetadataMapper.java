package org.acme.inbound.mapper;

import org.acme.domain.model.CertificateMetadata;
import org.acme.inbound.dto.CertificateDTOResponse;
import org.acme.outbound.model.CertificateMetadataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

// When this mapper maps certificateMetadata to CertificateMetadataEntity then it does not know how to map the nested UriDomainModel to UriEntity...
// ... so 'uses = UriMapper.class' tells this mapper to use the UriMapper when this happens
@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {UriMapper.class, NoteMapper.class})
public interface CertificateMetadataMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uris", ignore = true) // ignore mapping back to source
    @Mapping(target = "notes", ignore = true) // ignore mapping back to source
    CertificateMetadataEntity toEntity(CertificateMetadata certificateMetadata);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "uris", ignore = true) // ignore mapping back to source
    @Mapping(target = "notes", ignore = true) // ignore mapping back to source
    CertificateMetadata toDomain(CertificateMetadataEntity certificateMetadataEntity);

    List<CertificateMetadata> toDomainList(List<CertificateMetadataEntity> entities);

    // FOR MAPPING FULL CERTIFICATE DTO RESPONSE WITH URI AND NOTES ARRAYS POPULATED
    @Mapping(target = "uris", source = "uris")
    @Mapping(target = "notes", source = "notes")
    CertificateDTOResponse toCertificateDTOResponse(CertificateMetadataEntity entity);

    List<CertificateDTOResponse> toCertificateDTOResponseList(List<CertificateMetadataEntity> entities);

    // Domain Model to dto response (primarily for the)
    @Mapping(target = "uris", source = "uris")
    @Mapping(target = "notes", source = "notes")
    CertificateDTOResponse toCertificateDTOResponseFromDomain(CertificateMetadata certificateMetadata);

}
