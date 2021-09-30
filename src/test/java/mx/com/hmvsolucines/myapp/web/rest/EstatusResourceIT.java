package mx.com.hmvsolucines.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mx.com.hmvsolucines.myapp.IntegrationTest;
import mx.com.hmvsolucines.myapp.domain.Estatus;
import mx.com.hmvsolucines.myapp.repository.EstatusRepository;
import mx.com.hmvsolucines.myapp.service.dto.EstatusDTO;
import mx.com.hmvsolucines.myapp.service.mapper.EstatusMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EstatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstatusResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estatuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstatusRepository estatusRepository;

    @Autowired
    private EstatusMapper estatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstatusMockMvc;

    private Estatus estatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estatus createEntity(EntityManager em) {
        Estatus estatus = new Estatus().nombre(DEFAULT_NOMBRE);
        return estatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estatus createUpdatedEntity(EntityManager em) {
        Estatus estatus = new Estatus().nombre(UPDATED_NOMBRE);
        return estatus;
    }

    @BeforeEach
    public void initTest() {
        estatus = createEntity(em);
    }

    @Test
    @Transactional
    void createEstatus() throws Exception {
        int databaseSizeBeforeCreate = estatusRepository.findAll().size();
        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);
        restEstatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isCreated());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeCreate + 1);
        Estatus testEstatus = estatusList.get(estatusList.size() - 1);
        assertThat(testEstatus.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createEstatusWithExistingId() throws Exception {
        // Create the Estatus with an existing ID
        estatus.setId(1L);
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        int databaseSizeBeforeCreate = estatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstatuses() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        // Get all the estatusList
        restEstatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getEstatus() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        // Get the estatus
        restEstatusMockMvc
            .perform(get(ENTITY_API_URL_ID, estatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estatus.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingEstatus() throws Exception {
        // Get the estatus
        restEstatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstatus() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();

        // Update the estatus
        Estatus updatedEstatus = estatusRepository.findById(estatus.getId()).get();
        // Disconnect from session so that the updates on updatedEstatus are not directly saved in db
        em.detach(updatedEstatus);
        updatedEstatus.nombre(UPDATED_NOMBRE);
        EstatusDTO estatusDTO = estatusMapper.toDto(updatedEstatus);

        restEstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
        Estatus testEstatus = estatusList.get(estatusList.size() - 1);
        assertThat(testEstatus.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingEstatus() throws Exception {
        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();
        estatus.setId(count.incrementAndGet());

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstatus() throws Exception {
        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();
        estatus.setId(count.incrementAndGet());

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstatus() throws Exception {
        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();
        estatus.setId(count.incrementAndGet());

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstatusWithPatch() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();

        // Update the estatus using partial update
        Estatus partialUpdatedEstatus = new Estatus();
        partialUpdatedEstatus.setId(estatus.getId());

        partialUpdatedEstatus.nombre(UPDATED_NOMBRE);

        restEstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstatus))
            )
            .andExpect(status().isOk());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
        Estatus testEstatus = estatusList.get(estatusList.size() - 1);
        assertThat(testEstatus.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateEstatusWithPatch() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();

        // Update the estatus using partial update
        Estatus partialUpdatedEstatus = new Estatus();
        partialUpdatedEstatus.setId(estatus.getId());

        partialUpdatedEstatus.nombre(UPDATED_NOMBRE);

        restEstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstatus))
            )
            .andExpect(status().isOk());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
        Estatus testEstatus = estatusList.get(estatusList.size() - 1);
        assertThat(testEstatus.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingEstatus() throws Exception {
        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();
        estatus.setId(count.incrementAndGet());

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstatus() throws Exception {
        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();
        estatus.setId(count.incrementAndGet());

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstatus() throws Exception {
        int databaseSizeBeforeUpdate = estatusRepository.findAll().size();
        estatus.setId(count.incrementAndGet());

        // Create the Estatus
        EstatusDTO estatusDTO = estatusMapper.toDto(estatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estatus in the database
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstatus() throws Exception {
        // Initialize the database
        estatusRepository.saveAndFlush(estatus);

        int databaseSizeBeforeDelete = estatusRepository.findAll().size();

        // Delete the estatus
        restEstatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, estatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estatus> estatusList = estatusRepository.findAll();
        assertThat(estatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
