

public class EasyScoreCalculator implements EasyScoreCalculator<Solucao, HardSoftScore> {

    @Override
    public HardMediumSoftScore calculateScore(Solucao solucao) {
	    List<Pallet> palletList = solucao.getPallets();
        Estoque estoque = solucao.getEstoque();
        int hardScore = 0;
        int mediumScore = 0;
	    int softScore = 0;

        Map<Veiculo, List<Pallet>> palletsPorVeiculo = palletList.stream().collect(Collectors.groupingBy(p -> p.getVeiculo()));
        Estoque.reset();
        for (Map.Entry<Veiculo, List<Pallet>> entry : palletsPorVeiculo.entrySet()) {
            Veiculo veiculo = entry.getKey();
            List<Pallet> pallets = entry.getValue();
            bool mesmoTipo = true;
            double pesoTotal = 0;

            for (Pallet p : pallets){
                if (p.getTipo() != pallets.get(0)) mesmoTipo = false;
                if (!estoque.consumir(p)) hardScore--;
                pesoTotal += p.getPeso();
                mediumScore += p.getScore();
            }
            if (pesoTotal > veiculo.getPesoMax() || pallets.size() > veiculo.getQuantidadeMax()){
                hardScore--;
            }
            if (pesoTotal < veiculo.getPesoMin() && pallets.size() < veiculo.getQuantidadeMin()){
                hardScore--;
            }
            if (!mesmoTipo){
                hardScore--;
            }
            if (pallets.size() > 0){
            softScore--;
            }
        }
        return HardMediumSoftScore.of(hardScore, mediumScore, softScore);
    }

}
