package br.com.cotiinformatica.repositories;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import br.com.cotiinformatica.entities.Conta;
import br.com.cotiinformatica.entities.Usuario;
public class ContaRepository {
	private JdbcTemplate jdbcTemplate;
	
	public ContaRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//método para inserir uma conta no banco de dados
	public void create(Conta conta) throws Exception {
		
		//escrevendo a instrução SQL para gravar a conta na tabela do banco
		String sql = "insert into conta(nome, descricao, data, valor, tipo, usuario_id) values(?,?,?,?,?,?)";
		
		//criando os parametros que serão preenchidos no comando sql
		Object[] params = {
			conta.getNome(),
			conta.getDescricao(),
			new java.sql.Date(conta.getData().getTime()),
			conta.getValor(),
			conta.getTipo(),
			conta.getUsuario().getId()
		};
		
		//executando o comando SQL no banco de dados
		jdbcTemplate.update(sql, params);
	}
	
	//método para atualizar uma conta no banco de dados
	public void update(Conta conta) throws Exception {
		
		//escrevendo a instrução SQL para atualizar a conta na tabela do banco
		String sql = "update conta set nome=?, descricao=?, data=?, valor=?, tipo=? where id=?";
		
		//criando os parametros que serão preenchidos no comando sql
		Object[] params = {
			conta.getNome(),
			conta.getDescricao(),
			new java.sql.Date(conta.getData().getTime()),
			conta.getValor(),
			conta.getTipo(),			
			conta.getId()
		};		
		
		//executando
		jdbcTemplate.update(sql, params);
	}
	
	//método para excluir uma conta no banco de dados
	public void delete(Integer id) throws Exception {
		
		//escrevendo a instrução SQL para excluir a conta na tabela do banco
		String sql = "delete from conta where id=?";
		
		//criando os parametros que serão preenchidos no comando sql
		Object[] params = { id };
		
		//executando
		jdbcTemplate.update(sql, params);
	}
	
	//método para consultar contas através de um periodo de datas e de um usuário
	public List<Conta> findAll(Date dataMin, Date dataMax, Integer usuarioId) throws Exception {
		
		//escrevendo a instrução SQL para consultar as contas na tabela do banco
		String sql = "select * from conta "
				 + "where data between ? and ? "
				 + "and usuario_id = ? "
				 + "order by data";
		
		Object[] params = {
			new java.sql.Date(dataMin.getTime()),
			new java.sql.Date(dataMax.getTime()),
			usuarioId
		};
		
		List<Conta> contas = jdbcTemplate.query(sql, params, new RowMapper<Conta>() {
			@Override
			public Conta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Conta conta = new Conta();
				conta.setUsuario(new Usuario());
				
				conta.setId(rs.getInt("id"));
				conta.setNome(rs.getString("nome"));
				conta.setDescricao(rs.getString("descricao"));
				conta.setValor(rs.getDouble("valor"));
				conta.setTipo(rs.getInt("tipo"));
				conta.getUsuario().setId(rs.getInt("usuario_id"));
				
				try {
					conta.setData(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("data")));
				}
				catch(Exception e) {
					e.printStackTrace();
				}			
				
				return conta;
			}
			
		});
		
		return contas;
	}
	
	//método para consultar 1 conta através do ID
	public Conta findById(Integer id) throws Exception {
			
		//escrevendo a instrução SQL para consultar as contas na tabela do banco
		String sql = "select * from conta where id = ?";
			
		Object[] params = { id };
			
		List<Conta> contas = jdbcTemplate.query(sql, params, new RowMapper<Conta>() {
			@Override
			public Conta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Conta conta = new Conta();
				conta.setUsuario(new Usuario());
					
				conta.setId(rs.getInt("id"));
				conta.setNome(rs.getString("nome"));
				conta.setDescricao(rs.getString("descricao"));
				conta.setValor(rs.getDouble("valor"));
				conta.setTipo(rs.getInt("tipo"));
				conta.getUsuario().setId(rs.getInt("usuario_id"));
					
				try {
					conta.setData(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("data")));
				}
				catch(Exception e) {
					e.printStackTrace();
				}			
					
				return conta;
			}
				
		});
			
		//verificando se alguma conta foi encontrada
		if(contas.size() == 1) {
			//returnando os dados da conta
			return contas.get(0);
		}
		else {
			return null; //vazio
		}
	}
}



