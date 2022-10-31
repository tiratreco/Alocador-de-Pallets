package Entidades;

import Solucao.Cliente;
import lombok.Getter;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Getter
@Setter
@PlanningEntity
public class Pallet {

    @PlanningVariable(valueRangeProviderRefs = {"veiculoRange", nullable = true})
    private Veiculo veiculo;

    private Cliente cliente;

    private String material;

    private int caixas;

    private int pontuacao;

    private double peso;
}
