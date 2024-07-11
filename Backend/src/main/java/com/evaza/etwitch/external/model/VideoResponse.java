package com.evaza.etwitch.external.model;

import java.util.List;

public record VideoResponse(
        List<Video> data
) {
}

