package com.mpn.ecommerce.notification;

import com.mpn.ecommerce.notification.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}