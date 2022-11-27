package Entidades.Calculadores;

import Entidades.Pallet;
import Entidades.Solucao;
import Entidades.Veiculo;
import Solucao.Estoque;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EasyScore implements EasyScoreCalculator<Solucao, BendableScore> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EasyScore.class);

    @Override
    public BendableScore calculateScore(Solucao solucao) {
        List<Pallet> palletList = solucao.getPallets();
        Estoque estoque = new Estoque();
        int hardScore = 0;
        int mediumScoreFormados = 0;
        int mediumScore = 0;
        int softScore = 0;

        Map<Veiculo, List<Pallet>> palletsPorVeiculo = palletList.stream().filter(p->p.getVeiculo()!=null).collect(Collectors.groupingBy(Pallet::getVeiculo));
        for (Map.Entry<Veiculo, List<Pallet>> entry : palletsPorVeiculo.entrySet()) {
            int hardScoreVeiculo = 0;
            int mediumScoreVeiculo = 0;
            Veiculo veiculo = entry.getKey();
            List<Pallet> pallets = entry.getValue();
            double pesoTotal = 0;

            for (Pallet p : pallets) {
                if (!p.getMaterial().getTipo().equals(pallets.get(0).getMaterial().getTipo())) hardScoreVeiculo++;
                if (!p.getCliente().getCidade().equals(pallets.get(0).getCliente().getCidade())) hardScoreVeiculo++;
                pesoTotal += p.getPeso();
                mediumScoreVeiculo += p.getPontuacao();
            }
            if (pesoTotal > veiculo.getPesoMax() || pallets.size() > veiculo.getQuantidadeMax()) hardScoreVeiculo++;
            if (!estoque.consumirPallets(pallets)) hardScoreVeiculo++;

            if (hardScoreVeiculo == 0){
                if (pesoTotal < veiculo.getPesoMin() || pallets.size() < veiculo.getQuantidadeMin()) {
                    mediumScore += mediumScoreVeiculo;
                } else {
                    mediumScoreFormados += mediumScoreVeiculo;
                    softScore += veiculo.getCusto();
                }
            }

            hardScore += hardScoreVeiculo;

        }

        return BendableScore.of(new int[]{-hardScore}, new int[]{mediumScoreFormados, mediumScore, -softScore});
    }

}
