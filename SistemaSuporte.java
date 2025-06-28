import java.util.Scanner;

public class SistemaSuporte {
    public static void main(String[] args) {
       //ler comandos do teclado
        Scanner sc = new Scanner(System.in);
        String[][] chamados = new String[100][5];
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

                try {
                    opcao = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                    continue;
                }

                switch (opcao) {
                    case 1:
                        // Criar chamado
                        System.out.println("\n--- CRIAR CHAMADO ---");
                        System.out.print("Descreva o problema: ");
                        String descricao = sc.nextLine();
                        System.out.print("Prioridade (Baixa / Media / Alta): ");
                        String prioridade = sc.nextLine();

                        if (!prioridade.equalsIgnoreCase("Baixa") &&
                            !prioridade.equalsIgnoreCase("Media") &&
                            !prioridade.equalsIgnoreCase("Alta")) {
                           //Coloquei exceptions aqui para que tentativas invalidas nao fiquem sem tratativa
                            throw new IllegalArgumentException("Prioridade inválida. Use: Baixa, Media ou Alta.");
                        }

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

                            if (indice == -1) {
                                throw new IllegalArgumentException("Chamado não encontrado ou acesso negado.");
                            }

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

                                try {
                                    subOpcao = Integer.parseInt(sc.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Opção inválida.");
                                    continue;
                                }

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
                                            throw new SecurityException("Apenas administradores podem editar a descrição.");
                                        }
                                        break;
                                    case 3:
                                        if (tipoUsuario.equals("admin")) {
                                            System.out.print("Nova prioridade (Baixa / Media / Alta): ");
                                            String novaPrioridade = sc.nextLine();
                                            if (!novaPrioridade.equalsIgnoreCase("Baixa") &&
                                                !novaPrioridade.equalsIgnoreCase("Media") &&
                                                !novaPrioridade.equalsIgnoreCase("Alta")) {
                                                throw new IllegalArgumentException("Prioridade inválida.");
                                            }
                                            chamados[indice][3] = novaPrioridade;
                                            System.out.println("Prioridade atualizada.");
                                        } else {
                                            throw new SecurityException("Apenas administradores podem alterar a prioridade.");
                                        }
                                        break;
                                    case 4:
                                        if (tipoUsuario.equals("admin")) {
                                            chamados[indice][4] = "Fechado";
                                            System.out.println("Chamado fechado com sucesso.");
                                        } else {
                                            throw new SecurityException("Apenas administradores podem fechar chamados.");
                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("Opção inválida.");
                                }
                            } while (subOpcao != 0);
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
            } while (opcao != -1);
        }
    }
}
