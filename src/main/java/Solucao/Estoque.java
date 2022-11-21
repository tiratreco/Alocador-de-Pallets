package Solucao;

import Entidades.Pallet;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class Estoque {
    static Map<Material, Integer> estoqueInicial;

    private Map<Material, Integer> estoque;

    public Estoque(Map<Material, Integer> materiais) {
        estoqueInicial = materiais;
    }

    public Estoque() {
        this.estoque = new HashMap<>(estoqueInicial);
    }

    public boolean existeEstoqueMaterial(Material material, int quantidade) {
        return estoque.containsKey(material) ? estoque.get(material) >= quantidade : false;
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
}
