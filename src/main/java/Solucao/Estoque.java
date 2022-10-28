package Solucao;

import Solucao.Material;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.List;

@Getter
@Setter
public class Estoque {
    private Map<String, Material> estoque;

    public Estoque (List<Material> materiais) {
        
    }
}
