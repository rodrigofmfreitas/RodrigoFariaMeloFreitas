package RodrigoFariaMeloFreitas;

public class Computador {
    private String[] marcas = {"Dell", "Lenovo", "HP", "Positivo", "Asus", "Apple", "IBM"};
    private String[] processadores = {"Intel Core i3", "Intel Core i5", "Intel Core i7",
                                     "Intel Core i9", "AMD Ryzen", "AMD Athlon"};
    private int[] tamanhoTelas = {10, 12, 15, 20, 25, 28};


    private String codComp;
    private char ativo;
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

    public void incluir() {
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
    }
}
