package Solucao;

import Entidades.Pallet;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class Estoque {
    static Map<Material, Integer> estoqueInicial;

    private Map<Material, Integer> estoque;

    public Estoque(Map<Material, Integer> materiais) { estoqueInicial = new HashMap<>(materiais); }

    public Estoque() {
        this.estoque = new HashMap<>(estoqueInicial);
    }

    public boolean existeEstoqueMaterial(Material material, int quantidade) {
        return estoque.getOrDefault(material, 0) >= quantidade;
    }

    public Material existeEstoquePallet(Pallet pallet) {
        if (existeEstoqueMaterial(pallet.getMaterial(), pallet.getCaixas())) return pallet.getMaterial();
        for (Material m : pallet.getMaterial().getAlternativo()) {
            if (existeEstoqueMaterial(m, pallet.getCaixas())) return m;
        }
        return null;
    }

    public boolean consumirPallet(Pallet pallet) {
        if (pallet.getNumeroRemessa() != null) return true;
        Material m = existeEstoquePallet(pallet);
        if (m == null) return false;
        estoque.put(m, estoque.get(m) - pallet.getCaixas());
        return true;
    }

    public boolean consumirPallets(@NotNull List<Pallet> pallets){
        Map<Material, Integer> quantidadeConsumida = new HashMap<>();
        for (Pallet p : pallets){
            Material m = existeEstoquePallet(p);
            if (m == null) {
                // devolver consumido
                for (Map.Entry<Material,Integer> entry : quantidadeConsumida.entrySet()){
                    estoque.put(entry.getKey(), estoque.get(entry.getKey()) + entry.getValue());
                }
                return false;
            }
            estoque.put(m, estoque.get(m) - p.getCaixas()); // consome
            quantidadeConsumida.put(m, p.getCaixas());
        }
        return true;
    }
}
