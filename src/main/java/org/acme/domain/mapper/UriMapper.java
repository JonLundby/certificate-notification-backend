package org.acme.domain.mapper;

import org.acme.domain.dto.UriEntityDTO;
import org.acme.domain.model.UriEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UriMapper {
//    @Mapping(target = "id", ignore = true)
    UriEntityDTO toUriEntityDto(UriEntity uriEntity);

    // Mapping lists are dependent on mapping the single objects (therefore no ignore 'id' here but rather on 'toUriEntityDto')
    List<UriEntityDTO> toUriEntityDtoList(List<UriEntity> uriEntities);
}
