package com.kry.demo.pollingService.domain;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebService {

    @NonNull
    private Long id;

    @NonNull
    private String serviceName;

    @NonNull
    private String url;

    private boolean status;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private LocalDateTime lastUpdated;
}
