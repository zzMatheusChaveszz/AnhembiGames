package anhembigames;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    public boolean cadastrar(Produto p) {
        String sql = "INSERT INTO produtos (nome, categoria, plataforma, preco, quantidade, descricao) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCategoria());
            ps.setString(3, p.getPlataforma());
            ps.setDouble(4, p.getPreco());
            ps.setInt   (5, p.getQuantidade());
            ps.setString(6, p.getDescricao());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Produto p) {
        String sql = "UPDATE produtos SET nome=?, categoria=?, plataforma=?, "
                   + "preco=?, quantidade=?, descricao=? WHERE id=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCategoria());
            ps.setString(3, p.getPlataforma());
            ps.setDouble(4, p.getPreco());
            ps.setInt   (5, p.getQuantidade());
            ps.setString(6, p.getDescricao());
            ps.setInt   (7, p.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM produtos WHERE id=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public List<Produto> buscarPorNome(String nome) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE nome LIKE ? ORDER BY nome";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na busca: " + e.getMessage());
        }
        return lista;
    }

    public List<Produto> estoqueBaixo() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE quantidade <= 5 ORDER BY quantidade";
        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
        return lista;
    }

    private Produto mapear(ResultSet rs) throws SQLException {
        return new Produto(
            rs.getInt   ("id"),
            rs.getString("nome"),
            rs.getString("categoria"),
            rs.getString("plataforma"),
            rs.getDouble("preco"),
            rs.getInt   ("quantidade"),
            rs.getString("descricao")
        );
    }
}