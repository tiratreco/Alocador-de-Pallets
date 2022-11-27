package Entidades;

import Solucao.Estoque;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@PlanningSolution
public class Solucao {

    @ValueRangeProvider(id = "veiculoRange")
    @ProblemFactCollectionProperty
    private List<Veiculo> veiculos;

    @PlanningEntityCollectionProperty
    private List<Pallet> pallets;

    @PlanningScore(bendableHardLevelsSize = 1, bendableSoftLevelsSize = 3)
    private BendableScore pontuacao;

    public String toString() {
        return String.format("Solução com %d pallets e %d veiculos. Score %s | %d veiculos formados" , pallets.size(), veiculos.size(), pontuacao.toString(), getVeiculosFormados().size());
    }

    public List<Veiculo> getVeiculosFormados() {
        List<Veiculo> result = new ArrayList<>();
        Estoque estoque = new Estoque();

        Map<Veiculo, List<Pallet>> palletsPorVeiculo = pallets.stream().filter(p -> p.getVeiculo() != null).collect(Collectors.groupingBy(Pallet::getVeiculo));
        for (Map.Entry<Veiculo, List<Pallet>> entry : palletsPorVeiculo.entrySet()) {
            boolean veiculoFormado = true;
            Veiculo veiculo = entry.getKey();
            List<Pallet> pallets = entry.getValue();
            boolean mesmoTipo = true;
            boolean mesmaCidade = true;
            double pesoTotal = 0;

            for (Pallet p : pallets) {
                if (!p.getMaterial().getTipo().equals(pallets.get(0).getMaterial().getTipo())) veiculoFormado = false;
                if (!p.getCliente().getCidade().equals(pallets.get(0).getCliente().getCidade())) veiculoFormado = false;
                pesoTotal += p.getPeso();
            }
            if (pesoTotal > veiculo.getPesoMax() || pallets.size() > veiculo.getQuantidadeMax()) veiculoFormado = false;
            if (!estoque.consumirPallets(pallets)) veiculoFormado = false;
            if (pesoTotal < veiculo.getPesoMin() || pallets.size() < veiculo.getQuantidadeMin()) veiculoFormado = false;

            if (veiculoFormado) result.add(veiculo);

        }

        return result;
    }

}

