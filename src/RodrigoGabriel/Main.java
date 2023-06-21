package RodrigoGabriel;

import java.util.Scanner;

public class Main {
    static Scanner readKB = new Scanner(System.in);
    public static void main(String[] args) {
        Computador computador = new Computador();
        byte option = -1;

        do {
            do {
                System.out.println("[1] Incluir");
                System.out.println("[2] Alterar");
                System.out.println("[3] Deletar");
                System.out.println("[4] Pesquisar");
                System.out.println("[5] Registrar Venda");
                System.out.println("[0] Sair");

                option = readKB.nextByte();
                if (option < 0 || option > 5)
                    System.out.println("Opcao invalida");
            } while (option < 0 || option > 5);

            switch (option) {
                case 1 -> {
                    computador.includeComputer();
                }

                case 2 -> {
                    computador.alterData();
                }

                case 3 -> {
                    computador.deleteComputer();
                }

                case 4 -> {
                    computador.query();
                }

                case 5 -> {
                    computador.registerSale();
                }
            }
        } while (option != 0);
    }
}