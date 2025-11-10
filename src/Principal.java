import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner tablero = new Scanner(System.in);
        Monedas moneda = new Monedas();
        Gson gson = new Gson();
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("====================================");
            System.out.println("        CONVERSOR DE MONEDAS");
            System.out.println("====================================");
            System.out.println("1) Dólar => Peso Argentino");
            System.out.println("2) Peso Argentino => Dólar");
            System.out.println("3) Dólar => Real Brasileño");
            System.out.println("4) Real Brasileño => Dólar");
            System.out.println("5) Dólar => Peso Colombiano");
            System.out.println("6) Peso Colombiano => Dólar");
            System.out.println("7) Salir");
            System.out.print("Elija una opción: ");
            opcion = tablero.nextInt();

            String par="a";
            String monedaDeOrigen ="a";
            String monedaDeConversion ="b";

            switch (opcion) { case 1 -> {par = moneda.getDolarAPeso();
                monedaDeOrigen= "Dolares";
                monedaDeConversion ="Pesos";
                }
                case 2 -> { par = moneda.getPesoADolar();
                monedaDeOrigen="Pesos ";
                monedaDeConversion ="Dolares";   }

                case 3 -> {
                    par = moneda.getDolarAReal();;
                    monedaDeOrigen="Dolares";
                    monedaDeConversion ="Reales";
                      }
                case 4 -> {
                    par = moneda.getRealADolar();
                    monedaDeOrigen="Reales";
                    monedaDeConversion ="Dolares";
                }
                case 5 -> {
                    par = moneda.getUsdAPesoCol();
                    monedaDeOrigen= "dolares";
                    monedaDeConversion ="pesos colombianos ";
                }   // USD → COP
                case 6 -> {
                    par = moneda.getPscolADolar();
                    monedaDeOrigen= "Pesos colombianos";
                    monedaDeConversion = "Dolares";

                }   // COP → USD
                case 7 -> {
                    System.out.println("Saliendo...");
                    break;
                }
                default -> {
                    System.out.println("⚠ Opción inválida. Intente nuevamente.");
                    continue;
                }
            }

            if (opcion == 7) break;

            String url = "https://v6.exchangerate-api.com/v6/cd924cc0096add515c0e16f4/pair/" + par;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            RespuestaAPI datos = gson.fromJson(response.body(), RespuestaAPI.class);

            System.out.println("\nTasa de conversión actual: " + datos.getConversion_rate());
            System.out.print("Ingrese la cantidad a convertir: ");
            double cantidad = tablero.nextDouble();

            double resultado = cantidad * datos.getConversion_rate();
            System.out.println("el valor de : "+cantidad + "  "+ monedaDeOrigen + "  es igual a  "+ (cantidad * resultado) +"  "+ monedaDeConversion+ "\n");
        }

        tablero.close();
    }
}