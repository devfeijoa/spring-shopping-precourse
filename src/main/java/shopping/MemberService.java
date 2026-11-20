package shopping;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemberService {
    private final Map<String, String> passwordHashesByEmail = new ConcurrentHashMap<>();
    private final Map<String, String> emailsByToken = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder =
            Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public TokenResponse register(MemberRequest request) {
        String email = request.email();
        String passwordHash = passwordEncoder.encode(request.password());
        String existing = passwordHashesByEmail.putIfAbsent(email, passwordHash);

        if (existing != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입한 이메일입니다.");
        }
        return issueToken(email);
    }

    private TokenResponse issueToken(String email) {
        String token = UUID.randomUUID().toString();
        emailsByToken.put(token, email);
        return new TokenResponse(token);
    }
}
