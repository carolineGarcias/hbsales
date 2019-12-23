package br.com.hbsis.linhas;

public class LinhaDTO {

    private Long    idLinha, idCategoria;
    private String  nomeLinha, codLinha;

    public LinhaDTO(){
    }

    public LinhaDTO(Long idLinha, Long idCategoria, String nomeLinha, String codLinha) {
        this.idLinha     = idLinha;
        this.nomeLinha   = nomeLinha;
        this.idCategoria = idCategoria;
        this.codLinha    = codLinha;
    }

    public static LinhaDTO of(Linha linha) {
        return new LinhaDTO(
                linha.getIdLinha(),
                linha.getCategoria().getId(),
                linha.getNomeLinha(),
                linha.getCodLinha()
        );
    }

    public String getCodLinha() {
        return codLinha;
    }

    public void setCodLinha(String codLinha) {
        this.codLinha = codLinha;
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    @Override
    public String toString() {
        return "Linha { " +
                "  id= "           + idLinha     +
                ", Nome linha= '"  + nomeLinha   + '\'' +
                ", Categoria= "    + idCategoria + '\'' +
                ", Codigo Linha= " + codLinha    + '\'' +
                '}';
    }
}