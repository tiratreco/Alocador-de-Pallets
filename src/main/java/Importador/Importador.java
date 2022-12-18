package Importador;

import Entidades.Pallet;
import Entidades.Veiculo;
import Solucao.Cliente;
import Solucao.Material;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Importador {
    private static final Logger LOGGER = LoggerFactory.getLogger(Importador.class);

    private List<Pallet> palletList;
    private Map<String, Material> materialMap;
    private Map<String, Cliente> clienteMap;
    private Map<Material, Integer> estoqueMap;
    private List<Veiculo> veiculoList;
    private Map<String, Double> pesoMap;

    public void lerArquivos() {
        Reader reader1, reader2, reader3, reader4, reader5, reader6, reader7;
        try {
            reader1 = Files.newBufferedReader(Paths.get("data/palete.csv"));
            reader2 = Files.newBufferedReader(Paths.get("data/materiais.csv"));
            reader3 = Files.newBufferedReader(Paths.get("data/estoque.csv"));
            reader4 = Files.newBufferedReader(Paths.get("data/veiculo.csv"));
            reader5 = Files.newBufferedReader(Paths.get("data/peso.csv"));
            reader6 = Files.newBufferedReader(Paths.get("data/prioridadecliente.csv"));
            reader7 = Files.newBufferedReader(Paths.get("data/prioridadematerial.csv"));
        } catch (Exception e) {
            LOGGER.error("Erro na abertura do arquivo!");
            return;
        }
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader1 = new CSVReaderBuilder(reader1).withCSVParser(parser).build();
        CSVReader csvReader2 = new CSVReaderBuilder(reader2).withCSVParser(parser).build();
        CSVReader csvReader3 = new CSVReaderBuilder(reader3).withCSVParser(parser).build();
        CSVReader csvReader4 = new CSVReaderBuilder(reader4).withCSVParser(parser).build();
        CSVReader csvReader5 = new CSVReaderBuilder(reader5).withSkipLines(1).withCSVParser(parser).build();
        CSVReader csvReader6 = new CSVReaderBuilder(reader6).withCSVParser(parser).build();
        CSVReader csvReader7 = new CSVReaderBuilder(reader7).withCSVParser(parser).build();
        List<String[]> paletes;
        List<String[]> materiais;
        List<String[]> estoques;
        List<String[]> veiculos;
        List<String[]> pesos;
        List<String[]> prioridadeCliente;
        List<String[]> prioridadeMaterial;
        try {
            paletes = csvReader1.readAll();
            materiais = csvReader2.readAll();
            estoques = csvReader3.readAll();
            veiculos = csvReader4.readAll();
            pesos = csvReader5.readAll();;
            prioridadeCliente = csvReader6.readAll();;
            prioridadeMaterial = csvReader7.readAll();
        } catch (Exception e) {
            LOGGER.error("Erro na abertura dos arquivos!");
            return;
        }

        clienteMap = new HashMap<>();
        for (String[] linha : paletes) {
            if (!clienteMap.containsKey(linha[0])) {
                Cliente c = new Cliente();
                c.setCodigo(linha[0]);
                c.setCidade(linha[6]);
                clienteMap.put(linha[0], c);
            }
        }

        // lendo materiais
        // dos pedidos
        materialMap = new HashMap<>();
        for (String[] linha : paletes) {
            if (!materialMap.containsKey(linha[1])) {
                Material m = new Material();
                m.setCodigo(linha[1]);
                m.setTipo(linha[2]);
                materialMap.put(linha[1], m);
            }
        }
        //do estoque alternativo
        for (String[] linhaMaterial : materiais) {
            if (!materialMap.containsKey(linhaMaterial[0])) {
                Material m = new Material();
                m.setCodigo(linhaMaterial[0]);
                materialMap.put(linhaMaterial[0], m);
            }
            if (!materialMap.containsKey(linhaMaterial[1])) {
                Material m = new Material();
                m.setCodigo(linhaMaterial[1]);
                materialMap.put(linhaMaterial[1], m);
            }
        }
        //do estoque
        estoqueMap = new HashMap<>();
        for (String[] linhaEstoque : estoques) {
            if (!materialMap.containsKey(linhaEstoque[0])) {
                Material m = new Material();
                m.setCodigo(linhaEstoque[0]);
                materialMap.put(linhaEstoque[0], m);
            }
            estoqueMap.put(materialMap.get(linhaEstoque[0]), Integer.parseInt(linhaEstoque[1]));
        }

        //Lendo estoque alternativo
        materialMap.forEach((s, material) -> material.setAlternativo(new ArrayList<>()));
        for (String[] linhaMaterial : materiais) {
            materialMap.get(linhaMaterial[0]).getAlternativo().add(materialMap.get(linhaMaterial[1]));
        }

        //lendo pallets
        palletList = new ArrayList<>();
        for (String[] linha : paletes) {
            Pallet p = new Pallet();
            p.setCliente(clienteMap.get(linha[0]));
            p.setMaterial(materialMap.get(linha[1]));
            p.setPeso(Double.parseDouble(linha[4]));
            p.setCaixas(Integer.parseInt(linha[5]));
            p.setNumeroRemessa(linha[3].equals("null")?null:linha[3]);
            palletList.add(p);
        }

        //lendo veiculos
        veiculoList = new ArrayList<>();
        for (String[] veiculo : veiculos) {
            int num = Integer.parseInt(veiculo[6]);
            for (int i = 0; i < num; i++) {
                Veiculo v = new Veiculo();
                v.setPesoMax(Double.parseDouble(veiculo[1]));
                v.setPesoMin(Double.parseDouble(veiculo[2]));
                v.setQuantidadeMax(Integer.parseInt(veiculo[3]));
                v.setQuantidadeMin(Integer.parseInt(veiculo[4]));
                v.setCusto(Integer.parseInt(veiculo[5]));
                veiculoList.add(v);
            }
        }

        //lendo prioridades
        pesoMap = new HashMap<>();
        pesoMap.put("volume", Double.parseDouble(pesos.get(0)[0]));
        pesoMap.put("cliente", Double.parseDouble(pesos.get(0)[1]));
        pesoMap.put("material", Double.parseDouble(pesos.get(0)[2]));

        for (String[] linha : prioridadeCliente) {
            try {
                clienteMap.get(linha[0]).setPrioridade(Integer.parseInt(linha[1]));
            } catch (NumberFormatException e) {
                LOGGER.error(String.format("Erro ao converter prioridade: %s", linha[1]));
            } catch (NullPointerException e) {
                LOGGER.error(String.format("Erro - Cliente %s não encontrado", linha[0]));
            }
        }

        for (String[] linha : prioridadeMaterial) {
            try {
                materialMap.get(linha[0]).setPrioridade(Integer.parseInt(linha[1]));
            } catch (NumberFormatException e) {
                LOGGER.error(String.format("Erro ao converter prioridade: %s", linha[1]));
            } catch (NullPointerException e) {
                LOGGER.error(String.format("Erro - Material %s não encontrado", linha[0]));
            }
        }
    }
}
