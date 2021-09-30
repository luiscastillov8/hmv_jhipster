package mx.com.hmvsolucines.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(unique = true)
    private Estatus estatus;

    @OneToMany(mappedBy = "tarea")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estatus", "tarea" }, allowSetters = true)
    private Set<Tarea> tareas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Proyecto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Proyecto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Proyecto estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public Set<Tarea> getTareas() {
        return this.tareas;
    }

    public void setTareas(Set<Tarea> tareas) {
        if (this.tareas != null) {
            this.tareas.forEach(i -> i.setTarea(null));
        }
        if (tareas != null) {
            tareas.forEach(i -> i.setTarea(this));
        }
        this.tareas = tareas;
    }

    public Proyecto tareas(Set<Tarea> tareas) {
        this.setTareas(tareas);
        return this;
    }

    public Proyecto addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.setTarea(this);
        return this;
    }

    public Proyecto removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.setTarea(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyecto)) {
            return false;
        }
        return id != null && id.equals(((Proyecto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
