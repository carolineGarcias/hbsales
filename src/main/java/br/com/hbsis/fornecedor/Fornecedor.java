package br.com.hbsis.fornecedor;

import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "seg_fornecedores")
 public class Fornecedor extends FornecedorDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long idFornecedor;

    @Column(name = "razao_social", unique = true, nullable = false, length = 100)
    private String razaoSocial;

    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;

    @Column(name = "nome_fantasia", nullable = false, length = 100)
    private String nomeFantasia;

    @Column(name = "endereco",length = 100)
    private String endereco;

    @Column(name = "telefone",length = 14, nullable = false)
    private String telefone;

    @Column(name = "email", length = 11)
    private String email;

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setId(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id= '" + idFornecedor +
                ", Razao Social= '"  + razaoSocial  + '\'' +
                ", CNPJ= '"          + cnpj         + '\'' +
                ", Nome Fantasia= '" + nomeFantasia + '\'' +
                ", Endereço= '"      + endereco     + '\'' +
                ", Telefone= '"      + telefone     + '\'' +
                ", E-mail= '"        + email        + '\'' +
                '}';
    }
}