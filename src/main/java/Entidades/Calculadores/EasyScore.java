package Entidades.Calculadores;

import Entidades.Pallet;
import Entidades.Solucao;
import Entidades.Veiculo;
import Solucao.Estoque;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EasyScore implements EasyScoreCalculator<Solucao, HardMediumSoftScore> {

    @Override
    public HardMediumSoftScore calculateScore(Solucao solucao) {
        List<Pallet> palletList = solucao.getPallets();
        int hardScore = 0;
        int mediumScore = 0;
        int softScore = 0;

        Map<Veiculo, List<Pallet>> palletsPorVeiculo = palletList.stream().collect(Collectors.groupingBy(p -> p.getVeiculo()));
        Estoque estoque = new Estoque();
        solucao.setEstoque(estoque);
        for (Map.Entry<Veiculo, List<Pallet>> entry : palletsPorVeiculo.entrySet()) {
            Veiculo veiculo = entry.getKey();
            List<Pallet> pallets = entry.getValue();
            boolean mesmoTipo = true;
            boolean mesmaCidade = true;
            double pesoTotal = 0;

            for (Pallet p : pallets) {
                if (!p.getMaterial().getTipo().equals(pallets.get(0).getMaterial().getTipo())) mesmoTipo = false;
                if (!p.getCliente().getCidade().equals(pallets.get(0).getCliente().getCidade())) mesmaCidade = false;
                if (!estoque.consumirPallet(p)) hardScore--;
                pesoTotal += p.getPeso();
                mediumScore += p.getPontuacao();
            }
            if (pesoTotal > veiculo.getPesoMax() || pallets.size() > veiculo.getQuantidadeMax()) {
                hardScore--;
            }
            if (pesoTotal < veiculo.getPesoMin() && pallets.size() < veiculo.getQuantidadeMin()) {
                hardScore--;
            }
            if (!mesmoTipo) {
                hardScore--;
            }
            if (!mesmaCidade) {
                hardScore--;
            }
            if (pallets.size() > 0) {
                softScore--;
            }
        }
        return HardMediumSoftScore.of(hardScore, mediumScore, softScore);
    }

}
