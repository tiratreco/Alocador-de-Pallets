package Solucao;

import Entidades.Pallet;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.List;



@Getter
@Setter
public class Estoque {
    static Map<Material, Integer> estoqueInicial = null;

    private Map<Material, Integer> estoque;

    public Estoque (Map<Material, Integer> materiais) {
        estoqueInicial = materiais;
        new Estoque();
    }

    public Estoque (){
        this.estoque = estoqueInicial;
    }

    public boolean existeEstoqueMaterial (Material material, int quantidade) {
        return estoque.get(material) >= quantidade;
    }

    public Material existeEstoquePallet(Pallet pallet){
        if (existeEstoqueMaterial(pallet.getMaterial(), pallet.getCaixas())) return pallet.getMaterial();
        if (pallet.getCliente().isAceitaEstoqueAlternativo()){
            for (Material m : pallet.getMaterial().getAlternativo()){
                if (existeEstoqueMaterial(m, pallet.getCaixas())) return m;
            }
        }
        return null;
    }

    public boolean consumirPallet (Pallet pallet){
        if  (pallet.getNumeroRemessa() != null) return true;
        Material m = existeEstoquePallet(pallet);
        if (m == null) return false;
        estoque.put(m, estoque.get(m) - pallet.getCaixas());
        return true;
    }
}
