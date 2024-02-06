package ec.ude.uide.proyectogti.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * contribuyente de mysql
 */
@Schema(description = "contribuyente de mysql")
@Entity
@Table(name = "persona")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * cedula
     */
    @Schema(description = "cedula", required = true)
    @NotNull
    @Column(name = "numero_identificacion", nullable = false, unique = true)
    private String numeroIdentificacion;

    /**
     * apellidos
     */
    @Schema(description = "apellidos")
    @Column(name = "apellidos")
    private String apellidos;

    /**
     * nombres
     */
    @Schema(description = "nombres")
    @Column(name = "nombres")
    private String nombres;

    /**
     * nombres y apellidos
     */
    @Schema(description = "nombres y apellidos")
    @Column(name = "nombre")
    private String nombre;

    /**
     * direccion
     */
    @Schema(description = "direccion")
    @Column(name = "direccion")
    private String direccion;

    /**
     * fono
     */
    @Schema(description = "fono")
    @Column(name = "telefono_fijo")
    private String telefonoFijo;

    /**
     * celular
     */
    @Schema(description = "celular")
    @Column(name = "telefono_movil")
    private String telefonoMovil;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    /**
     * estado civil
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "catalogo" }, allowSetters = true)
    private ItemCatalogo estadoCivil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "catalogo" }, allowSetters = true)
    private ItemCatalogo tipoIdentificacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "catalogo" }, allowSetters = true)
    private ItemCatalogo paisProcedencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "catalogo" }, allowSetters = true)
    private ItemCatalogo nivelEducativo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Persona id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public Persona numeroIdentificacion(String numeroIdentificacion) {
        this.setNumeroIdentificacion(numeroIdentificacion);
        return this;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Persona apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return this.nombres;
    }

    public Persona nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Persona nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Persona direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoFijo() {
        return this.telefonoFijo;
    }

    public Persona telefonoFijo(String telefonoFijo) {
        this.setTelefonoFijo(telefonoFijo);
        return this;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return this.telefonoMovil;
    }

    public Persona telefonoMovil(String telefonoMovil) {
        this.setTelefonoMovil(telefonoMovil);
        return this;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Persona fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public ItemCatalogo getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(ItemCatalogo itemCatalogo) {
        this.estadoCivil = itemCatalogo;
    }

    public Persona estadoCivil(ItemCatalogo itemCatalogo) {
        this.setEstadoCivil(itemCatalogo);
        return this;
    }

    public ItemCatalogo getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(ItemCatalogo itemCatalogo) {
        this.tipoIdentificacion = itemCatalogo;
    }

    public Persona tipoIdentificacion(ItemCatalogo itemCatalogo) {
        this.setTipoIdentificacion(itemCatalogo);
        return this;
    }

    public ItemCatalogo getPaisProcedencia() {
        return this.paisProcedencia;
    }

    public void setPaisProcedencia(ItemCatalogo itemCatalogo) {
        this.paisProcedencia = itemCatalogo;
    }

    public Persona paisProcedencia(ItemCatalogo itemCatalogo) {
        this.setPaisProcedencia(itemCatalogo);
        return this;
    }

    public ItemCatalogo getNivelEducativo() {
        return this.nivelEducativo;
    }

    public void setNivelEducativo(ItemCatalogo itemCatalogo) {
        this.nivelEducativo = itemCatalogo;
    }

    public Persona nivelEducativo(ItemCatalogo itemCatalogo) {
        this.setNivelEducativo(itemCatalogo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persona)) {
            return false;
        }
        return getId() != null && getId().equals(((Persona) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefonoFijo='" + getTelefonoFijo() + "'" +
            ", telefonoMovil='" + getTelefonoMovil() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            "}";
    }
}
