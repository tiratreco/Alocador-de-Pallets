import Entidades.Solucao;
import org.kie.api.io.Resource;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

public class Otimizador {
    private static final Logger LOGGER = LoggerFactory.getLogger(Otimizador.class);
    public static void main(String[] args) {
        File config = new File("config1.xml");
        SolverFactory<Solucao> solverFactory = SolverFactory.createFromXmlResource(config.toString());
        Solver<Solucao> solver = solverFactory.buildSolver();
        LOGGER.info("Execução concluída");

    }

}
