public class ComparadorDeDificuldade implements Comparator<Pallet> {

    public int compare(Pallet a, Pallet b) {
        return new CompareToBuilder()
                .append(a.getPeso(), b.getPeso())
                .toComparison();
    }
}
