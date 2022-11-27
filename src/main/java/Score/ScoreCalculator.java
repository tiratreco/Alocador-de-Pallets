package Score;

import Entidades.Pallet;
import Solucao.Cliente;
import Solucao.Material;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ScoreCalculator {
    private Map<Cliente, Double> clientesPrioritarios;
    private Map<Material, Double> materiaisPrioritarios;
    private Map<String, Double> pesos;

    private int pontuar (double max, double num){
        return (int) ((100*num) / max);
    }

    public void calcularPontuacao (List<Pallet> pallets) {
        Double maiorPeso = Double.MAX_VALUE;
        for (Pallet p : pallets){
            maiorPeso = Double.max(maiorPeso, p.getPeso());
        }
/*
* max - 100
* n - y
* */

        for (Pallet p : pallets){
            //set
        }
    }
}
