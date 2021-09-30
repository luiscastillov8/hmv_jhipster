package mx.com.hmvsolucines.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.hmvsolucines.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusDTO.class);
        EstatusDTO estatusDTO1 = new EstatusDTO();
        estatusDTO1.setId(1L);
        EstatusDTO estatusDTO2 = new EstatusDTO();
        assertThat(estatusDTO1).isNotEqualTo(estatusDTO2);
        estatusDTO2.setId(estatusDTO1.getId());
        assertThat(estatusDTO1).isEqualTo(estatusDTO2);
        estatusDTO2.setId(2L);
        assertThat(estatusDTO1).isNotEqualTo(estatusDTO2);
        estatusDTO1.setId(null);
        assertThat(estatusDTO1).isNotEqualTo(estatusDTO2);
    }
}
