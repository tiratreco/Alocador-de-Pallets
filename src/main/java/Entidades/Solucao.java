package Entidades;

import Solucao.Estoque;
import lombok.Getter;
import lombok.Setter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.util.List;

@Getter
@Setter
@PlanningSolution
public class Solucao {

    @ValueRangeProvider(id = "veiculoRange")
    @ProblemFactCollectionProperty
    private List<Veiculo> veiculos;

    @PlanningEntityCollectionProperty
    private List<Pallet> pallets;

    @PlanningScore
    private HardMediumSoftScore pontuacao;

    private Estoque estoque;
}
