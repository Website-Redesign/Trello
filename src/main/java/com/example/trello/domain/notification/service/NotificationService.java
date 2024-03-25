package com.example.trello.domain.notification.service;

import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.domain.notification.controller.NotificationController;
import com.example.trello.domain.notification.entity.Notification;
import com.example.trello.domain.notification.repository.NotificationRepository;
import com.example.trello.domain.worker.service.WorkerService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CommentService commentService;
    private final WorkerService workerService;

    private static Map<Long, Integer> notificationCounts = new HashMap<>();

    @Transactional
    public SseEmitter subscribe(Long userId) {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationController.sseEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));

        return sseEmitter;
    }

    @Transactional
    public void notifyComment(Long cardId) {

        Comment receiveComment = commentService.findLatestComment(cardId);

        List<Long> workers = workerService.findByCardId(cardId);

        for (Long workerId : workers) {

            if (NotificationController.sseEmitters.containsKey(workerId)) {
                SseEmitter sseEmitter = NotificationController.sseEmitters.get(workerId);
                try {
                    Map<String, String> eventData = new HashMap<>();
                    eventData.put("sender", receiveComment.getNickname() + " 님이 댓글을 작성했습니다.");
                    eventData.put("contents", receiveComment.getComment());

                    sseEmitter.send(SseEmitter.event().name("addComment").data(eventData));

                    Notification notification = Notification.builder()
                        .cardId(cardId)
                        .userId(workerId)
                        .sender(receiveComment.getNickname())
                        .contents(receiveComment.getComment())
                        .build();

                    notificationRepository.save(notification);

                    notificationCounts.put(workerId,
                        notificationCounts.getOrDefault(workerId, 0) + 1);

                    sseEmitter.send(SseEmitter.event().name("notificationCount")
                        .data(notificationCounts.get(workerId)));

                } catch (IOException e) {
                    NotificationController.sseEmitters.remove(workerId);
                }
            }
        }
    }

    public void deleteNotification(Long id) throws IOException {

        Notification notification = notificationRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("알림을 찾을 수 없습니다."));

        Long workerId = 0L;

        if (notification.getCardId() != null) {
            workerId = notification.getUserId();
        }

        notificationRepository.delete(notification);

        if (notificationCounts.containsKey(workerId)) {
            int currentCount = notificationCounts.get(workerId);
            if (currentCount > 0) {
                notificationCounts.put(workerId, currentCount - 1);
            }
        }

        SseEmitter sseEmitter = NotificationController.sseEmitters.get(workerId);
        sseEmitter.send(
            SseEmitter.event().name("notificationCount").data(notificationCounts.get(workerId)));
    }
}

