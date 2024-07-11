package com.evaza.etwitch.external.model;

import java.util.List;

public record StreamResponse(
        List<Stream> data
) {
}
