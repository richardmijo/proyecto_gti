package ec.ude.uide.proyectogti.web.request;

public class CriterioBusquedaComun {

    private String criterio;
    private Long id;
    private int limite;
    private int pagina;

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CriterioBusquedaComun [criterio=" + criterio + ", id=" + id + ", limite=" + limite + ", pagina=" + pagina + "]";
    }
}
