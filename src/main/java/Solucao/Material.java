package Solucao;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Material {
    private String codigo;

    private String tipo;

    private List<Material> alternativo;

    private int prioridade;
}
