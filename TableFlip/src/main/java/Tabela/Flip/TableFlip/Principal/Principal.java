package Tabela.Flip.TableFlip.Principal;

import Tabela.Flip.TableFlip.Modelo.Dados;
import Tabela.Flip.TableFlip.Modelo.Modelos;
import Tabela.Flip.TableFlip.Modelo.Veiculo;
import Tabela.Flip.TableFlip.Servico.ConsumoApi;
import Tabela.Flip.TableFlip.Servico.ConverterDados;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverterDados conversor = new ConverterDados();

    private  String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void menu(){

         var menu = """
                ***OPCOES***
                
                carros
                
                motos
                
                caminhoes
                
                Digite uma das opçoes para consultar valores:
                """;
        System.out.println(menu);

        var opcao = leitura.nextLine();
        String marcaVeiculo;

        if(opcao.toLowerCase().contains("carr")){
            marcaVeiculo = ENDERECO + "/carros/marcas";
        } else if(opcao.toLowerCase().contains("mot")){
            marcaVeiculo = ENDERECO + "/motos/marcas";
        } else {
            ENDERECO = "caminhoes/marcas";
        }

        var json = consumo.obterDados(ENDERECO);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream().sorted(Comparator.comparing(Dados::codigo)).forEach(System.out::println);

        System.out.println("Digite o código do modelo para consultar valores: ");
        var codigoMarca = leitura.nextLine();

        ENDERECO = ENDERECO + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(ENDERECO);

        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream().sorted(Comparator.comparing(Dados::codigo)).forEach(System.out::println);


        var trechoModelo = leitura.nextLine();
        System.out.println("Digite um trecho do modelo  que deseja buscar: ");
        Optional<Dados> marcaBuscada = marcas.stream()
        .filter(e -> e.codigo().toUpperCase().contains(trechoModelo.toLowerCase())).findAny();

        var nomeVeiculo = leitura.nextLine();
        System.out.println("Digite um trecho do nome do carro a ser buscado: ");
        List<Dados> modelosFiltrados = modeloLista.modelos().stream().filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o codigo do modelo que deseja buscar os valores da avaliacao: ");
        var codigoModelo = leitura.nextLine();

        ENDERECO = ENDERECO + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(ENDERECO);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0 ; i < anos.size(); i++){
            var enderecoAnos = ENDERECO + "/" + anos.get(i). codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculo.add(veiculo);
        }
        System.out.println("\nTodos os veiculos filtrados com avaliação por ano");
    }
}
