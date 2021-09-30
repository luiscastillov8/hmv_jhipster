package mx.com.hmvsolucines.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.com.hmvsolucines.myapp.repository.EstatusRepository;
import mx.com.hmvsolucines.myapp.service.EstatusService;
import mx.com.hmvsolucines.myapp.service.dto.EstatusDTO;
import mx.com.hmvsolucines.myapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mx.com.hmvsolucines.myapp.domain.Estatus}.
 */
@RestController
@RequestMapping("/api")
public class EstatusResource {

    private final Logger log = LoggerFactory.getLogger(EstatusResource.class);

    private static final String ENTITY_NAME = "estatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusService estatusService;

    private final EstatusRepository estatusRepository;

    public EstatusResource(EstatusService estatusService, EstatusRepository estatusRepository) {
        this.estatusService = estatusService;
        this.estatusRepository = estatusRepository;
    }

    /**
     * {@code POST  /estatuses} : Create a new estatus.
     *
     * @param estatusDTO the estatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusDTO, or with status {@code 400 (Bad Request)} if the estatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatuses")
    public ResponseEntity<EstatusDTO> createEstatus(@RequestBody EstatusDTO estatusDTO) throws URISyntaxException {
        log.debug("REST request to save Estatus : {}", estatusDTO);
        if (estatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusDTO result = estatusService.save(estatusDTO);
        return ResponseEntity
            .created(new URI("/api/estatuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatuses/:id} : Updates an existing estatus.
     *
     * @param id the id of the estatusDTO to save.
     * @param estatusDTO the estatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusDTO,
     * or with status {@code 400 (Bad Request)} if the estatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatuses/{id}")
    public ResponseEntity<EstatusDTO> updateEstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstatusDTO estatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Estatus : {}, {}", id, estatusDTO);
        if (estatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstatusDTO result = estatusService.save(estatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estatuses/:id} : Partial updates given fields of an existing estatus, field will ignore if it is null
     *
     * @param id the id of the estatusDTO to save.
     * @param estatusDTO the estatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusDTO,
     * or with status {@code 400 (Bad Request)} if the estatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estatuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstatusDTO> partialUpdateEstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstatusDTO estatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estatus partially : {}, {}", id, estatusDTO);
        if (estatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstatusDTO> result = estatusService.partialUpdate(estatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estatuses} : get all the estatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatuses in body.
     */
    @GetMapping("/estatuses")
    public List<EstatusDTO> getAllEstatuses() {
        log.debug("REST request to get all Estatuses");
        return estatusService.findAll();
    }

    /**
     * {@code GET  /estatuses/:id} : get the "id" estatus.
     *
     * @param id the id of the estatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatuses/{id}")
    public ResponseEntity<EstatusDTO> getEstatus(@PathVariable Long id) {
        log.debug("REST request to get Estatus : {}", id);
        Optional<EstatusDTO> estatusDTO = estatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusDTO);
    }

    /**
     * {@code DELETE  /estatuses/:id} : delete the "id" estatus.
     *
     * @param id the id of the estatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatuses/{id}")
    public ResponseEntity<Void> deleteEstatus(@PathVariable Long id) {
        log.debug("REST request to delete Estatus : {}", id);
        estatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
