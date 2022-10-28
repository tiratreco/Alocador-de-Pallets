

public class EasyScoreCalculator implements EasyScoreCalculator<Solucao, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(Solucao solucao) {
        List<Veiculo> veiculos = solucao.getVeiculos();
	List<Pallet> pallets = solucao.getPallets();
        int hardScore = 0;
	int softScore = 0;

	Map<Veiculo, Integer> quantidadePorVeiculo = new HashMap()<>;
	Map<Veiculo, Integer> pesoPorVeiculo = new HashMap()<>;
	for (Pallet p: pallets){
	    if (!quantidadePorVeiculo.containsKey(p.getVeiculo)) quantidadePorVeiculo.put(p.getVeiculo(), 0);
	    quantidadePorVeiculo.get(p.getVeiculo) ++;

	    if (!pesoPorVeiculo.containsKey(p.getVeiculo)) pesoPorVeiculo.put(p.getVeiculo(), 0);
	    pesoPorVeiculo.get(p.getVeiculo) += p.getPeso();

	    
	}


        return HardSoftScore.of(hardScore, softScore);
    }

}
