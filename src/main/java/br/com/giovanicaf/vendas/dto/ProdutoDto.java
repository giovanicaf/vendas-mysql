package br.com.giovanicaf.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoDto {

    private Long id;

    @Length(min = 4, max = 100)
    private String descricao;

    private BigDecimal precoUnitario;
}
