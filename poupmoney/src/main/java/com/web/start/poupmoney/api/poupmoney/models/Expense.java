package com.web.start.poupmoney.api.poupmoney.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.web.start.poupmoney.api.poupmoney.models.enums.ExpenseType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Expense.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Expense {
    
    public static final String TABLE_NAME = "expense"; // alterar para expense

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "nome", length = 100, nullable = false)
    @NotEmpty
    private String nome;

    @Column(name = "description", length = 255, nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 50, nullable = false)
    private ExpenseType tipo;

    @Column(name = "valor", nullable = false)
    @NotNull
    private Double valor;

    @Column(name = "data_registro_despesa", nullable = false)
    @NotNull
    @Past
    private LocalDate dataRegistroDespesa;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String getDataRegistroDespesa() {
        return dataRegistroDespesa.format(DATE_FORMATTER);
    }

    public void setDataRegistroDespesa(String dataRegistroDespesa) {
        this.dataRegistroDespesa = LocalDate.parse(dataRegistroDespesa, DATE_FORMATTER);
    }

   

}