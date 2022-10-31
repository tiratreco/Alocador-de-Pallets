package Solucao;

import Solucao.Material;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.List;

@Getter
@Setter
public static Map<String, Material> estoqueInicial = null;

public class Estoque {
    private Map<String, Material> estoque;

    public Estoque (List<Material> materiais) {
        this.estoqueInicial = new HashMap<>();
        materiais.foreach(material->this.estoqueInicial.put(material.getCodigo(), material));
        return Estoque();
    }

    public Estoque (){
        this.estoque = estoqueInicial;
    }

    public existeEstoqueMaterial (Material material, int quantidade) {
        return estoque.get(material.getCodigo()) >= quantidade;
    }

    public Material existeEstoquePallet(Pallet pallet){
        if (existeEstoqueMaterial(pallet.getMaterial(), pallet.getCaixas())){
            return pallet.getMaterial;
        }
        if (pallet.getCliente().getAceitaEstoqueAlternativo()){
            for (Material m : pallet.getMaterial().getAlternativo()){
                if (existeEstoqueMaterial(m, pallet.getCaixas())) return m;
            }
        }
        return null;
    }

    public bool consumir (Pallet pallet){
        Material m = existeEstoquePallet(pallet);
        if (m == null) return false;
        estoque.get(material.getCodigo()) -= pallet.getCaixas();
        return true;
    }
}
