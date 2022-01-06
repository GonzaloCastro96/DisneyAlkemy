/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.controladores;

import challenge.alkemy.auth.JwtUtil;
import challenge.alkemy.entidades.AuthRequest;
import challenge.alkemy.entidades.Usuario;
import challenge.alkemy.servicios.IUsuarioServicio;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gonzalo
 */
@RestController
@RequestMapping("/auth")
public class UsuarioControlador {
    @Autowired
	private IUsuarioServicio us;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@ApiOperation(value="Registrar Usuario nuevo")
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody Usuario user) throws IOException{
		Map<String,Object> response = new HashMap<>();
			this.us.saveUser(user);
			this.us.EnvioMailBienvenida(user.getMail());
		response.put("message", "Registrado de forma exitosa");
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Login usuario")
	@PostMapping("/login")
	public ResponseEntity<?> generatetoken(@RequestBody AuthRequest authrequest) throws Exception {
		Map<String,Object> response = new HashMap<>();

		try {			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authrequest.getNombre(), authrequest.getClave()));
		}catch(Exception ex) {
			response.put("message", "Acceso Invalido");
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}

		
		response.put("token",jwtUtil.generateToken(authrequest.getNombre()) );
		response.put("message", "Autorizado");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
