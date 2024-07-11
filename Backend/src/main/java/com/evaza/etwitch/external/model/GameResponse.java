package com.evaza.etwitch.external.model;

import java.util.List;

public record GameResponse(
        List<Game> data
) {
}

