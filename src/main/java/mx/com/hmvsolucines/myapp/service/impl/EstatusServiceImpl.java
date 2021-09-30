package mx.com.hmvsolucines.myapp.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mx.com.hmvsolucines.myapp.domain.Estatus;
import mx.com.hmvsolucines.myapp.repository.EstatusRepository;
import mx.com.hmvsolucines.myapp.service.EstatusService;
import mx.com.hmvsolucines.myapp.service.dto.EstatusDTO;
import mx.com.hmvsolucines.myapp.service.mapper.EstatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Estatus}.
 */
@Service
@Transactional
public class EstatusServiceImpl implements EstatusService {

    private final Logger log = LoggerFactory.getLogger(EstatusServiceImpl.class);

    private final EstatusRepository estatusRepository;

    private final EstatusMapper estatusMapper;

    public EstatusServiceImpl(EstatusRepository estatusRepository, EstatusMapper estatusMapper) {
        this.estatusRepository = estatusRepository;
        this.estatusMapper = estatusMapper;
    }

    @Override
    public EstatusDTO save(EstatusDTO estatusDTO) {
        log.debug("Request to save Estatus : {}", estatusDTO);
        Estatus estatus = estatusMapper.toEntity(estatusDTO);
        estatus = estatusRepository.save(estatus);
        return estatusMapper.toDto(estatus);
    }

    @Override
    public Optional<EstatusDTO> partialUpdate(EstatusDTO estatusDTO) {
        log.debug("Request to partially update Estatus : {}", estatusDTO);

        return estatusRepository
            .findById(estatusDTO.getId())
            .map(existingEstatus -> {
                estatusMapper.partialUpdate(existingEstatus, estatusDTO);

                return existingEstatus;
            })
            .map(estatusRepository::save)
            .map(estatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstatusDTO> findAll() {
        log.debug("Request to get all Estatuses");
        return estatusRepository.findAll().stream().map(estatusMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusDTO> findOne(Long id) {
        log.debug("Request to get Estatus : {}", id);
        return estatusRepository.findById(id).map(estatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estatus : {}", id);
        estatusRepository.deleteById(id);
    }
}
