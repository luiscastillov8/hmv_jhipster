package mx.com.hmvsolucines.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TareaMapperTest {

    private TareaMapper tareaMapper;

    @BeforeEach
    public void setUp() {
        tareaMapper = new TareaMapperImpl();
    }
}
