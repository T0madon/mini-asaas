function NotificationListController() {
    let _this = this;
    this.reference = $(".has-atlas");
    let notificationListReference = this.reference.find("#notification-center");
    this.init = function () {
        $.ajax({
            url: _this.reference.find(".js-notification-url").val(),
            type: 'GET',
            async: true,
            success: function (data) {
                if (!data) return;

                notificationListReference.replaceWith(data);
            }
        })
    }
}

let notificationListController;

$(document).ready(function() {
    notificationListController = new NotificationListController();
    notificationListController.init();
});