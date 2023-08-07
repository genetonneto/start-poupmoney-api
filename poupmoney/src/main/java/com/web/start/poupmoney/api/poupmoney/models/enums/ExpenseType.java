package com.web.start.poupmoney.api.poupmoney.models.enums;

public enum ExpenseType {
    ESSENCIAL("Essencial"),
    EDUCACAO("Educação"),
    OBJETIVOS_LONGO_PRAZO("Objetivos a longo prazo"),
    GASTOS_TRIVIAIS("Gastos triviais"),
    POUPANCA("Poupança");

    private final String descricao;

    ExpenseType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }    
} 