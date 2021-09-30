package mx.com.hmvsolucines.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.hmvsolucines.myapp.domain.Tarea} entity.
 */
public class TareaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private EstatusDTO estatus;

    private ProyectoDTO tarea;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstatusDTO getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusDTO estatus) {
        this.estatus = estatus;
    }

    public ProyectoDTO getTarea() {
        return tarea;
    }

    public void setTarea(ProyectoDTO tarea) {
        this.tarea = tarea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TareaDTO)) {
            return false;
        }

        TareaDTO tareaDTO = (TareaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tareaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TareaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estatus=" + getEstatus() +
            ", tarea=" + getTarea() +
            "}";
    }
}
