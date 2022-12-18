import Entidades.*;
import Score.ScoreCalculator;
import Solucao.*;
import Importador.Importador;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Otimizador {
    private static final Logger LOGGER = LoggerFactory.getLogger(Otimizador.class);

    public static void main(String[] args) {


        Solucao solucaoInicial = gerarSolucaoInicial();

        File config = new File("config1.xml");
        SolverFactory<Solucao> solverFactory = SolverFactory.createFromXmlResource(config.toString());
        Solver<Solucao> solver = solverFactory.buildSolver();
        Solucao solucao = solver.solve(solucaoInicial);
        LOGGER.info(solucao.toString());
        LOGGER.info("Execução concluída");

    }

    private static Solucao gerarSolucaoInicial(){
        Importador importador = new Importador();
        importador.lerArquivos();


        Solucao solucaoInicial = new Solucao();
        new Estoque(importador.getEstoqueMap());
        ScoreCalculator geradorDeScore = new ScoreCalculator(importador.getPesoMap());
        geradorDeScore.calcularPontuacao(importador.getPalletList(), new ArrayList<>(importador.getClienteMap().values()), new ArrayList<>(importador.getMaterialMap().values()));

        solucaoInicial.setPallets(importador.getPalletList());
        solucaoInicial.setVeiculos(importador.getVeiculoList());


        return solucaoInicial;
    }

}
