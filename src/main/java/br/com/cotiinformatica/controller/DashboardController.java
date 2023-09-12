package br.com.cotiinformatica.controller;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class DashboardController {
	@Autowired //injeção de dependência
	ContaRepository contaRepository;
	
	/*
	 * Método executado no momento em que a página é aberta
	 */
	@RequestMapping(value = "/admin/dashboard")
	public ModelAndView dashboard(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("admin/dashboard");
		
		try {
			
			//capturar os dados do usuário autenticado
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("usuario_auth");
			
			//capturando o primeiro dia do mês atual
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date primeiroDiaDoMes = calendar.getTime();
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date ultimoDiaDoMes = calendar.getTime();
			
			//fazendo a consulta das contas no banco de dados
			List<Conta> lista = contaRepository.findAll(primeiroDiaDoMes, ultimoDiaDoMes, usuarioDTO.getId());
			//fazendo o somatório das contas a pagar e receber
			Double totalReceitas = 0.0;
			Double totalDespesas = 0.0;
			
			for(Conta conta : lista) {
				if(conta.getTipo() == 1) totalReceitas += conta.getValor();
				else if(conta.getTipo() == 2) totalDespesas += conta.getValor();
			}
			
			//enviando os dados para a página
			modelAndView.addObject("listagem_contas", lista);
			modelAndView.addObject("total_receitas", totalReceitas);
			modelAndView.addObject("total_despesas", totalDespesas);
			modelAndView.addObject("data_inicio", new SimpleDateFormat("yyyy-MM-dd").format(primeiroDiaDoMes));
			modelAndView.addObject("data_fim", new SimpleDateFormat("yyyy-MM-dd").format(ultimoDiaDoMes));
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
		
		return modelAndView;
	}
}



