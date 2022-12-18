package Score;

import Entidades.Pallet;
import Solucao.Cliente;
import Solucao.Material;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ScoreCalculator {
    private Map<String, Double> pesos;

    public ScoreCalculator(Map<String, Double> pesos) {
        this.pesos = pesos;
    }

    private double normalizar(double max, double num) {
        return ((100 * num) / max);
    }

    public void calcularPontuacao(List<Pallet> pallets, List<Cliente> clienteList, List<Material> materialList) {
        Double maiorPeso = Double.MIN_VALUE;
        int maiorClientePrioritario = Integer.MIN_VALUE;
        int maiorMaterialPrioritario = Integer.MIN_VALUE;
        for (Pallet p : pallets) {
            maiorPeso = Double.max(maiorPeso, p.getPeso());
        }
        maiorClientePrioritario = clienteList.stream().max(Comparator.comparingInt(Cliente::getPrioridade)).get().getPrioridade();
        maiorMaterialPrioritario = materialList.stream().max(Comparator.comparingInt(Material::getPrioridade)).get().getPrioridade();

        for (Pallet p : pallets) {
            p.setPontuacao((int) (
                      normalizar(maiorPeso, p.getPeso())                                   //peso
                    + normalizar(maiorClientePrioritario, p.getCliente().getPrioridade())  //prioridade de cliente
                    + normalizar(maiorMaterialPrioritario, p.getMaterial().getPrioridade()) //prioridade de material
                )/3
            );
        }
    }
}


