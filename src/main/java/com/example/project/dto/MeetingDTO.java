package com.example.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDTO {
    @NonNull
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    @OneToOne
    private UUID createdById;
    @OneToOne
    private UUID invitedUserId;
    @NonNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime startDate;
    @NonNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime endDate;
}
