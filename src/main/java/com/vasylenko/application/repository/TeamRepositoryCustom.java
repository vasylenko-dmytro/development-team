package com.vasylenko.application.repository;

import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.member.TeamMemberId;

public interface TeamRepositoryCustom {
    TeamId nextId();

    TeamMemberId nextMemberId();
}
