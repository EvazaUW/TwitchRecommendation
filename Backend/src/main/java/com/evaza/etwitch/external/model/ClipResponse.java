package com.evaza.etwitch.external.model;

import java.util.List;

public record ClipResponse(
        List<Clip> data
) {
}
