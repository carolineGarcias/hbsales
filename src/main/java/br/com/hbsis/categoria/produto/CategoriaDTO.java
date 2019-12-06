package br.com.hbsis.categoria.produto;

public class CategoriaDTO {

    private Long id,  fornecedorId;
    private String nomeCategoria,codCategoria;



    public CategoriaDTO(Long id, String nomeCategoria, String codCategoria, Long fornecedorId) {
        this.id = id;
        this.codCategoria  = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedorId  = fornecedorId;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNomeCategoria(),
                categoria.getCodCategoria(),
                categoria.getFornecedor().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", codCategoria="   + codCategoria  +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", idFornecedor="   + fornecedorId  +
                '}';
    }
}

