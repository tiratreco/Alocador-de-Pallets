package Entidades;

import Entidades.Extra.PalletComparator;
import Solucao.Cliente;
import Solucao.Material;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Getter
@Setter
@NoArgsConstructor
@PlanningEntity(difficultyComparatorClass = PalletComparator.class)
public class Pallet {

    @PlanningVariable(valueRangeProviderRefs = {"veiculoRange"}, nullable = true)
    private Veiculo veiculo;

    private Cliente cliente;

    private Material material;

    private String numeroRemessa;

    private int caixas;

    private int pontuacao;

    private double peso;
}
