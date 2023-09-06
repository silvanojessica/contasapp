package br.com.cotiinformatica.controller;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.cotiinformatica.dtos.UsuarioDTO;
import br.com.cotiinformatica.entities.Conta;
import br.com.cotiinformatica.repositories.ContaRepository;
@Controller
public class ExcluirContasController {
	@Autowired
	ContaRepository contaRepository;
	
	@RequestMapping(value = "/admin/excluir-contas")
	public ModelAndView excluirContas(HttpServletRequest request) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView("admin/consultar-contas");
		
		try {
			
			//capturar o id enviado na URL do link (querystring)
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			//capturar o usuário autenticado na sessão
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("usuario_auth");
			
			//consultar a conta no banco de dados
			Conta conta = contaRepository.findById(id);
			
			//verificando se a conta existe e se pretence ao usuário autenticado
			if(conta != null && conta.getUsuario().getId() == usuarioDTO.getId()) {
				
				//excluindo a conta no banco de dados
				contaRepository.delete(id);
				
				modelAndView.addObject("mensagem_sucesso", "Conta '" + conta.getNome() + "', excluída com sucesso.");
				
				//capturando as datas selecionadas que estão em sessão
				String dataInicio = (String) request.getSession().getAttribute("dt_inicio");
				String dataFim = (String) request.getSession().getAttribute("dt_fim");
				
				//realiza uma nova consulta de contas no banco de dados
				List<Conta> contas = contaRepository.findAll(
						new SimpleDateFormat("yyyy-MM-dd").parse(dataInicio),
						new SimpleDateFormat("yyyy-MM-dd").parse(dataFim),
						usuarioDTO.getId());
				
				//envia os dados da consulta para a página
				modelAndView.addObject("listagem_contas", contas);	
			}
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
		
		return modelAndView;
	}
}



