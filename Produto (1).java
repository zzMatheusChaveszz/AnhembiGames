package anhembigames;

public class Produto {

    private int    id;
    private String nome;
    private String categoria;
    private String plataforma;
    private double preco;
    private int    quantidade;
    private String descricao;

    public Produto() {}

    public Produto(int id, String nome, String categoria, String plataforma,
                   double preco, int quantidade, String descricao) {
        this.id         = id;
        this.nome       = nome;
        this.categoria  = categoria;
        this.plataforma = plataforma;
        this.preco      = preco;
        this.quantidade = quantidade;
        this.descricao  = descricao;
    }

    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getNome()              { return nome; }
    public void   setNome(String nome)   { this.nome = nome; }

    public String getCategoria()                   { return categoria; }
    public void   setCategoria(String categoria)   { this.categoria = categoria; }

    public String getPlataforma()                    { return plataforma; }
    public void   setPlataforma(String plataforma)   { this.plataforma = plataforma; }

    public double getPreco()               { return preco; }
    public void   setPreco(double preco)   { this.preco = preco; }

    public int  getQuantidade()                  { return quantidade; }
    public void setQuantidade(int quantidade)    { this.quantidade = quantidade; }

    public String getDescricao()                   { return descricao; }
    public void   setDescricao(String descricao)   { this.descricao = descricao; }

    @Override
    public String toString() {
        return nome;
    }
}