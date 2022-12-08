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
    private Map<Cliente, Integer> clientesPrioritarios;
    private Map<Material, Integer> materiaisPrioritarios;
    private Map<String, Double> pesos;

    public ScoreCalculator(Map<Cliente, Integer> clientesPrioritarios, Map<Material, Integer> materiaisPrioritarios, Map<String, Double> pesos) {
        this.clientesPrioritarios = clientesPrioritarios;
        this.materiaisPrioritarios = materiaisPrioritarios;
        this.pesos = pesos;
    }

    private double normalizar(double max, double num) {
        return ((100 * num) / max);
    }

    public void calcularPontuacao(List<Pallet> pallets) {
        Double maiorPeso = Double.MIN_VALUE;
        int maiorClientePrioritario = Integer.MIN_VALUE;
        int maiorMaterialPrioritario = Integer.MIN_VALUE;
        for (Pallet p : pallets) {
            maiorPeso = Double.max(maiorPeso, p.getPeso());
        }
        for (Map.Entry<Cliente, Integer> entry : clientesPrioritarios.entrySet()) {
            maiorClientePrioritario = Integer.max(maiorClientePrioritario, entry.getValue());
        }
        for (Map.Entry<Material, Integer> entry : materiaisPrioritarios.entrySet()) {
            maiorMaterialPrioritario = Integer.max(maiorMaterialPrioritario, entry.getValue());
        }

        for (Pallet p : pallets) {
            p.setPontuacao((int) (
                      normalizar(maiorPeso, p.getPeso())                                            //peso
                    + normalizar(maiorClientePrioritario, clientesPrioritarios.get(p.getCliente())) //prioridade de cliente
                    + normalizar(maiorClientePrioritario, clientesPrioritarios.get(p.getCliente())) //prioridade de material
                )/3
            );
        }
    }
}


