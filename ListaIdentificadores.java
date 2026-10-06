public class ListaIdentificadores {
    private static class No {
        int id;
        No proximo;

        No(int id) {
            this.id = id;
            this.proximo = null;
        }
    }

    public static class Iterador {
        private No atual;

        private Iterador(No sentinela) {
            this.atual = sentinela;
        }

        public boolean temProximo() {
            return atual.proximo != null;
        }

        public int proximo() {
            if (atual.proximo == null) {
                throw new IllegalStateException("Fim da lista");
            }
            atual = atual.proximo;
            return atual.id;
        }
    }

    private final No sentinela;
    private No tail;
    private int tamanho;

    public ListaIdentificadores() {
        sentinela = new No(-1);
        tail = sentinela;
        tamanho = 0;
    }

    public void inserirFim(int id) {
        No novo = new No(id);
        tail.proximo = novo;
        tail = novo;
        tamanho++;
    }

    public boolean remover(int id) {
        No anterior = sentinela;
        while (anterior.proximo != null) {
            if (anterior.proximo.id == id) {
                No removido = anterior.proximo;
                anterior.proximo = removido.proximo;
                if (removido == tail) {
                    tail = anterior;
                }
                tamanho--;
                return true;
            }
            anterior = anterior.proximo;
        }
        return false;
    }

    public boolean contem(int id) {
        No p = sentinela.proximo;
        while (p != null) {
            if (p.id == id) {
                return true;
            }
            p = p.proximo;
        }
        return false;
    }

    public int obter(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + posicao);
        }
        No p = sentinela.proximo;
        for (int i = 0; i < posicao; i++) {
            p = p.proximo;
        }
        return p.id;
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public void limpar() {
        sentinela.proximo = null;
        tail = sentinela;
        tamanho = 0;
    }

    public Iterador iterador() {
        return new Iterador(sentinela);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        No p = sentinela.proximo;
        while (p != null) {
            sb.append(p.id);
            if (p.proximo != null) {
                sb.append(", ");
            }
            p = p.proximo;
        }
        return sb.append("]").toString();
    }
}
