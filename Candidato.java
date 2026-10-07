
public class Candidato {

    public static final int NUM_AREAS = 8;

    public static final String[] NOMES_AREAS = {
        "", "ESPORTES", "ARTES", "MÚSICA", "CINEMA",
        "TECNOLOGIA", "ANIMAIS", "GASTRONOMIA", "CIÊNCIAS"
    };

    private static final int[] PESOS_AREAS = {0, 1, 2, 1, 1, 3, 2, 2, 3};

    private int id;
    private String nome;
    private String estado;
    private char sexo;           
    private char sexoInteresse;   
    private int[] areas;         
    private ListaIdentificadores companheiros;

    public Candidato(int id, String nome, String estado, char sexo, char sexoInteresse, int[] areas) {
        this.id = id;
        this.companheiros = new ListaIdentificadores();
        atualizar(nome, estado, sexo, sexoInteresse, areas);
    }

    public void atualizar(String nome, String estado, char sexo, char sexoInteresse, int[] areas) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        if (estado == null || estado.trim().length() != 2) {
            throw new IllegalArgumentException("Sigla de estado inválida: " + estado);
        }
        char s = Character.toUpperCase(sexo);
        char si = Character.toUpperCase(sexoInteresse);
        if (s != 'F' && s != 'M') {
            throw new IllegalArgumentException("Sexo inválido: " + sexo);
        }
        if (si != 'F' && si != 'M' && si != 'I') {
            throw new IllegalArgumentException("Sexo de interesse inválido: " + sexoInteresse);
        }
        if (areas == null) {
            throw new IllegalArgumentException("Lista de áreas nula");
        }

        boolean[] marcada = new boolean[NUM_AREAS + 1];
        int qtd = 0;
        for (int i = 0; i < areas.length; i++) {
            int a = areas[i];
            if (a < 1 || a > NUM_AREAS) {
                throw new IllegalArgumentException("Área inválida: " + a);
            }
            if (!marcada[a]) {
                marcada[a] = true;
                qtd++;
            }
        }
        int[] novas = new int[qtd];
        int k = 0;
        for (int a = 1; a <= NUM_AREAS; a++) {
            if (marcada[a]) {
                novas[k++] = a;
            }
        }

        this.nome = nome.trim();
        this.estado = estado.trim().toUpperCase();
        this.sexo = s;
        this.sexoInteresse = si;
        this.areas = novas;
        this.companheiros.limpar();
    }

    public boolean temInteresse(int area) {
        for (int i = 0; i < areas.length; i++) {
            if (areas[i] == area) {
                return true;
            }
        }
        return false;
    }

    public boolean reside(String sigla) {
        return sigla != null && estado.equalsIgnoreCase(sigla.trim());
    }

    public int grauInteresseComum(Candidato v) {
        int grau = 0;
        for (int i = 0; i < areas.length; i++) {
            if (v.temInteresse(areas[i])) {
                grau += PESOS_AREAS[areas[i]];
            }
        }
        return grau;
    }

    private boolean aceitaSexoDe(Candidato v) {
        return sexoInteresse == 'I' || sexoInteresse == v.sexo;
    }

    public boolean verificaPotencialCompanheiro(Candidato v, int grauMinimo) {
        if (this.id == v.id) {
            return false;
        }
        if (!reside(v.estado)) {
            return false;
        }
        if (!aceitaSexoDe(v) || !v.aceitaSexoDe(this)) {
            return false;
        }
        int grau = grauInteresseComum(v);
        return grau > 0 && grau >= grauMinimo;
    }

    public void inserePotencialCompanheiro(Candidato v) {
        companheiros.inserirFim(v.getId());
    }

    public int numeroCompanheiros() {
        return companheiros.tamanho();
    }

    public ListaIdentificadores listaCompanheiros() {
        return companheiros;
    }

    public void limparCompanheiros() {
        companheiros.limpar();
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return estado;
    }

    public char getSexo() {
        return sexo;
    }

    public char getSexoInteresse() {
        return sexoInteresse;
    }

    public int numeroAreas() {
        return areas.length;
    }

    public int getArea(int posicao) {
        return areas[posicao];
    }

    @Override
    public String toString() {
        return id + "/" + nome;
    }
}
