import java.io.*;  // Importa classes para operações de entrada e saída.
import java.net.*; // Importa classes para operações de rede (sockets, pacotes, etc.).

public class UdpCliente { // Declaração da classe principal do cliente UDP.
    public static void main(String[] args) { // Método principal que executa o programa.
        DatagramSocket aSocket = null; // Declaração de um socket UDP, inicialmente nulo.

        try { // abre bloco de tratamento de exceções
            aSocket = new DatagramSocket(); // Cria um socket UDP
            String mensagem = args[0] + " " + args[1] + " " + args[2]; // Concatena os argumentos para formar a expressão completa
            System.out.println("<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("MENSAGEM A SER ENVIADA AO SERVIDOR: " + mensagem); //exibe a mensagem que será enviada ao servidor
            byte[] m = mensagem.getBytes(); // Converte a mensagem para bytes
            InetAddress aHost = InetAddress.getByName(args[3]); // Endereço do servidor
            System.out.println("ENVIANDO A MENSAGEM (" + mensagem + ") PARA O SERVIDOR...");
            int serverPort = 6789; // Porta do servidor

            DatagramPacket request = new DatagramPacket( // Cria um pacote de solicitação.
                m,                  // Define os dados da mensagem (em bytes).
                mensagem.length(),   // Define o tamanho da mensagem.
                aHost,              // Define o endereço do servidor.
                serverPort          // Define a porta do servidor.
            );

            aSocket.send(request); // Envia o pacote de solicitação para o servidor.

            byte[] buffer = new byte[100]; // Cria um buffer para armazenar a resposta do servidor (tamanho 100 bytes).
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length); // Cria um pacote para armazenar a resposta recebida.

            System.out.println("AGUARDANDO RESPOSTA...");
            aSocket.receive(reply); // Aguarda a resposta do servidor e armazena no pacote reply.

            System.out.println("\n RESPOSTA DO SERVIDOR: " + new String(reply.getData())); // Exibe a resposta recebida no console.
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
}