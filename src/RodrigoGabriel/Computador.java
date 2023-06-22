package RodrigoGabriel;

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
    private int sold;
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

    private long searchComputer(String codCompSearch) {
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
                sold       = computerFile.readInt();
                dtLastSale      = computerFile.readUTF();

                if (codCompSearch.equals(codComp) && active == 'S') {
                    computerFile.close();
                    return cursorLocation;
                }
            }
        } catch (EOFException e) {
            System.out.println("Computador nao cadastrado.");
            return -1;
        } catch (IOException e) {
            System.out.println("Erro na abertura do arquivo - programa sera finalizado");
            System.exit(0);
            return -1;
        }
    }

    private String generateCodComp (String codCompSearch) {
        String temp;
        int serialNum;
        temp = findLastOCcurrence(codCompSearch.toUpperCase().substring(0,2));
        if (temp.equals("no")) {
            temp = codCompSearch.toUpperCase().substring(0,2) + "0001";
        } else {
            serialNum = Integer.parseInt(temp.substring(2));
            temp = temp.toUpperCase().substring(0,2);
            serialNum++;
            if (serialNum < 10) {
                temp = temp + "000" + Integer.toString(serialNum);
            } else if (serialNum < 100) {
                temp = temp + "00" + Integer.toString(serialNum);
            } else if (serialNum < 1000) {
                temp = temp + "0" + Integer.toString(serialNum);
            } else {
                temp = temp + Integer.toString(serialNum);
            }
        }
        return temp;
    }

    private String findLastOCcurrence (String compBrand) {
        String lastOccurrence = "no";
        try {
        	long cursorLocation = 0;
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
                sold        = computerFile.readInt();
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

    private void saveComputer () {
        try {
            RandomAccessFile computerFile = new RandomAccessFile("COMP.DAT", "rw");

            computerFile.seek(computerFile.length());
            computerFile.writeChar(active);
            computerFile.writeUTF(codComp);
            computerFile.writeUTF(brand);
            computerFile.writeUTF(model);
            computerFile.writeUTF(processor);
            computerFile.writeInt(memorySize);
            computerFile.writeInt(screenSize);
            computerFile.writeInt(amountInStock);
            computerFile.writeFloat(price);
            computerFile.writeInt(sold);
            computerFile.writeUTF(dtLastSale);
            computerFile.close();
            System.out.println("Dados gravados com sucesso!\n");
        } catch (IOException e) {
            System.out.println("Erro na abertura do arquivo - programa sera finalizado");
            System.exit(0);
        }
    }

    private void deactivateComputer (long pos) {
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

    private void printHeader() {
        System.out.println("-CODIGO-----MARCA-----MODELO-----PROCESSADOR-----MEMORIA-----TELA-----ESTOQUE-----PRECO-----VENDIDO-----ULTIMA VENDA-----TOTAL-----");
    }
    private void printComputer() {
        String stockInString;
        String soldInString;

        stockInString = String.valueOf(amountInStock);
        soldInString = String.valueOf(sold);

        System.out.print(" " + codComp + "     ");
        System.out.print(formatString(brand, 7) + "   ");
        System.out.print(formatString(model, 9) + "  ");
        System.out.print(formatString(processor, 13) + "    ");
        System.out.print(" " + memorySize + "         ");
        System.out.print(screenSize + "      ");
        System.out.print(formatString(stockInString, 7) + "   ");
        if(price < 10000)
            System.out.print(" ");
        System.out.printf("%.2f     ", price);
        System.out.print(formatString(soldInString, 7) + "    ");
        System.out.print(" " + dtLastSale + "     ");
        System.out.printf("%.2f\n", price*sold);
    }

    private void printFooter(int totalSold, float totalIncome) {
        String tSoldInString;

        tSoldInString = String.valueOf(totalSold);

        System.out.print(formatString("TOTAIS:", 93));
        System.out.print(formatString(tSoldInString, 9));
        System.out.print(formatString("", 18));
        System.out.printf("%.2f\n", totalIncome);
        System.out.println();
    }

    private String formatString(String text, int size) {
        if (text.length() > size) {
            text = text.substring(0, size);
        }else{
            while (text.length() < size) {
                text = text + " ";
            }
        }
        return text;
    }

    private void computerVerification() {
        System.out.println("Codigo.................: " + codComp);
        System.out.println("Marca..................: " + brand);
        System.out.println("Modelo.................: " + model);
        System.out.println("Processador............: " + processor);
        System.out.println("Quantidade de memoria..: " + memorySize);
        System.out.println("Tamanho da tela........: " + screenSize);
        System.out.println("Quantidade em estoque..: " + amountInStock);
        System.out.println("Preco..................: " + price);
        System.out.println();
    }

    private boolean validateDate(String vDate, boolean isSale) {
        if (!isSale) {
            if (vDate.length() == 7 && vDate.charAt(2) == '/') {
                try {
                    Integer.parseInt(vDate.substring(0,2));
                    Integer.parseInt(vDate.substring(3));
                    return true;
                } catch (NumberFormatException E) {
                    System.out.println("Data invalida.");
                }
            }
        } else {
            if (vDate.length() == 10 && vDate.charAt(2) == '/' && vDate.charAt(5) == '/') {
                try {
                    Integer.parseInt(vDate.substring(0,2));
                    Integer.parseInt(vDate.substring(3,5));
                    Integer.parseInt(vDate.substring(6));
                    return true;
                } catch (NumberFormatException E) {
                    System.out.println("Data invalida.");
                }
            }
        }
        return false;
    }

    public void includeComputer() {
        char confirmation;
        String aux;
        do {
            Main.readKB.nextLine();
            System.out.println("Inserir marca: \nMarcas permitidas:\nDell, Lenovo, HP, Positivo, Asus, Apple, IBM");
            brand = Main.readKB.next();
            if (!consistBrand(brand))
                System.out.println("Marca invalida");
        } while (!consistBrand(brand));

        aux = brand;

        codComp = generateCodComp(brand);

        brand = aux;

        do {
            System.out.println("Inserir modelo: ");
            model = Main.readKB.next();
            if (model.length() == 0)
                System.out.println("Modelo deve ser digitado");
        } while (model.length() == 0);

        do {
            Main.readKB.nextLine();
            System.out.println("Inserir processador:\nProcessadores permitidos:\nIntel Core i3, Intel Core i5, Intel Core i7, Intel Core i9, AMD Ryzen, AMD Athlon");
            processor = Main.readKB.nextLine();
            if (!consistProcessor(processor))
                System.out.println("Processador invalido");
        } while (!consistProcessor(processor));

        do {
//            Main.readKB.nextLine();
            System.out.println("Inserir quantidade de memoria:\nValores permitidos:\n1 ate 16");
            memorySize = Main.readKB.nextInt();
            if (memorySize < 1 || memorySize > 16)
                System.out.println("Quantidade de memoria invalida");
        } while (memorySize < 1 || memorySize > 16);

        do {
            Main.readKB.nextLine();
            System.out.println("Inserir tamanho da tela:\nValores permitidos:\n10, 12, 15, 20, 25, 28");
            screenSize = Main.readKB.nextInt();
            if (!consistScreenSize(screenSize))
                System.out.println("Tamanho de tela invalido");
        } while (!consistScreenSize(screenSize));

        do {
            Main.readKB.nextLine();
            System.out.println("Quantidade em estoque: ");
            amountInStock = Main.readKB.nextInt();
            if (amountInStock < 0)
                System.out.println("Quantidade invalida");
        } while (amountInStock < 0);

        do {
            Main.readKB.nextLine();
            System.out.println("Inserir o preco:\nValores permitidos:\n1000 ate 20000");
            price = Main.readKB.nextFloat();
            if (price < 1000 || price > 20000)
                System.out.println("Preco invalido");
        } while (price < 1000 || price > 20000);

        active = 'S';
        sold = 0;
        dtLastSale = "01/01/2000";

        computerVerification();

        System.out.println("Confirma a gravacao dos dados (S/N)?");
        confirmation = Main.readKB.next().charAt(0);
        if (confirmation == 'S' || confirmation == 's') {
            saveComputer();
        } else {
            System.out.println("Dados nao foram salvos. Se desejar, repita a operacao.");
        }
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
                System.out.println("[1] Modelo.................: " + model);
                System.out.println("[2] Processador............: " + processor);
                System.out.println("[3] Memoria................: " + memorySize + "gb");
                System.out.println("[4] Tamanho da Tela........: " + screenSize);
                System.out.println("[5] Quantidade em estoque..: " + amountInStock);
                System.out.println("[6] Preco..................: " + price);

                System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar): ");
                option = Main.readKB.nextByte();

                switch (option) {
                    case 1 -> {
                    	do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo modelo: ");
                            model = Main.readKB.nextLine();

                            if (model.length() == 0)
                                System.out.println("Modelo precisa ser digitado.");
                        } while (model.length() == 0);
                    }
                    case 2 -> {
                    	do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo processador: ");
                            tempStr = Main.readKB.next();
                        } while (!consistProcessor(tempStr));
                        processor = tempStr;
                    }
                    case 3 -> {
                    	do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova quantidade de memoria: ");
                            tempInt = Main.readKB.nextInt();
                        } while (tempInt < 1 || tempInt > 16);
                        memorySize = tempInt;
                    }
                    case 4 -> {
                    	do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo tamanho de tela: ");
                            tempInt = Main.readKB.nextInt();
                        } while (!consistScreenSize(tempInt));
                        screenSize = tempInt;
                    }
                    case 5 -> {
                    	do {
                            Main.readKB.nextLine();
                            System.out.println("Digite a nova quantidade em estoque: ");
                            tempInt = Main.readKB.nextInt();
                        } while (tempInt < 0);
                        amountInStock = tempInt;
                    }
                    case 6 -> {
                    	do {
                            Main.readKB.nextLine();
                            System.out.println("Digite o novo preco: ");
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
            computerVerification();

            System.out.println("Confirma a exclusao do computador (S/N)? ");
            confirmation = Main.readKB.next().charAt(0);
            if (confirmation == 'S' || confirmation == 's') {
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
            System.out.println("----------------------CONSULTA----------------------");
            System.out.println("[1] Consultar apenas 1 computador:");
            System.out.println("[2] Listar todos os computadores:");
            System.out.println("[3] Listar computadores ja vendidos:");
            System.out.println("[4] Listar computadores por data de venda (mes/ano):");
            System.out.println("[5] Listar computadores por faixa de preco:");
            System.out.println("[0] Sair");

            option = Main.readKB.nextByte();

            switch (option) {
                case 1 -> {
                    Main.readKB.nextLine();
                    System.out.println("Digite o codigo do computador:");
                    searchComp = Main.readKB.next();
                    regPos = searchComputer(searchComp);

                    if (regPos != -1) {
                        printHeader();
                        printComputer();
                        Main.readKB.nextLine();
                    }

                    break;
                }
                case 2 -> {
                	boolean computerFound = false;
                    float totalIncome = 0;
                    int totalSold = 0;
                	try {
						computerFile = new RandomAccessFile("COMP.DAT", "rw");
                        printHeader();
						while (true) {
                            active = computerFile.readChar();
							codComp = computerFile.readUTF();
							brand = computerFile.readUTF();
							model = computerFile.readUTF();
							processor = computerFile.readUTF();
							memorySize = computerFile.readInt();
                            screenSize = computerFile.readInt();
							amountInStock = computerFile.readInt();
							price = computerFile.readFloat();
							sold = computerFile.readInt();
							dtLastSale = computerFile.readUTF();
							
							if (active == 'S') {
                                totalSold += sold;
                                totalIncome += sold*price;
								printComputer();
								computerFound = true;
							}
						}
					} catch (EOFException e) {
						if(!computerFound)
							System.out.println("Nenhum computador listado.");
                        else
                            printFooter(totalSold, totalIncome);
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa sera finalizado");
			            System.exit(0);
					}
                	break;
                }
                case 3 -> {
                	boolean computerFound = false;
                    float totalIncome = 0;
                    int totalSold = 0;
                	try {
						computerFile = new RandomAccessFile("COMP.DAT", "rw");
                        printHeader();
						while(true) {
                            active = computerFile.readChar();
                            codComp = computerFile.readUTF();
                            brand = computerFile.readUTF();
                            model = computerFile.readUTF();
                            processor = computerFile.readUTF();
                            memorySize = computerFile.readInt();
                            screenSize = computerFile.readInt();
                            amountInStock = computerFile.readInt();
                            price = computerFile.readFloat();
                            sold = computerFile.readInt();
                            dtLastSale = computerFile.readUTF();
							
							if (active == 'S' && sold > 0) {
                                totalSold += sold;
                                totalIncome += sold*price;
								printComputer();
								computerFound = true;
							}
						}
					} catch (EOFException e) {
						if(!computerFound)
							System.out.println("Nenhum computador vendido.");
                        else
                            printFooter(totalSold, totalIncome);
						
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa sera finalizado");
			            System.exit(0);
					}
                	break;
                }
                case 4 -> {
                	Main.readKB.nextLine();
                	String date;
                	boolean computerFound = false;
                	do {
                		System.out.println("Insira data de venda (MM/AAAA):");
                		date = Main.readKB.next();
                	} while(!validateDate(date, false));

                    float totalIncome = 0;
                    int totalSold = 0;

                	try {
						computerFile = new RandomAccessFile("COMP.DAT", "rw");
                        printHeader();
						while(true) {
                            active = computerFile.readChar();
                            codComp = computerFile.readUTF();
                            brand = computerFile.readUTF();
                            model = computerFile.readUTF();
                            processor = computerFile.readUTF();
                            memorySize = computerFile.readInt();
                            screenSize = computerFile.readInt();
                            amountInStock = computerFile.readInt();
                            price = computerFile.readFloat();
                            sold = computerFile.readInt();
                            dtLastSale = computerFile.readUTF();
							
							if (active == 'S' && dtLastSale.substring(3).equals(date)) {
                                totalSold += sold;
                                totalIncome += sold*price;
								printComputer();
								computerFound = true;
							}
						}
					} catch (EOFException e) {
						if(!computerFound)
							System.out.println("Nenhum computador vendido nesta data.");
                        else
                            printFooter(totalSold, totalIncome);
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa sera finalizado");
			            System.exit(0);
					}
                	break;
                	
                }
                case 5 -> {
                	float lowerCap;
                	float higherCap;
                	boolean computerFound = false;
                	Main.readKB.nextLine();
                	
                	System.out.println("Insira o valor minimo:");
                	lowerCap = Main.readKB.nextFloat();
                	
                	System.out.println("Insira o valor maximo:");
                	higherCap = Main.readKB.nextFloat();

                    float totalIncome = 0;
                    int totalSold = 0;

                	try {
						computerFile = new RandomAccessFile("COMP.DAT", "rw");
                        printHeader();
						while(true) {
                            active = computerFile.readChar();
                            codComp = computerFile.readUTF();
                            brand = computerFile.readUTF();
                            model = computerFile.readUTF();
                            processor = computerFile.readUTF();
                            memorySize = computerFile.readInt();
                            screenSize = computerFile.readInt();
                            amountInStock = computerFile.readInt();
                            price = computerFile.readFloat();
                            sold = computerFile.readInt();
                            dtLastSale = computerFile.readUTF();
							
							if (active == 'S' && price >= lowerCap && price <= higherCap) {
                                totalSold += sold;
                                totalIncome += sold*price;
								printComputer();
								computerFound = true;
							}
						}
					} catch (EOFException e) {
						if (!computerFound)
							System.out.println("Computador nao encontrado nessa faixa de preco.");
                        else
                            printFooter(totalSold, totalIncome);
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa sera finalizado");
			            System.exit(0);
					}
                	break;
                }
                case 0 -> {
                    break;
                }
                default -> System.out.println("Opcao invalida.");
            }
        } while (option != 0);
        
    }

    public void registerSale() {
        String saleDate;
        String computerKey;
        int amountSold;
        char saleConfirmation;
        long regPos;

        do {
            Main.readKB.nextLine();
            System.out.println("Digite o codigo do computador a ser vendido:");
            computerKey = Main.readKB.next();
        } while (searchComputer(computerKey) == -1);

        computerVerification();

        do {
            Main.readKB.nextLine();
            System.out.println("Digite a quantidade vendida:");
            amountSold = Main.readKB.nextInt();
            if (amountSold > amountInStock)
                System.out.println("Quantidade nao disponivel em estoque.");
            if (amountSold <= 0)
                System.out.println("Valor deve ser superior a 0");
        } while (amountSold > amountInStock || amountSold <= 0);

        do {
            Main.readKB.nextLine();
            System.out.println("Inserir data de venda (DD/MM/AAAA):");
            saleDate = Main.readKB.next();
        } while (!validateDate(saleDate, true));

        System.out.println(saleDate);
        System.out.println(amountSold);
        System.out.println("Confirma venda (S/N)?");
        saleConfirmation = Main.readKB.next().charAt(0);

        if (saleConfirmation == 's' || saleConfirmation == 'S') {
            regPos = searchComputer(computerKey);
            amountInStock -= amountSold;
            sold += amountSold;
            try {
                RandomAccessFile computerFile = new RandomAccessFile("COMP.DAT", "rw");

                computerFile.seek(regPos);
                computerFile.writeChar(active);
                computerFile.writeUTF(codComp);
                computerFile.writeUTF(brand);
                computerFile.writeUTF(model);
                computerFile.writeUTF(processor);
                computerFile.writeInt(memorySize);
                computerFile.writeInt(screenSize);
                computerFile.writeInt(amountInStock);
                computerFile.writeFloat(price);
                computerFile.writeInt(sold);
                computerFile.writeUTF(saleDate);
                computerFile.close();
                System.out.println("Dados gravados com sucesso!\n");
            } catch (IOException e) {
                System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
                System.exit(0);
            }
        }
    }
}