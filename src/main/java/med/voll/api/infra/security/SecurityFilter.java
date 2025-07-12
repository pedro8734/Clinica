package med.voll.api.infra.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domian.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entrante al filtro");
       var tokenJWT = recuperarToken(request);
       if(tokenJWT != null){
           var subject = tokenService.getSubject(tokenJWT);
           var usuario = usuarioRepository.findByLogin(subject);

           var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authentication);
           System.out.println("usurio logeando");
       }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return  authorizationHeader.replace("Bearer ","");

        }
       return  null;
    }
}
