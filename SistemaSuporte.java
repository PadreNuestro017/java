import java.util.Scanner;
public class SistemaSuporte {
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       String[][] chamados = new String[100][5]; // ID, Usuário, Descrição, Prioridade, Status
       int contadorChamados = 0;
       while (true) {
           String usuarioLogado = "";
           String tipoUsuario = "";
           // Tela de login
           System.out.println("\n=== LOGIN ===");
           System.out.print("Digite seu nome de usuário: ");
           usuarioLogado = sc.nextLine();
           if (usuarioLogado.equalsIgnoreCase("admin")) {
               System.out.print("Digite a senha: ");
               String senha = sc.nextLine();
               if (!senha.equals("admin$")) {
                   System.out.println("Senha incorreta. Tente novamente.");
                   continue;
               }
               tipoUsuario = "admin";
           } else {
               tipoUsuario = "usuario";
           }
           int opcao;
           do {
               System.out.println("\n=== SISTEMA DE SUPORTE ===");
               System.out.println("Usuário logado: " + usuarioLogado + " (" + tipoUsuario + ")");
               System.out.println("1 - Criar Chamado");
               System.out.println("2 - Verificar Chamados");
               System.out.println("3 - Trocar de Usuário");
               System.out.println("0 - Sair");
               System.out.print("Escolha uma opção: ");
               opcao = sc.nextInt();
               sc.nextLine();
               switch (opcao) {
                   case 1:
                       // Criar chamado
                       System.out.println("\n--- CRIAR CHAMADO ---");
                       System.out.print("Descreva o problema: ");
                       String descricao = sc.nextLine();
                       System.out.print("Prioridade (Baixa / Media / Alta): ");
                       String prioridade = sc.nextLine();
                       chamados[contadorChamados][0] = String.valueOf(contadorChamados + 1);
                       chamados[contadorChamados][1] = usuarioLogado;
                       chamados[contadorChamados][2] = descricao;
                       chamados[contadorChamados][3] = prioridade;
                       chamados[contadorChamados][4] = "Aberto";
                       contadorChamados++;
                       System.out.println("Chamado criado com sucesso!");
                       break;
                   case 2:
                       // Verificar chamados
                       boolean encontrados = false;
                       System.out.println("\n--- LISTA DE CHAMADOS ---");
                       for (int i = 0; i < contadorChamados; i++) {
                           if (tipoUsuario.equals("admin") || chamados[i][1].equals(usuarioLogado)) {
                               System.out.println("ID: " + chamados[i][0] +
                                       " | Nome: " + chamados[i][1] +
                                       " | Prioridade: " + chamados[i][3] +
                                       " | Status: " + chamados[i][4]);
                               encontrados = true;
                           }
                       }
                       if (!encontrados) {
                           System.out.println("Nenhum chamado encontrado.");
                           break;
                       }
                       System.out.print("\nDigite o ID do chamado para gerenciar (ou 0 para voltar): ");
                       String idEscolhido = sc.nextLine();
                       if (!idEscolhido.equals("0")) {
                           int indice = -1;
                           for (int i = 0; i < contadorChamados; i++) {
                               if (chamados[i][0].equals(idEscolhido)) {
                                   if (tipoUsuario.equals("admin") || chamados[i][1].equals(usuarioLogado)) {
                                       indice = i;
                                   }
                                   break;
                               }
                           }
                           if (indice != -1) {
                               int subOpcao;
                               do {
                                   System.out.println("\n--- GERENCIAR CHAMADO ID " + idEscolhido + " ---");
                                   System.out.println("1 - Ver Detalhes");
                                   if (tipoUsuario.equals("admin")) {
                                       System.out.println("2 - Editar Descrição");
                                       System.out.println("3 - Alterar Prioridade");
                                       System.out.println("4 - Fechar Chamado");
                                   }
                                   System.out.println("0 - Voltar");
                                   System.out.print("Escolha uma opção: ");
                                   subOpcao = sc.nextInt();
                                   sc.nextLine();
                                   switch (subOpcao) {
                                       case 1:
                                           System.out.println("\n--- DETALHES DO CHAMADO ---");
                                           System.out.println("ID: " + chamados[indice][0]);
                                           System.out.println("Nome: " + chamados[indice][1]);
                                           System.out.println("Descrição: " + chamados[indice][2]);
                                           System.out.println("Prioridade: " + chamados[indice][3]);
                                           System.out.println("Status: " + chamados[indice][4]);
                                           break;
                                       case 2:
                                           if (tipoUsuario.equals("admin")) {
                                               System.out.print("Nova descrição: ");
                                               chamados[indice][2] = sc.nextLine();
                                               System.out.println("Descrição atualizada.");
                                           } else {
                                               System.out.println("Apenas administradores podem editar a descrição.");
                                           }
                                           break;
                                       case 3:
                                           if (tipoUsuario.equals("admin")) {
                                               System.out.print("Nova prioridade (Baixa / Media / Alta): ");
                                               chamados[indice][3] = sc.nextLine();
                                               System.out.println("Prioridade atualizada.");
                                           } else {
                                               System.out.println("Apenas administradores podem alterar a prioridade.");
                                           }
                                           break;
                                       case 4:
                                           if (tipoUsuario.equals("admin")) {
                                               chamados[indice][4] = "Fechado";
                                               System.out.println("Chamado fechado com sucesso.");
                                           } else {
                                               System.out.println("Apenas administradores podem fechar chamados.");
                                           }
                                           break;
                                       case 0:
                                           break;
                                       default:
                                           System.out.println("Opção inválida.");
                                   }
                               } while (subOpcao != 0);
                           } else {
                               System.out.println("Chamado não encontrado ou acesso negado.");
                           }
                       }
                       break;
                   case 3:
                       System.out.println("Trocando de usuário...");
                       opcao = -1;
                       break;
                   case 0:
                       System.out.println("Encerrando o sistema...");
                       sc.close();
                       return;
                   default:
                       System.out.println("Opção inválida. Tente novamente.");
               }
           } while (opcao != -1); // troca de usuário
       }
   }
}
