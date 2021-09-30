package mx.com.hmvsolucines.myapp.service.impl;

import java.util.Optional;
import mx.com.hmvsolucines.myapp.domain.Tarea;
import mx.com.hmvsolucines.myapp.repository.TareaRepository;
import mx.com.hmvsolucines.myapp.service.TareaService;
import mx.com.hmvsolucines.myapp.service.dto.TareaDTO;
import mx.com.hmvsolucines.myapp.service.mapper.TareaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tarea}.
 */
@Service
@Transactional
public class TareaServiceImpl implements TareaService {

    private final Logger log = LoggerFactory.getLogger(TareaServiceImpl.class);

    private final TareaRepository tareaRepository;

    private final TareaMapper tareaMapper;

    public TareaServiceImpl(TareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    @Override
    public TareaDTO save(TareaDTO tareaDTO) {
        log.debug("Request to save Tarea : {}", tareaDTO);
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    @Override
    public Optional<TareaDTO> partialUpdate(TareaDTO tareaDTO) {
        log.debug("Request to partially update Tarea : {}", tareaDTO);

        return tareaRepository
            .findById(tareaDTO.getId())
            .map(existingTarea -> {
                tareaMapper.partialUpdate(existingTarea, tareaDTO);

                return existingTarea;
            })
            .map(tareaRepository::save)
            .map(tareaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TareaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tareas");
        return tareaRepository.findAll(pageable).map(tareaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TareaDTO> findOne(Long id) {
        log.debug("Request to get Tarea : {}", id);
        return tareaRepository.findById(id).map(tareaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarea : {}", id);
        tareaRepository.deleteById(id);
    }
}
