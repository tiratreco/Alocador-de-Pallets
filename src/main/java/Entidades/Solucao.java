package Entidades;

import Solução.Estoque;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Solucao {

    private List<Veiculo> veiculos;

    private List<Pallet> pallets;

    private Estoque estoque;
}
