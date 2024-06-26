package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class handles takeaway orders.
 */
public class TakeawayOrder extends Order implements NotifyAction {
    private LocalTime estimatedPickupTime;

    /**
     * The constructor to initiate a takeaway order with empty basket.
     *
     * @param orderId   - the unique id.
     * @param customer  - the customer who is making the order.
     * @param orderDate - the date of the order.
     * @param orderTime - the time of the order.
     */
    public TakeawayOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime) {
        super(orderId, customer, orderDate, orderTime, "takeaway", "pending-kitchen");
        // TODO review this is needed or not
        // Default 30 mins for now - restaurant policy to prepare within 30
        // mins.
//        estimatedPickupTime = orderTime.plusMinutes(30);
    }

    // TODO comment to get updated data
    public TakeawayOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime, LocalTime estimatedPickupTime,
                         String orderStatus, List<FoodDrink> orderedList) {
        super(orderId, customer, orderDate, orderTime, "takeaway", orderStatus, orderedList);
        this.estimatedPickupTime = estimatedPickupTime;
    }

    /**
     * Getter method to get the date of the order.
     *
     * @return the order date.
     */
    public LocalDate getOrderDate() {
        return super.getOrderDate();
    }

    /**
     * Getter method to get the time of the order.
     *
     * @return the order time.
     */
    public LocalTime getOrderTime() {
        return super.getOrderTime();
    }

    /**
     * Getter method to get the type of the order.
     *
     * @return the order type.
     */
    public String getOrderType() {
        return super.getOrderType();
    }

    /**
     * Getter method to get the status of the order.
     *
     * @return the order status.
     */
    public String getOrderStatus() {
        return super.getOrderStatus();
    }

    /**
     * This method adds pickup time in minutes.
     *
     * @param addTimeInMinutes - the time in minutes.
     * @return the new pickup time for UI.
     */
    public LocalTime newEstimatedPickupTime(int addTimeInMinutes) {
        return LocalTime.now().plusMinutes(addTimeInMinutes);
    }

    // TODO comment
    public void setEstimatedPickupTime() {
        estimatedPickupTime = newEstimatedPickupTime(30);
    }

    /**
     * Getter method to get the pickup time.
     *
     * @return the pickup time.
     */
    public LocalTime getPickupTime() {
        return estimatedPickupTime;
    }

    // TODO comment
    public String getPickupTimeInFormat() {
        return estimatedPickupTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    // TODO comment
    public String completeTakeawayOrderMessage() {
        return String.format(
                "Your order no.%d is ready for pickup at %s.",
                super.getOrderId(),
                getPickupTimeInFormat()
        );
    }

    @Override
    public void notifyCustomer(Customer customer,
                               boolean isSuccessfulRequest)
            throws TextFileNotFoundException {

        try {

            NotificationBoard notificationBoard = new NotificationBoard();
            String notificationType = "takeaway";
            Notification newNotification = notificationBoard.createNewNotification(
                    customer.getCustomerId(),
                    notificationType
            );

            if (isSuccessfulRequest) {
                newNotification.setMessageBody(
                        completeTakeawayOrderMessage()
                );
            }
            notificationBoard.addNotificationToDatabase(
                    newNotification
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
