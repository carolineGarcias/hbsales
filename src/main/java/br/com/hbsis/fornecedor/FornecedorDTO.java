package br.com.hbsis.fornecedor;

import br.com.hbsis.validation.ValidationCNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FornecedorDTO {

    private Long id;
    @Size(max = 100)
    @NotBlank(message = "razaoSocial é obrigatória")
    private String razaoSocial;

    @ValidationCNPJ(length = 14, message = "Deve conter somente números e exatamente 14 caracteres")
    private String cnpj;

    @Size(max = 100)
    @NotBlank(message = "nomeFantasia é obrigatório")
    private String nomeFantasia;
    @NotBlank(message = "endereco é obrigatório")
    private String endereco;
    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "Telefone de contato deve ter 14 caracteres numéricos")
    private String telefone;
    @NotBlank(message = "e-mail de contato é obrigatório")
    private String email;

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long id, String razaoSocial,
                         String nomeFantasia, String endereco,
                         String email, String cnpj, String telefone) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.email = email;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public static FornecedorDTO of(Fornecedor fornecedor){
        return new FornecedorDTO(
            fornecedor.getId(),
            fornecedor.getRazaoSocial(),
            fornecedor.getNomeFantasia(),
            fornecedor.getEndereco(),
            fornecedor.getEmail(),
            fornecedor.getCnpj(),
            fornecedor.getTelefone()
         );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id= '" + id +
                ", Razao Social= '"  + razaoSocial  + '\'' +
                ", CNPJ= '"          + cnpj         + '\'' +
                ", Nome Fantasia= '" + nomeFantasia + '\'' +
                ", Endereço= '"      + endereco     + '\'' +
                ", Telefone= '"      + telefone     + '\'' +
                ", E-mail= '"        + email        + '\'' +
                '}';
    }

    }


