package Entidades;

import Solução.Cliente;
import lombok.Getter;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Getter
@Setter
@PlanningEntity
public class Pallet {

    @PlanningVariable
    private Veiculo veiculo;

    private Cliente cliente;

    private int pontuacao;
}
