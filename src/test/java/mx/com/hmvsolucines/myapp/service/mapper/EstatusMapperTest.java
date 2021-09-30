package mx.com.hmvsolucines.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstatusMapperTest {

    private EstatusMapper estatusMapper;

    @BeforeEach
    public void setUp() {
        estatusMapper = new EstatusMapperImpl();
    }
}
