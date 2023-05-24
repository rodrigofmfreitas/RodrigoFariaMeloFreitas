package RodrigoFariaMeloFreitas;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Computador {
    private String[] marcas = {"Dell", "Lenovo", "HP", "Positivo", "Asus", "Apple", "IBM"};
    private String[] processadores = {"Intel Core i3", "Intel Core i5", "Intel Core i7",
                                     "Intel Core i9", "AMD Ryzen", "AMD Athlon"};
    private int[] tamanhoTelas = {10, 12, 15, 20, 25, 28};


    private char ativo;
    private String codComp;
    private String marca;
    private String modelo;
    private String processador;
    private int quantMemoria;
    private int tamanhoTela;
    private int quantEstoque;
    private float preco;
    private int quantVendida;
    private String dtUltimaVenda;

    private boolean consistirMarca(String marcaCheck) {
        for (byte i = 0; i < marcas.length; i++) {
            if (marcaCheck.equals(marcas[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean consistirProcessador(String procesCheck) {
        for (byte i = 0; i < processadores.length; i++) {
            if (procesCheck.equals(processadores[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean consistirTamanhoTela(int tTela) {
        for (byte i = 0; i < tamanhoTelas.length; i++) {
            if (tTela == tamanhoTelas[i]) {
                return true;
            }
        }
        return false;
    }

    public long searchComputer(String compSearch) {
        long cursorLocation = 0;
        try {
            RandomAccessFile computerFile = new RandomAccessFile("COMP.DAT", "rw");
            while (true) {
                cursorLocation  = computerFile.getFilePointer();
                ativo           = computerFile.readChar();
                codComp         = computerFile.readUTF();
                marca           = computerFile.readUTF();
                modelo          = computerFile.readUTF();
                processador     = computerFile.readUTF();
                quantMemoria    = computerFile.readInt();
                tamanhoTela     = computerFile.readInt();
                quantEstoque    = computerFile.readInt();
                preco           = computerFile.readFloat();
                quantVendida    = computerFile.readInt();
                dtUltimaVenda   = computerFile.readUTF();

                if (compSearch.equals(codComp) && ativo == 'S') {
                    computerFile.close();
                    return cursorLocation;
                }
            }
        } catch (EOFException e) {
            return -1;
        } catch (IOException e) {
            System.out.println("Erro na abertura do arquivo - programa sera finalizado");
            System.exit(0);
            return -1;
        }
    }

    public String findLastOCcurrence (String compBrand) {
        long cursorLocation = 0;
        String lastOccurrence = "no";
        try {
            RandomAccessFile computerFile = new RandomAccessFile("COMP.DAT", "rw");
            while (true) {
                cursorLocation  = computerFile.getFilePointer();
                ativo           = computerFile.readChar();
                codComp         = computerFile.readUTF();
                marca           = computerFile.readUTF();
                modelo          = computerFile.readUTF();
                processador     = computerFile.readUTF();
                quantMemoria    = computerFile.readInt();
                tamanhoTela     = computerFile.readInt();
                quantEstoque    = computerFile.readInt();
                preco           = computerFile.readFloat();
                quantVendida    = computerFile.readInt();
                dtUltimaVenda   = computerFile.readUTF();

                if (compBrand.equals(codComp.substring(0, 2)) && ativo == 'S') {
                    lastOccurrence = codComp;
                }
            }
        } catch (EOFException e) {
            return lastOccurrence;
        } catch (IOException e) {
            System.out.println("Erro na abertura do arquivo - programa sera finalizado");
            System.exit(0);
            return "error";
        }
    }

    public void saveComputer () {
        try {
            RandomAccessFile computerFile = new RandomAccessFile("COMP.DAT", "rw");

            computerFile.seek(computerFile.length());
            computerFile.writeChar(ativo);
            computerFile.writeUTF(codComp);
            computerFile.writeUTF(marca);
            computerFile.writeUTF(modelo);
            computerFile.writeUTF(processador);
            computerFile.writeInt(quantMemoria);
            computerFile.writeInt(quantEstoque);
            computerFile.writeFloat(preco);
            computerFile.writeInt(quantVendida);
            computerFile.writeUTF(dtUltimaVenda);
            computerFile.close();
            System.out.println("Dados gravados com sucesso!\n");
        } catch (IOException e) {
            System.out.println("Erro na abertura do arquivo - programa sera finalizado");
            System.exit(0);
        }
    }

    public void deactivateComputer (long pos) {
        try {
            RandomAccessFile computerFile = new RandomAccessFile("COMP.DAT", "rw");

            computerFile.seek(pos);
            computerFile.writeChar('N');
            computerFile.close();
        } catch (IOException e) {
            System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
            System.exit(0);
        }
    }

    public void addComp() {
        char confirmation;
        do {
            System.out.print("Inserir marca: ");
            marca = Main.readKB.next();
            if (!consistirMarca(marca))
                System.out.println("Marca invalida");
        } while (!consistirMarca(marca));

        do {
            System.out.print("Inserir modelo: ");
            modelo = Main.readKB.next();
            if (modelo.length() == 0)
                System.out.println("Modelo deve ser digitado");
        } while (modelo.length() == 0);

        do {
            System.out.print("Inserir processador: ");
            processador = Main.readKB.next();
            if (!consistirProcessador(processador))
                System.out.println("Processador invalido");
        } while (!consistirProcessador(processador));

        do {
            System.out.print("Inserir quantidade de memoria: ");
            quantMemoria = Main.readKB.nextInt();
            if (quantMemoria < 1 || quantMemoria > 16)
                System.out.println("Quantidade de memoria invalida");
        } while (quantMemoria < 1 || quantMemoria > 16);

        do {
            System.out.print("Inserir tamanho da tela: ");
            tamanhoTela = Main.readKB.nextInt();
            if (!consistirTamanhoTela(tamanhoTela))
                System.out.println("Tamanho de tela invalido");
        } while (!consistirTamanhoTela(tamanhoTela));

        do {
            System.out.print("Quantidade em estoque: ");
            quantEstoque = Main.readKB.nextInt();
            if (quantEstoque < 0)
                System.out.println("Quantidade invalida");
        } while (quantEstoque < 0);

        do {
            System.out.print("Inserir o preco: ");
            preco = Main.readKB.nextFloat();
            if (preco < 1000 || preco > 20000)
                System.out.println("Preco invalido");
        } while (preco < 1000 || preco > 20000);

        ativo = 'S';
        quantVendida = 0;
        dtUltimaVenda = "01/01/2000";

        if (marca.equals("Dell")) {
            codComp = generateCodComp("DE");
        } else if (marca.equals("Lenovo")) {
            codComp = generateCodComp("LE");
        } else if (marca.equals("HP")) {
            codComp = generateCodComp("HP");
        } else if (marca.equals("Positivo")) {
            codComp = generateCodComp("PO");
        } else if (marca.equals("Asus")) {
            codComp = generateCodComp("AS");
        } else if (marca.equals("Apple")) {
            codComp = generateCodComp("AP");
        } else {
            codComp = generateCodComp("IB");
        }

        System.out.println("\nConfirma a gravacao dos dados (S/N)?");
        confirmation = Main.readKB.next().charAt(0);
        if (confirmation == 'S') {
            saveComputer();
            System.out.println("Dados salvos com sucesso.");
        } else {
            System.out.println("Dados nao foram salvos. Se desejar, repita a operacao.");
        }
    }

    public String generateCodComp (String compBrand) {
        String temp;
        int serialNum;
        temp = findLastOCcurrence(compBrand);
        if (temp.equals("no")) {
            temp = compBrand + "0001";
        } else {
            serialNum = Integer.parseInt(temp.substring(2));
            if (serialNum < 10) {
                temp = compBrand + "000" + Integer.toString(serialNum);
            } else if (serialNum < 100) {
                temp = compBrand + "00" + Integer.toString(serialNum);
            } else if (serialNum < 1000) {
                temp = compBrand + "0" + Integer.toString(serialNum);
            } else {
                temp = compBrand + Integer.toString(serialNum);
            }
        }
        return temp;
    }

    public void alterData () {
        String computerKey;
        char confirmation;
        long regPos = 0;
        byte option;
        String tempStr;
        int tempInt;
        float tempFlo;

        System.out.println("Digite o registro que deseja alterar: ");
        computerKey = Main.readKB.next();

        regPos = searchComputer(computerKey);

        if (regPos == -1) {
            System.out.println("Registro nao encontrado.");
        } else {
            ativo = 'S';

            do {
                System.out.println("[1] Marca..................: " + marca);
                System.out.println("[2] Modelo.................: " + modelo);
                System.out.println("[3] Processador............: " + processador);
                System.out.println("[4] Memoria................: " + quantMemoria + "gb");
                System.out.println("[5] Tamanho da Tela........: " + tamanhoTela);
                System.out.println("[6] Quantidade em estoque..: " + quantEstoque);
                System.out.println("[7] Preco..................: " + preco);

                System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar): ");
                option = Main.readKB.nextByte();

                switch (option) {
                    case 1 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova marca: ");
                            tempStr = Main.readKB.next();
                        } while (!consistirMarca(tempStr));
                        marca = tempStr;
                    }
                    case 2 -> {
                        Main.readKB.nextLine();
                        System.out.println("Digite o novo modelo: ");
                        modelo = Main.readKB.nextLine();
                    }
                    case 3 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo processador: ");
                            tempStr = Main.readKB.next();
                        } while (!consistirProcessador(tempStr));
                        processador = tempStr;
                    }
                    case 4 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova quantidade de memoria: ");
                            tempInt = Main.readKB.nextInt();
                        } while (tempInt < 1 || tempInt > 16);
                        quantMemoria = tempInt;
                    }
                    case 5 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo tamanho de tela: ");
                            tempInt = Main.readKB.nextInt();
                        } while (!consistirTamanhoTela(tempInt));
                        tamanhoTela = tempInt;
                    }
                    case 6 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova quantidade em estoque: ");
                            tempInt = Main.readKB.nextInt();
                        } while (tempInt < 0);
                        quantEstoque = tempInt;
                    }
                    case 7 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo preco: ");
                            tempFlo = Main.readKB.nextFloat();
                        } while (tempFlo < 1000 || tempFlo > 20000);
                        preco = tempFlo;
                    }
                    default -> System.out.println("Opcao invalida.");
                }
            } while (option != 0);

            System.out.println("\nConfirma a alteracao dos dados (S/N)?");
            confirmation = Main.readKB.next().charAt(0);
            if (confirmation == 'S') {
                deactivateComputer(regPos);
                saveComputer();
                System.out.println("Dados alterados com sucesso!");
            } else {
                System.out.println("Alteracao descartada.");
            }
        }
    }
}
