package mx.com.hmvsolucines.myapp.service.mapper;

import mx.com.hmvsolucines.myapp.domain.*;
import mx.com.hmvsolucines.myapp.service.dto.TareaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarea} and its DTO {@link TareaDTO}.
 */
@Mapper(componentModel = "spring", uses = { EstatusMapper.class, ProyectoMapper.class })
public interface TareaMapper extends EntityMapper<TareaDTO, Tarea> {
    @Mapping(target = "estatus", source = "estatus", qualifiedByName = "id")
    @Mapping(target = "tarea", source = "tarea", qualifiedByName = "id")
    TareaDTO toDto(Tarea s);
}
