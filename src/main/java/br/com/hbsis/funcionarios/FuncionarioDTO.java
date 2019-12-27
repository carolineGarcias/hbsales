package br.com.hbsis.funcionarios;

public class FuncionarioDTO {

    Long id;
    private String nomeFuncionario, emailFuncionario, uuidFuncionario;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(Long id, String nomeFuncionario, String emailFuncionario, String uuidFuncionario) {
        this.id = id;
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
        this.uuidFuncionario = uuidFuncionario;
    }

    public static FuncionarioDTO of(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNomeFuncionario(),
                funcionario.getEmailFuncionario(),
                funcionario.getUuidFuncionario()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public String getUuidFuncionario() {
        return uuidFuncionario;
    }

    public void setUuidFuncionario(String uuidFuncionario) {
        this.uuidFuncionario = uuidFuncionario;
    }

    @Override
    public String toString() {
        return "FuncionarioDTO{" +
                "id=" + id +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", emailFuncionario='" + emailFuncionario + '\'' +
                ", uuidFuncionario='" + uuidFuncionario + '\'' +
                '}';
    }
}