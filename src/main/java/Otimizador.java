import Entidades.*;
import Solucao.*;
import Importador.Importador;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.stream.Collectors;


public class Otimizador {
    private static final Logger LOGGER = LoggerFactory.getLogger(Otimizador.class);

    public static void main(String[] args) {
        Importador importador = new Importador();
        importador.lerArquivos();


        Solucao solucaoInicial = new Solucao();
        new Estoque(importador.getEstoqueMap());
        solucaoInicial.setPallets(importador.getPalletList());
        //solucaoInicial.setPallets(importador.getPalletList());
        solucaoInicial.setVeiculos(importador.getVeiculoList());

        File config = new File("config1.xml");
        SolverFactory<Solucao> solverFactory = SolverFactory.createFromXmlResource(config.toString());
        Solver<Solucao> solver = solverFactory.buildSolver();
        Solucao solucao = solver.solve(solucaoInicial);
        LOGGER.info(solucao.toString());
        LOGGER.info("Execução concluída");

    }

}
