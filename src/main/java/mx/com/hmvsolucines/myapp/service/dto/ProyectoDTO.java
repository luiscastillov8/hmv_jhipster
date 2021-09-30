package mx.com.hmvsolucines.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.hmvsolucines.myapp.domain.Proyecto} entity.
 */
public class ProyectoDTO implements Serializable {

    private Long id;

    private String nombre;

    private EstatusDTO estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstatusDTO getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusDTO estatus) {
        this.estatus = estatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoDTO)) {
            return false;
        }

        ProyectoDTO proyectoDTO = (ProyectoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, proyectoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProyectoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", estatus=" + getEstatus() +
            "}";
    }
}
