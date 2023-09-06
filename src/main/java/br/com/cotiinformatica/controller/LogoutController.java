package br.com.cotiinformatica.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class LogoutController {
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {
		
		//apagar os dados do usuário gravado em sessão
		request.getSession().removeAttribute("usuario_auth");
		//apagar todos os valores gravados em sessão
		request.getSession().invalidate();
		
		//redirecionar de volta para a página de login do sistema
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/"); //raiz do sistema
		
		return modelAndView;
	}
}



