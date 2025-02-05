package dz.kyrios.core.service.meeting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomService implements MeetingService {

    @Value("${zoom.client-id}")
    private String clientId;

    @Value("${zoom.client-secret}")
    private String clientSecret;

    @Value("${zoom.account-id}")
    private String accountId;

    private static final String ZOOM_TOKEN_URL = "https://zoom.us/oauth/token";
    private static final String ZOOM_MEETING_URL = "https://api.zoom.us/v2/users/me/meetings";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String createMeeting(String topic, String startTime, int duration) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("topic", topic);
        requestBody.put("type", 2); // Scheduled meeting
        requestBody.put("start_time", startTime);
        requestBody.put("duration", duration);
        requestBody.put("timezone", "UTC");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(ZOOM_MEETING_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            return (String) response.getBody().get("join_url");
        }
        throw new RuntimeException("Failed to create Zoom meeting");
    }

    public String getAccessToken() {
        String url = ZOOM_TOKEN_URL + "?grant_type=account_credentials&account_id=" + accountId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }
        throw new RuntimeException("Failed to fetch Zoom access token");
    }
}
