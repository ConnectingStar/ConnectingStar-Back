package connectingstar.tars.pushnotification.command;

import com.google.firebase.messaging.*;
import connectingstar.tars.pushnotification.dto.PushNotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 모바일 기기로 보내는 푸시 알림 관련 Command Service.
 * FCM 을 사용하고 있음
 *
 * @author 이우진
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PushNotificationCommandService {
    private final FirebaseMessaging firebaseMessaging;

    /**
     * Firebase의 메시지 동시 전송 제한.
     *
     * @see <a href="https://firebase.google.com/docs/reference/admin/node/firebase-admin.messaging.messaging.md#messagingsendeach">Firebase - sendEach()</a>
     */
    private static final int FIREBASE_BATCH_SIZE = 500;

    /**
     * 여러 개의 메세지를 동시에 전송합니다.
     * 최대 500개의 메세지를 동시 전송할 수 있습니다.
     * 500개가 초과하면 나눠서 전송합니다.
     * @see <a href="https://firebase.google.com/docs/reference/admin/node/firebase-admin.messaging.messaging.md#messagingsendeach">Firebase - sendEach()</a>
     *
     * @author 이우진
     */
    public List<BatchResponse> sendMany(List<PushNotificationMessage> pushNotificationMessages) throws FirebaseMessagingException {
        List<BatchResponse> responses = new ArrayList<>();
        List<Message> firebaseMessages = pushNotificationMessages.stream()
                .map(this::toFirebaseMessage)
                .toList();

        // Firebase 동시 전송 제한 개수에 맞춰서 분할해서 전송
        List<List<Message>> batches = splitIntoBatches(firebaseMessages, FIREBASE_BATCH_SIZE);
        for (List<Message> batch : batches) {
            try {
                BatchResponse response = firebaseMessaging.sendEach(batch);
                responses.add(response);
            } catch (FirebaseMessagingException err) {
                log.error("Error sending batch", err);
            }
        }

        return responses;
    }

    /**
     * 프로젝트에서 정의한 메세지 DTO를 Firebase Message 형식으로 변환합니다.
     * FCM과의 결합도 낮추기 위해서 사용.
     *
     * @author 이우진
     */
    private Message toFirebaseMessage(PushNotificationMessage pushNotificationMessage) {
        Message message = Message.builder()
                .setToken(pushNotificationMessage.getToken())
                .setNotification(Notification.builder()
                        .setTitle(pushNotificationMessage.getTitle())
                        .setBody(pushNotificationMessage.getBody())
                        .build())
                .build();

        return message;
    }

    /**
     * 주어진 리스트를 지정된 크기의 작은 배치들로 분할합니다.
     *
     * @param original 분할할 원본 리스트
     * @param batchSize 각 배치의 최대 크기
     * @return 지정된 크기로 분할된 배치들의 리스트
     *
     * @author 이우진
     */
    private <T> List<List<T>> splitIntoBatches(List<T> original, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < original.size(); i += batchSize) {
            batches.add(original.subList(i, Math.min(i + batchSize, original.size())));
        }
        return batches;
    }
}
