package shopping;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final MemberService memberService;
    private final Map<String, List<Wish>> wishesByEmail = new ConcurrentHashMap<>();

    public WishController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Wish> getWishes(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String email = memberService.findEmailByAuthorization(authorization);
        return List.copyOf(wishesByEmail.getOrDefault(email, List.of()));
    }
}
