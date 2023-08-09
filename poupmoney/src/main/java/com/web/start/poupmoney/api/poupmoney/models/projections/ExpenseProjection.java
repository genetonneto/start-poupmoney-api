package com.web.start.poupmoney.api.poupmoney.models.projections;


import java.util.Date;

public interface ExpenseProjection {
    
    public Long getId();

    public String getNome();

    public String getValor();

    public String getTipo();

    public String getDescription();

    public Date getDataRegistroDespesa();

}
