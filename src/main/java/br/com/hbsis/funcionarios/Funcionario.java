package br.com.hbsis.funcionarios;


import javax.persistence.*;

@Entity
@Table(name = "seg_funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Long id;

    @Column(name = "nome_funcionario")
    private String nomeFuncionario;

    @Column(name = "email_funcionario", length = 50)
    private String emailFuncionario;

    @Column(name = "uuid_funcionario", length = 36)
    private String uuidFuncionario;

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
        return "Funcionario{" +
                "id=" + id +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", emailFuncionario='" + emailFuncionario + '\'' +
                ", uuidFuncionario='" + uuidFuncionario + '\'' +
                '}';
    }
}
