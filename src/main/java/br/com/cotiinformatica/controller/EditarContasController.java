package br.com.cotiinformatica.controller;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import br.com.cotiinformatica.dtos.UsuarioDTO;
import br.com.cotiinformatica.entities.Conta;
import br.com.cotiinformatica.repositories.ContaRepository;
@Controller
public class EditarContasController {
	@Autowired
	ContaRepository contaRepository;
	
	//método para abrir a página de edição de contas
	@RequestMapping(value = "/admin/editar-contas")
	public ModelAndView editarContas(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("admin/editar-contas");
		
		try {
			
			//capturar o id enviado pela URL
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			//capturar o usuário autenticado na sessão
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("usuario_auth");
			
			//consultar a conta no banco de dados através do ID
			Conta conta = contaRepository.findById(id);
			
			//verificando se a conta foi encontrada
			//e se a conta pertence ao usuário autenticado
			if(conta != null && conta.getUsuario().getId() == usuarioDTO.getId()) {
				
				//enviando os dados da conta para a página
				modelAndView.addObject("id", conta.getId());
				modelAndView.addObject("nome", conta.getNome());
				modelAndView.addObject("data", new SimpleDateFormat("yyyy-MM-dd").format(conta.getData()));
				modelAndView.addObject("valor", conta.getValor());
				modelAndView.addObject("descricao", conta.getDescricao());
				modelAndView.addObject("tipo", conta.getTipo());
			}
			else {
				//voltar para a página de consulta
				modelAndView.setViewName("redirect:/admin/consultar-contas");
			}
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
		
		return modelAndView;
	}
	
	
	//método para capturar o SUBMIT POST do formulário de edição
	@RequestMapping(value = "/admin/editar-contas-post", method = RequestMethod.POST)
	public ModelAndView editarContasPost(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("admin/consultar-contas");
		
		try {
			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
		
		return modelAndView;
	}
}
