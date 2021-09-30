package mx.com.hmvsolucines.myapp.repository;

import mx.com.hmvsolucines.myapp.domain.Estatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Estatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusRepository extends JpaRepository<Estatus, Long> {}
