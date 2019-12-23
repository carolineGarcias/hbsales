package br.com.hbsis.fornecedor;

public class FornecedorDTO {

    private Long idFornecedor;
    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long idFornecedor, String razaoSocial,
                         String nomeFantasia, String endereco,
                         String email, String cnpj, String telefone) {

        this.idFornecedor = idFornecedor;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.email = email;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                    fornecedor.getIdFornecedor(),
                    fornecedor.getRazaoSocial(),
                    fornecedor.getNomeFantasia(),
                    fornecedor.getEndereco(),
                    fornecedor.getEmail(),
                    fornecedor.getCnpj(),
                    fornecedor.getTelefone()
        );
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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