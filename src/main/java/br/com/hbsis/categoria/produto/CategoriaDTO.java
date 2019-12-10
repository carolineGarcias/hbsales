package br.com.hbsis.categoria.produto;

import br.com.hbsis.fornecedor.Fornecedor;

public class CategoriaDTO {

    private Long id, fornecedorId;
    private String nomeCategoria, codCategoria;

    public CategoriaDTO(){
    }

    public CategoriaDTO(Long id, Long fornecedorId, String nomeCategoria, String codCategoria) {
        this.id = id;
        this.fornecedorId  = fornecedorId;
        this.nomeCategoria = nomeCategoria;
        this.codCategoria  = codCategoria;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                    categoria.getId(),
                    categoria.getFornecedor().getIdFornecedor(),
                    categoria.getNomeCategoria(),
                    categoria.getCodCategoria()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", fornecedorId="   + fornecedorId +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", codCategoria='"  + codCategoria  + '\'' +
                '}';
    }
}