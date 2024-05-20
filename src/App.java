import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class App {

    private static final String URL_API = "https://v6.exchangerate-api.com/v6/1866eb8fa7051f96999051be/latest/USD";

    public static void main(String[] args) {
        try {
            // Fazendo a requisição
            URL url = new URL(URL_API);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convertendo para JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader(request.getInputStream()));
            JsonObject jsonobj = root.getAsJsonObject();

            // Acessando o objeto
            String req_result = jsonobj.get("result").getAsString();
            if (!"success".equals(req_result)) {
                System.out.println("Falha ao obter as taxas de câmbio.");
                return;
            }

            JsonObject taxasConversao = jsonobj.getAsJsonObject("conversion_rates");

            // Criando um menu para o usuário selecionar a conversão
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Menu de Conversão de Moeda");
                System.out.println("1. Reais para Dólar");
                System.out.println("2. Reais para Euro");
                System.out.println("3. Reais para Libra Esterlina");
                System.out.println("4. Reais para Peso Argentino");
                System.out.println("5. Reais para Peso Chileno");
                System.out.println("6. Dólar para Reais");
                System.out.println("7. Euro para Reais");
                System.out.println("8. Libra Esterlina para Reais");
                System.out.println("9. Peso Argentino para Reais");
                System.out.println("10. Peso Chileno para Reais");
                System.out.println("11. Sair");
                System.out.print("Selecione uma opção: ");
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        converterDeReais("Dólar", "USD", taxasConversao);
                        break;
                    case 2:
                        converterDeReais("Euro", "EUR", taxasConversao);
                        break;
                    case 3:
                        converterDeReais("Libra Esterlina", "GBP", taxasConversao);
                        break;
                    case 4:
                        converterDeReais("Peso Argentino", "ARS", taxasConversao);
                        break;
                    case 5:
                        converterDeReais("Peso Chileno", "CLP", taxasConversao);
                        break;
                    case 6:
                        converterParaReais("Dólar", "USD", taxasConversao);
                        break;
                    case 7:
                        converterParaReais("Euro", "EUR", taxasConversao);
                        break;
                    case 8:
                        converterParaReais("Libra Esterlina", "GBP", taxasConversao);
                        break;
                    case 9:
                        converterParaReais("Peso Argentino", "ARS", taxasConversao);
                        break;
                    case 10:
                        converterParaReais("Peso Chileno", "CLP", taxasConversao);
                        break;
                    case 11:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void converterDeReais(String paraMoeda, String codigoMoeda, JsonObject taxasConversao) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Digite o valor em Reais para converter para %s: ", paraMoeda);
        double valorReais = scanner.nextDouble();
        if (taxasConversao.has(codigoMoeda)) {
            double taxa = taxasConversao.get(codigoMoeda).getAsDouble();
            double valorConvertido = valorReais / taxa;
            System.out.printf("Valor convertido: %.2f %s%n", valorConvertido, paraMoeda);
        } else {
            System.out.println("Código de moeda inválido.");
        }
    }

    private static void converterParaReais(String deMoeda, String codigoMoeda, JsonObject taxasConversao) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Digite o valor em %s para converter para Reais: ", deMoeda);
        double valorMoeda = scanner.nextDouble();
        if (taxasConversao.has(codigoMoeda)) {
            double taxa = taxasConversao.get(codigoMoeda).getAsDouble();
            double valorConvertido = valorMoeda * taxa;
            System.out.printf("Valor convertido: %.2f BRL%n", valorConvertido);
        } else {
            System.out.println("Código de moeda inválido.");
        }
    }
}
