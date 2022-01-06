/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.servicios;

import challenge.alkemy.entidades.Usuario;
import challenge.alkemy.errores.ErrorDeMailyUsuario;
import challenge.alkemy.repositorios.UsuarioRepositorio;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gonzalo
 */
@Service
public class UsuarioServicio implements IUsuarioServicio , UserDetailsService{

    @Autowired
    private UsuarioRepositorio ur;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    //Digite la clave de SendGrid.
    private final String KEY = "";

    @Transactional
    public void saveUser(Usuario user) {
        Usuario userresult = null;

        userresult = this.ur.findByUsernameOrEmail(user.getUsername(), user.getMail());

        if (userresult == null) {
            user.setClave(passwordEncoder.encode(user.getClave()));
            this.ur.save(user);

        } else {
            throw new ErrorDeMailyUsuario("El nombre de usuario o el mail no existe");
        }
    }
    
	public void EnvioMailBienvenida(String email) throws IOException{
		Email from = new Email("Disney@alkemy.org");
		String subject = "Bienvenido";
		Email to = new Email(email);
		Content content = new Content("texto", "Bienvenido a la Api Disney de Alkemy");
		Mail mail = new Mail(from, subject, to, content);
		
		SendGrid sg = new SendGrid(KEY);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}
        
	@Transactional(readOnly=true)
	public Usuario findByUsername(String username) {
		return this.ur.findByUsername(username);
	}
        
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			Usuario user = this.findByUsername(username);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getClave(),new ArrayList<>());
	}

}
