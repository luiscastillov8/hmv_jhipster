package mx.com.hmvsolucines.myapp.service;

import java.util.List;
import java.util.Optional;
import mx.com.hmvsolucines.myapp.service.dto.EstatusDTO;

/**
 * Service Interface for managing {@link mx.com.hmvsolucines.myapp.domain.Estatus}.
 */
public interface EstatusService {
    /**
     * Save a estatus.
     *
     * @param estatusDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusDTO save(EstatusDTO estatusDTO);

    /**
     * Partially updates a estatus.
     *
     * @param estatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstatusDTO> partialUpdate(EstatusDTO estatusDTO);

    /**
     * Get all the estatuses.
     *
     * @return the list of entities.
     */
    List<EstatusDTO> findAll();

    /**
     * Get the "id" estatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusDTO> findOne(Long id);

    /**
     * Delete the "id" estatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
