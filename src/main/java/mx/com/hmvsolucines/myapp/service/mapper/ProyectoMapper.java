package mx.com.hmvsolucines.myapp.service.mapper;

import mx.com.hmvsolucines.myapp.domain.*;
import mx.com.hmvsolucines.myapp.service.dto.ProyectoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proyecto} and its DTO {@link ProyectoDTO}.
 */
@Mapper(componentModel = "spring", uses = { EstatusMapper.class })
public interface ProyectoMapper extends EntityMapper<ProyectoDTO, Proyecto> {
    @Mapping(target = "estatus", source = "estatus", qualifiedByName = "id")
    ProyectoDTO toDto(Proyecto s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProyectoDTO toDtoId(Proyecto proyecto);
}
