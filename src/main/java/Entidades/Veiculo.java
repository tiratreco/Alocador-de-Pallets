package Entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Veiculo {

    private String destino;

    // Caracteristicas do veiculo
    private double pesoMax;
    private int quantidadeMax;
    private double pesoMin;
    private int quantidadeMin;
}
