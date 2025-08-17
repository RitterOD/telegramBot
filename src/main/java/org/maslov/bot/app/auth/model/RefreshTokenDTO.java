package org.maslov.bot.app.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class RefreshTokenDTO {

    @NotBlank
    @Schema(example = "refreshToken", description = "Refresh token")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }
}
