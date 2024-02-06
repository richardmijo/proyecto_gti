package ec.ude.uide.proyectogti.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Catalogo.
 */
@Entity
@Table(name = "catalogo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Catalogo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * nombre del catalogo
     */
    @Schema(description = "nombre del catalogo", required = true)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * codigo del catalogo
     */
    @Schema(description = "codigo del catalogo", required = true)
    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    /**
     * descripcion del catalogo
     */
    @Schema(description = "descripcion del catalogo")
    @Column(name = "detalle")
    private String detalle;

    @Column(name = "habilitado")
    private Boolean habilitado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalogo")
    @JsonIgnoreProperties(value = { "catalogo" }, allowSetters = true)
    private Set<ItemCatalogo> itemsCatalogos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Catalogo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Catalogo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Catalogo codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public Catalogo detalle(String detalle) {
        this.setDetalle(detalle);
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Boolean getHabilitado() {
        return this.habilitado;
    }

    public Catalogo habilitado(Boolean habilitado) {
        this.setHabilitado(habilitado);
        return this;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Set<ItemCatalogo> getItemsCatalogos() {
        return this.itemsCatalogos;
    }

    public void setItemsCatalogos(Set<ItemCatalogo> itemCatalogos) {
        if (this.itemsCatalogos != null) {
            this.itemsCatalogos.forEach(i -> i.setCatalogo(null));
        }
        if (itemCatalogos != null) {
            itemCatalogos.forEach(i -> i.setCatalogo(this));
        }
        this.itemsCatalogos = itemCatalogos;
    }

    public Catalogo itemsCatalogos(Set<ItemCatalogo> itemCatalogos) {
        this.setItemsCatalogos(itemCatalogos);
        return this;
    }

    public Catalogo addItemsCatalogo(ItemCatalogo itemCatalogo) {
        this.itemsCatalogos.add(itemCatalogo);
        itemCatalogo.setCatalogo(this);
        return this;
    }

    public Catalogo removeItemsCatalogo(ItemCatalogo itemCatalogo) {
        this.itemsCatalogos.remove(itemCatalogo);
        itemCatalogo.setCatalogo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Catalogo)) {
            return false;
        }
        return getId() != null && getId().equals(((Catalogo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Catalogo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", habilitado='" + getHabilitado() + "'" +
            "}";
    }
}
