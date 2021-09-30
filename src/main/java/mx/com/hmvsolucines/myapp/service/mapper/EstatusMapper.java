package mx.com.hmvsolucines.myapp.service.mapper;

import mx.com.hmvsolucines.myapp.domain.*;
import mx.com.hmvsolucines.myapp.service.dto.EstatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estatus} and its DTO {@link EstatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusMapper extends EntityMapper<EstatusDTO, Estatus> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstatusDTO toDtoId(Estatus estatus);
}
