package io.github.Maike2961.validation_cpf.application.core.domain;

public class CpfValidation {
    private String id;

    private String name;

    private String zipCode;

    private String cpf;

    private Boolean isValidCpf;

    public CpfValidation() {

    }

    public CpfValidation(String id, String name, String zipCode, String cpf, Boolean isValidCpf) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.cpf = cpf;
        this.isValidCpf = isValidCpf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getIsValidCpf() {
        return isValidCpf;
    }
    public void setIsValidCpf(Boolean isValidCpf) {
        this.isValidCpf = isValidCpf;
    }
}
