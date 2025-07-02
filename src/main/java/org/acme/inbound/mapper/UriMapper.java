package org.acme.inbound.mapper;

import org.acme.inbound.dto.UriDTOResponse;
import org.acme.domain.model.UriDomainModel;
import org.acme.outbound.model.UriEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = CertificateMetadataMapper.class)
public interface UriMapper {
    @Mapping(source = "certificateMetadata", target = "certificateMetadataEntity")
    UriEntity toUriEntity(UriDomainModel uriDomainModel);

    @Mapping(source = "certificateMetadataEntity", target = "certificateMetadata")
    UriDomainModel toDomain(UriEntity uriEntity);

    @Mapping(target = "certificateId", source = "certificateMetadata.id")
    UriDTOResponse toUriDtoResponse(UriDomainModel uriDomainModel);

    List<UriDTOResponse> toUriDTOResponseList(List<UriDomainModel> uriDomainModels);

    List<UriDomainModel> toUriDomainList(List<UriEntity> uriEntities);
}
