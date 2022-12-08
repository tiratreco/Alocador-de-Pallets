package Entidades.Extra;

import Entidades.Pallet;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

public class PalletComparator implements Comparator<Pallet> {

    public int compare(Pallet a, Pallet b) {
        return new CompareToBuilder()
                .append(a.getPontuacao(), b.getPontuacao())
                .toComparison();
    }

}
