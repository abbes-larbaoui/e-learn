package dz.kyrios.adminservice.event.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileCreatedEvent {
    private Long id;
    private Long userId;
    private String userUuid;
}
