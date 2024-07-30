package com.vasylenko.application.model.team.member;

import com.vasylenko.application.model.user.UserId;
import jakarta.validation.constraints.NotNull;

/**
 * Class representing the parameters for a team member.
 */
public record TeamMemberParameters(
        @NotNull UserId memberId,
        @NotNull TeamMemberPosition position) {
}
