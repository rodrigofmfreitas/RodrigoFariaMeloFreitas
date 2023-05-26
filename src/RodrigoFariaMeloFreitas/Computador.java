package RodrigoFariaMeloFreitas;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Computador {
    private String[] brandsList = {"Dell", "Lenovo", "HP", "Positivo", "Asus", "Apple", "IBM"};
    private String[] processorsList = {"Intel Core i3", "Intel Core i5", "Intel Core i7",
                                     "Intel Core i9", "AMD Ryzen", "AMD Athlon"};
    private int[] screenSizeList = {10, 12, 15, 20, 25, 28};


    private char active;
    private String codComp;
    private String brand;
    private String model;
    private String processor;
    private int memorySize;
    private int screenSize;
    private int amountInStock;
    private float price;
    private int totalSold;
    private String dtLastSale;

    private boolean consistBrand(String brandCheck) {
        for (byte i = 0; i < brandsList.length; i++) {
            if (brandCheck.equals(brandsList[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean consistProcessor(String procesCheck) {
        for (byte i = 0; i < processorsList.length; i++) {
            if (procesCheck.equals(processorsList[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean consistScreenSize(int tTela) {
        for (byte i = 0; i < screenSizeList.length; i++) {
            if (tTela == screenSizeList[i]) {
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
                active          = computerFile.readChar();
                codComp         = computerFile.readUTF();
                brand           = computerFile.readUTF();
                model           = computerFile.readUTF();
                processor       = computerFile.readUTF();
                memorySize      = computerFile.readInt();
                screenSize      = computerFile.readInt();
                amountInStock   = computerFile.readInt();
                price           = computerFile.readFloat();
                totalSold       = computerFile.readInt();
                dtLastSale      = computerFile.readUTF();

                if (compSearch.equals(codComp) && active == 'S') {
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
                cursorLocation   = computerFile.getFilePointer();
                active           = computerFile.readChar();
                codComp          = computerFile.readUTF();
                brand            = computerFile.readUTF();
                model            = computerFile.readUTF();
                processor        = computerFile.readUTF();
                memorySize       = computerFile.readInt();
                screenSize       = computerFile.readInt();
                amountInStock    = computerFile.readInt();
                price            = computerFile.readFloat();
                totalSold        = computerFile.readInt();
                dtLastSale       = computerFile.readUTF();

                if (compBrand.equals(codComp.substring(0, 2)) && active == 'S') {
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
            computerFile.writeChar(active);
            computerFile.writeUTF(codComp);
            computerFile.writeUTF(brand);
            computerFile.writeUTF(model);
            computerFile.writeUTF(processor);
            computerFile.writeInt(memorySize);
            computerFile.writeInt(amountInStock);
            computerFile.writeFloat(price);
            computerFile.writeInt(totalSold);
            computerFile.writeUTF(dtLastSale);
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
            System.out.print("Inserir brand: ");
            brand = Main.readKB.next();
            if (!consistBrand(brand))
                System.out.println("Marca invalida");
        } while (!consistBrand(brand));

        do {
            System.out.print("Inserir model: ");
            model = Main.readKB.next();
            if (model.length() == 0)
                System.out.println("Modelo deve ser digitado");
        } while (model.length() == 0);

        do {
            System.out.print("Inserir processor: ");
            processor = Main.readKB.next();
            if (!consistProcessor(processor))
                System.out.println("Processador invalido");
        } while (!consistProcessor(processor));

        do {
            System.out.print("Inserir quantidade de memoria: ");
            memorySize = Main.readKB.nextInt();
            if (memorySize < 1 || memorySize > 16)
                System.out.println("Quantidade de memoria invalida");
        } while (memorySize < 1 || memorySize > 16);

        do {
            System.out.print("Inserir tamanho da tela: ");
            screenSize = Main.readKB.nextInt();
            if (!consistScreenSize(screenSize))
                System.out.println("Tamanho de tela invalido");
        } while (!consistScreenSize(screenSize));

        do {
            System.out.print("Quantidade em estoque: ");
            amountInStock = Main.readKB.nextInt();
            if (amountInStock < 0)
                System.out.println("Quantidade invalida");
        } while (amountInStock < 0);

        do {
            System.out.print("Inserir o price: ");
            price = Main.readKB.nextFloat();
            if (price < 1000 || price > 20000)
                System.out.println("Preco invalido");
        } while (price < 1000 || price > 20000);

        active = 'S';
        totalSold = 0;
        dtLastSale = "01/01/2000";

        if (brand.equals("Dell")) {
            codComp = generateCodComp("DE");
        } else if (brand.equals("Lenovo")) {
            codComp = generateCodComp("LE");
        } else if (brand.equals("HP")) {
            codComp = generateCodComp("HP");
        } else if (brand.equals("Positivo")) {
            codComp = generateCodComp("PO");
        } else if (brand.equals("Asus")) {
            codComp = generateCodComp("AS");
        } else if (brand.equals("Apple")) {
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
            active = 'S';

            do {
                System.out.println("[1] Marca..................: " + brand);
                System.out.println("[2] Modelo.................: " + model);
                System.out.println("[3] Processador............: " + processor);
                System.out.println("[4] Memoria................: " + memorySize + "gb");
                System.out.println("[5] Tamanho da Tela........: " + screenSize);
                System.out.println("[6] Quantidade em estoque..: " + amountInStock);
                System.out.println("[7] Preco..................: " + price);

                System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar): ");
                option = Main.readKB.nextByte();

                switch (option) {
                    case 1 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova brand: ");
                            tempStr = Main.readKB.next();
                        } while (!consistBrand(tempStr));
                        brand = tempStr;
                    }
                    case 2 -> {
                        Main.readKB.nextLine();
                        System.out.println("Digite o novo model: ");
                        model = Main.readKB.nextLine();
                    }
                    case 3 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo processor: ");
                            tempStr = Main.readKB.next();
                        } while (!consistProcessor(tempStr));
                        processor = tempStr;
                    }
                    case 4 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova quantidade de memoria: ");
                            tempInt = Main.readKB.nextInt();
                        } while (tempInt < 1 || tempInt > 16);
                        memorySize = tempInt;
                    }
                    case 5 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo tamanho de tela: ");
                            tempInt = Main.readKB.nextInt();
                        } while (!consistScreenSize(tempInt));
                        screenSize = tempInt;
                    }
                    case 6 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova quantidade em estoque: ");
                            tempInt = Main.readKB.nextInt();
                        } while (tempInt < 0);
                        amountInStock = tempInt;
                    }
                    case 7 -> {
                        do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo price: ");
                            tempFlo = Main.readKB.nextFloat();
                        } while (tempFlo < 1000 || tempFlo > 20000);
                        price = tempFlo;
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

    public void deleteComputer () {
        String searchComp;
        char confirmation;
        long regPos = 0;

        System.out.println("Digite o codigo do computador que deseja excluir: ");
        searchComp = Main.readKB.next();

        regPos = searchComputer(searchComp);
        if (regPos == -1) {
            System.out.println("Computador nao cadastrado no arquivo\n");
        } else {
            System.out.println("Marca..................: " + brand);
            System.out.println("Modelo.................: " + model);
            System.out.println("Processador............: " + processor);
            System.out.println("Quantidade de Memoria..: " + memorySize);
            System.out.println("Tamanho da tela........: " + screenSize);
            System.out.println();

            System.out.println("\nConfirma a exclusao do computador (S/N)? ");
            confirmation = Main.readKB.next().charAt(0);
            if (confirmation == 'S') {
                deactivateComputer(regPos);
            } else {
                System.out.println("Exclusao cancelada.");
            }
        }
    }

    public void query () {
        RandomAccessFile computerFile;
        byte option;
        String searchComp;
        long regPos;

        do {
            System.out.println("[1] Consultar apenas 1 computador:");
            System.out.println("[2] Listar todos os computadores:");
            System.out.println("[3] Listar computadores de determinada marca:");
            System.out.println("[4] Listar computadores com determinado processador:");
            System.out.println("[5] Listar computadores com determinado tamanho de tela:");
            System.out.println("[0] Sair");

            option = Main.readKB.nextByte();

            switch (option) {
                case 1 -> {
                    Main.readKB.nextLine();
                    System.out.println("Digite o codigo do computador:");
                    searchComp = Main.readKB.next();
                    regPos = searchComputer(searchComp);

                    if (regPos == -1) {
                        System.out.println("Computador nao cadastrado.");
                    } else {
                        //printComputer
                    }
                }
                case 2 -> {

                }
                case 3 -> {

                }
                case 4 -> {

                }
                case 5 -> {

                }
                case 0 -> {
                }
                default -> System.out.println("Opcao invalida.");
            }
        } while (option != 0);

    }
}
