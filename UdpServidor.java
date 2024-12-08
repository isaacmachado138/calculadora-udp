import java.io.*;  // Importa classes para operações de entrada e saída.
import java.net.*; // Importa classes para operações de rede (sockets, pacotes, etc.).

public class UdpServidor { // Declaração da classe principal do servidor UDP.
    public static void main(String[] args) { // Método principal que executa o programa.
        DatagramSocket aSocket = null; // Declaração de um socket UDP, inicialmente nulo.

        try {
            aSocket = new DatagramSocket(6789); // Cria um socket UDP na porta 6789.
            byte[] buffer = new byte[100]; // Cria um buffer para armazenar os dados recebidos (tamanho 100 bytes).
            System.out.println("AGUARDANDO REQUISICAO"); // Exibe mensagem no console indicando que o servidor está aguardando.

            while (true) { // Loop infinito para processar múltiplas requisições.
                DatagramPacket request = new DatagramPacket(buffer, buffer.length); // Cria um pacote para receber dados.
                aSocket.receive(request); // Aguarda o recebimento de uma mensagem no socket.
                
                String message = new String(request.getData(), 0, request.getLength()).trim(); // Converte os dados recebidos do pacote UDP para uma string, removendo espaços em branco no início e no fim
                System.out.println("Mensagem recebida: " + message); //exibe a mensagem recebida no console

                String response; // Declara uma variável para armazenar a resposta.
                try {
                    response = calcWithOperator(message); // response recebe retorno da função de calculo
                } catch (Exception e) {
                    response = "Erro: " + e.getMessage(); // Responde com o erro, se houver
                }


                DatagramPacket reply = new DatagramPacket( // Cria um pacote de resposta.
                    response.getBytes(),         // Copia os dados recebidos.
                    response.getBytes().length,  // Copia o tamanho dos dados recebidos.
                    request.getAddress(),      // Define o endereço do remetente.
                    request.getPort()          // Define a porta do remetente.
                );

                System.out.println("ENVIANDO RESPOSTA PARA...: " + message); // Exibe a mensagem recebida no console.
                aSocket.send(reply); // Envia o pacote de resposta de volta para o remetente.
            }
        } catch (SocketException e) { // Trata exceções relacionadas ao socket.
            System.out.println("Socket: " + e.getMessage()); // Exibe a mensagem de erro do socket.
        } catch (IOException e) { // Trata exceções de entrada/saída.
            System.out.println("IO: " + e.getMessage()); // Exibe a mensagem de erro de IO.
        } finally {
            if (aSocket != null) { // Verifica se o socket foi criado.
                aSocket.close(); // Fecha o socket para liberar os recursos.
            }
        }
    }

    // Método para processar o cálculo
    private static String calcWithOperator(String message) throws Exception {
        // Divide a mensagem recebida nos componentes
        String[] parts = message.split(" "); // Divide a string da mensagem em partes, usando espaço como delimitador.
        if (parts.length != 3) { // Verifica se a mensagem contém exatamente três partes.
            throw new Exception("Formato inválido. Use: <número1> <operador> <número2>"); // Lança uma exceção se o formato estiver incorreto.
        }

        double num1 = Double.parseDouble(parts[0]); // Converte a primeira parte da mensagem para um número do tipo double.
        String operator = parts[1]; // Atribui a segunda parte da mensagem ao operador.
        double num2 = Double.parseDouble(parts[2]); // Converte a terceira parte da mensagem para um número do tipo double.


        System.out.println("******************* SEPARANDO CADEIA DE CARACTERES *******************"); // Exibe a mensagem recebida no console.
        System.out.println("NUMERO 1: " + num1); // Exibe o primeiro número no console.
        System.out.println("OPERADOR: " + operator); // Exibe o operador no console.
        System.out.println("NUMERO 2: " + num2); // Exibe o segundo número no console.


        System.out.println("******************* PROCESSANDO OPERAÇÕES *******************"); // Exibe o primeiro número no console.

        // Realiza o cálculo com base no operador
        double result; // Declara uma variável para armazenar o resultado.
        switch (operator) { // Verifica o operador especificado.
            case "+": // Se o operador for adição.
            result = num1 + num2; // Realiza a adição.
            System.out.println("Adição"); // Exibe "Adição" no console.
            break; 
            case "-": // Se o operador for subtração.
            result = num1 - num2; // Realiza a subtração.
            System.out.println("Subtração"); // Exibe "Subtração" no console.
            break;
            case "x": // Se o operador for multiplicação.
            result = num1 * num2; // Realiza a multiplicação.
            System.out.println("Multiplicação"); // Exibe "Multiplicação" no console.
            break;
            case "/": // Se o operador for divisão.
            if (num2 == 0) { // Verifica se o divisor é zero.
                throw new Exception("Divisão por zero não é permitida."); // Lança uma exceção se houver divisão por zero.
            }
            result = num1 / num2; // Realiza a divisão.
            System.out.println("Divisão"); // Exibe "Divisão" no console.
            break;
            default:
            throw new Exception("Operador inválido. Use +, -, x ou /."); // Lança uma exceção se o operador for inválido.
        }

        return String.valueOf(result); // Converte o resultado numérico para String antes de retornar.
    }
}