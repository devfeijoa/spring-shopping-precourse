package shopping;

import jakarta.validation.constraints.NotNull;

public record AddWishRequest(@NotNull Long productId) {}
