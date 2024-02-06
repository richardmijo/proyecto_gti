package ec.ude.uide.proyectogti.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * The ItemCatalog entity.
 * @author rfam
 */
@Schema(description = "The ItemCatalog entity.\n@author rfam")
@Entity
@Table(name = "item_catalogo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemCatalogo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * nombre del item
     */
    @Schema(description = "nombre del item", required = true)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * codigo del item
     */
    @Schema(description = "codigo del item", required = true)
    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    /**
     * descripcion del item
     */
    @Schema(description = "descripcion del item")
    @Column(name = "detalle")
    private String detalle;

    /**
     * codigo del catalogo padre
     */
    @Schema(description = "codigo del catalogo padre")
    @Column(name = "catalog_code")
    private String catalogCode;

    @Column(name = "habilitado")
    private Boolean habilitado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "itemsCatalogos" }, allowSetters = true)
    private Catalogo catalogo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemCatalogo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public ItemCatalogo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public ItemCatalogo codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public ItemCatalogo detalle(String detalle) {
        this.setDetalle(detalle);
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCatalogCode() {
        return this.catalogCode;
    }

    public ItemCatalogo catalogCode(String catalogCode) {
        this.setCatalogCode(catalogCode);
        return this;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public Boolean getHabilitado() {
        return this.habilitado;
    }

    public ItemCatalogo habilitado(Boolean habilitado) {
        this.setHabilitado(habilitado);
        return this;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Catalogo getCatalogo() {
        return this.catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public ItemCatalogo catalogo(Catalogo catalogo) {
        this.setCatalogo(catalogo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemCatalogo)) {
            return false;
        }
        return getId() != null && getId().equals(((ItemCatalogo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemCatalogo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", catalogCode='" + getCatalogCode() + "'" +
            ", habilitado='" + getHabilitado() + "'" +
            "}";
    }
}
