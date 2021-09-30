package mx.com.hmvsolucines.myapp.service.impl;

import java.util.Optional;
import mx.com.hmvsolucines.myapp.domain.Proyecto;
import mx.com.hmvsolucines.myapp.repository.ProyectoRepository;
import mx.com.hmvsolucines.myapp.service.ProyectoService;
import mx.com.hmvsolucines.myapp.service.dto.ProyectoDTO;
import mx.com.hmvsolucines.myapp.service.mapper.ProyectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Proyecto}.
 */
@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private final Logger log = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    private final ProyectoRepository proyectoRepository;

    private final ProyectoMapper proyectoMapper;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
    }

    @Override
    public ProyectoDTO save(ProyectoDTO proyectoDTO) {
        log.debug("Request to save Proyecto : {}", proyectoDTO);
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);
        proyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toDto(proyecto);
    }

    @Override
    public Optional<ProyectoDTO> partialUpdate(ProyectoDTO proyectoDTO) {
        log.debug("Request to partially update Proyecto : {}", proyectoDTO);

        return proyectoRepository
            .findById(proyectoDTO.getId())
            .map(existingProyecto -> {
                proyectoMapper.partialUpdate(existingProyecto, proyectoDTO);

                return existingProyecto;
            })
            .map(proyectoRepository::save)
            .map(proyectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proyectos");
        return proyectoRepository.findAll(pageable).map(proyectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoDTO> findOne(Long id) {
        log.debug("Request to get Proyecto : {}", id);
        return proyectoRepository.findById(id).map(proyectoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proyecto : {}", id);
        proyectoRepository.deleteById(id);
    }
}
