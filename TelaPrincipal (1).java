package anhembigames;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends JFrame {

    private JTextField txtNome, txtCategoria, txtPlataforma, txtPreco, txtQuantidade, txtDescricao, txtBusca;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JLabel lblStatus;

    private final ProdutoDAO dao = new ProdutoDAO();

    public TelaPrincipal() {
        setTitle("Anhembi Games - Sistema de Produtos");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        carregarTabela(dao.listarTodos());
    }

    private void initUI() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(8, 8));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.setBackground(new Color(0, 168, 150));

        JLabel lblTitulo = new JLabel("ANHEMBI GAMES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(0, 168, 150));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                          painelFormulario(), painelTabela());
        split.setDividerLocation(310);
        split.setEnabled(false);
        split.setBackground(new Color(245, 245, 245));
        painelPrincipal.add(split, BorderLayout.CENTER);

        lblStatus = new JLabel("Pronto.");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 11));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 0));
        painelPrincipal.add(lblStatus, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JPanel painelFormulario() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);

        TitledBorder borderForm = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 168, 150)),
            "Dados do Produto",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 13));
        borderForm.setTitleColor(new Color(0, 130, 116));
        p.setBorder(borderForm);

        GridBagConstraints g = new GridBagConstraints();
        g.insets  = new Insets(4, 6, 4, 6);
        g.fill    = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        txtNome       = addCampo(p, g, "Nome:",       0);
        txtCategoria  = addCampo(p, g, "Categoria:",  1);
        txtPlataforma = addCampo(p, g, "Plataforma:", 2);
        txtPreco      = addCampo(p, g, "Preço (R$):", 3);
        txtQuantidade = addCampo(p, g, "Quantidade:", 4);
        txtDescricao  = addCampo(p, g, "Descrição:",  5);

        JPanel botoes = new JPanel(new GridLayout(2, 2, 6, 6));
        botoes.setBackground(Color.WHITE);

        JButton btnCadastrar = botao("Cadastrar",  new Color(0, 168, 150));
        JButton btnAtualizar = botao("Atualizar",  new Color(0, 130, 116));
        JButton btnRemover   = botao("Remover",    new Color(180, 40, 40));
        JButton btnLimpar    = botao("Limpar",     new Color(160, 160, 160));

        botoes.add(btnCadastrar);
        botoes.add(btnAtualizar);
        botoes.add(btnRemover);
        botoes.add(btnLimpar);

        g.gridy     = 6;
        g.gridx     = 0;
        g.gridwidth = 2;
        g.insets    = new Insets(10, 6, 6, 6);
        p.add(botoes, g);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnRemover  .addActionListener(e -> remover());
        btnLimpar   .addActionListener(e -> limparCampos());

        return p;
    }

    private JPanel painelTabela() {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.setBackground(Color.WHITE);

        TitledBorder borderTabela = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 168, 150)),
            "Produtos em Estoque",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 13));
        borderTabela.setTitleColor(new Color(0, 130, 116));
        p.setBorder(borderTabela);

        JPanel barraBusca = new JPanel(new BorderLayout(4, 0));
        barraBusca.setBackground(Color.WHITE);
        txtBusca = new JTextField();
        estilizarCampo(txtBusca);
        JButton btnBuscar       = botao("Buscar",        new Color(0, 168, 150));
        JButton btnVerTodos     = botao("Ver Todos",     new Color(160, 160, 160));
        JButton btnEstoqueBaixo = botao("Estoque Baixo", new Color(230, 126, 34));
        barraBusca.add(new JLabel("  🔍 "), BorderLayout.WEST);
        barraBusca.add(txtBusca, BorderLayout.CENTER);
        JPanel btnsBusca = new JPanel(new GridLayout(1, 3, 4, 0));
        btnsBusca.setBackground(Color.WHITE);
        btnsBusca.add(btnBuscar);
        btnsBusca.add(btnVerTodos);
        btnsBusca.add(btnEstoqueBaixo);
        barraBusca.add(btnsBusca, BorderLayout.EAST);
        p.add(barraBusca, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Categoria", "Plataforma", "Preço", "Qtd"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setBackground(Color.WHITE);
        tabela.setForeground(Color.BLACK);
        tabela.setGridColor(new Color(220, 220, 220));
        tabela.setSelectionBackground(new Color(0, 168, 150));
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setRowHeight(22);
        tabela.getTableHeader().setBackground(new Color(0, 168, 150));
        tabela.getTableHeader().setForeground(Color.BLACK);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(40);

        p.add(new JScrollPane(tabela), BorderLayout.CENTER);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherFormulario();
        });
        btnBuscar      .addActionListener(e -> buscar());
        btnVerTodos    .addActionListener(e -> carregarTabela(dao.listarTodos()));
        btnEstoqueBaixo.addActionListener(e -> {
            carregarTabela(dao.estoqueBaixo());
            status("Mostrando produtos com estoque baixo (≤ 5 unidades)");
        });

        return p;
    }

    private void cadastrar() {
        if (!validar()) return;
        Produto p = coletarDados();
        if (dao.cadastrar(p)) {
            carregarTabela(dao.listarTodos());
            limparCampos();
            status("Produto cadastrado com sucesso!");
        }
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { aviso("Selecione um produto na tabela para atualizar."); return; }
        if (!validar()) return;
        Produto p = coletarDados();
        p.setId((int) modeloTabela.getValueAt(linha, 0));
        if (dao.atualizar(p)) {
            carregarTabela(dao.listarTodos());
            limparCampos();
            status("Produto atualizado com sucesso!");
        }
    }

    private void remover() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { aviso("Selecione um produto na tabela para remover."); return; }
        String nome = modeloTabela.getValueAt(linha, 1).toString();
        int confirma = JOptionPane.showConfirmDialog(this,
            "Deseja remover o produto:\n" + nome + "?",
            "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            if (dao.remover(id)) {
                carregarTabela(dao.listarTodos());
                limparCampos();
                status("Produto removido com sucesso!");
            }
        }
    }

    private void buscar() {
        String termo = txtBusca.getText().trim();
        if (termo.isEmpty()) { carregarTabela(dao.listarTodos()); return; }
        List<Produto> resultado = dao.buscarPorNome(termo);
        carregarTabela(resultado);
        status("Busca: " + resultado.size() + " produto(s) encontrado(s) para [" + termo + "]" );
    }

    private void carregarTabela(List<Produto> lista) {
        modeloTabela.setRowCount(0);
        for (Produto p : lista) {
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getCategoria(),
                p.getPlataforma(), String.format("R$ %.2f", p.getPreco()), p.getQuantidade()
            });
        }
        status("Total: " + lista.size() + " produto(s) listado(s).");
    }

    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        txtNome      .setText(modeloTabela.getValueAt(linha, 1).toString());
        txtCategoria .setText(modeloTabela.getValueAt(linha, 2).toString());
        txtPlataforma.setText(modeloTabela.getValueAt(linha, 3).toString());
        String preco = modeloTabela.getValueAt(linha, 4).toString().replace("R$ ", "").replace(",", ".");
        txtPreco     .setText(preco);
        txtQuantidade.setText(modeloTabela.getValueAt(linha, 5).toString());
    }

    private Produto coletarDados() {
        Produto p = new Produto();
        p.setNome      (txtNome.getText().trim());
        p.setCategoria (txtCategoria.getText().trim());
        p.setPlataforma(txtPlataforma.getText().trim());
        p.setPreco     (Double.parseDouble(txtPreco.getText().replace(",", ".")));
        p.setQuantidade(Integer.parseInt(txtQuantidade.getText().trim()));
        p.setDescricao (txtDescricao.getText().trim());
        return p;
    }

    private boolean validar() {
        if (txtNome.getText().trim().isEmpty())      { aviso("Informe o nome do produto.");  return false; }
        if (txtCategoria.getText().trim().isEmpty()) { aviso("Informe a categoria.");        return false; }
        try { Double.parseDouble(txtPreco.getText().replace(",", ".")); }
        catch (NumberFormatException e) { aviso("Preço inválido. Use números (ex: 99.90)"); return false; }
        try { Integer.parseInt(txtQuantidade.getText().trim()); }
        catch (NumberFormatException e) { aviso("Quantidade inválida. Use números inteiros."); return false; }
        return true;
    }

    private void limparCampos() {
        txtNome.setText(""); txtCategoria.setText(""); txtPlataforma.setText("");
        txtPreco.setText(""); txtQuantidade.setText(""); txtDescricao.setText("");
        tabela.clearSelection();
    }

    private JTextField addCampo(JPanel p, GridBagConstraints g, String rotulo, int linha) {
        g.gridx = 0; g.gridy = linha; g.gridwidth = 1; g.weightx = 0;
        JLabel lbl = new JLabel(rotulo);
        lbl.setForeground(new Color(60, 60, 60));
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        p.add(lbl, g);

        g.gridx = 1; g.weightx = 1;
        JTextField tf = new JTextField();
        estilizarCampo(tf);
        p.add(tf, g);
        return tf;
    }

    private void estilizarCampo(JTextField tf) {
        tf.setBackground(new Color(245, 250, 249));
        tf.setForeground(new Color(30, 30, 30));
        tf.setCaretColor(new Color(0, 168, 150));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 168, 150)),
            BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        tf.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private JButton botao(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 11));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void aviso(String msg)  { JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE); }
    private void status(String msg) { lblStatus.setText(msg); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new TelaPrincipal().setVisible(true);
        });
    }
}
