

CAlert = function () { }
CAlert.Warning = function (message,onclosefn) {
    iziToast.warning({
        title: 'Warning',
        resetOnHover: true,
        message: message,
        onClose: onclosefn
    });
}
CAlert.Success = function (message, onclosefn) {
    iziToast.success({
        title: 'OK',
        resetOnHover: true,
        message: message,
        onClose: onclosefn
    });
}
CAlert.Info = function (message, onclosefn) {
    iziToast.info({
        title: 'Info',
        resetOnHover: true,
        message: message,
        onClose: onclosefn
    });
}
CAlert.Danger = function (message, onclosefn) {
    iziToast.error({
        title: 'Error',
        resetOnHover: true,
        message: message,
        onClose: onclosefn
    });
}
CAlert.Notification = function (message, onclosefn) {
    iziToast.show({
        color: 'dark',
        icon: 'icon-person',
        title: 'Notification',
        message: message,
        resetOnHover:true,
        position: 'bottomCenter', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter
        progressBarColor: 'rgb(0, 255, 184)',
        onClose: onclosefn
    });
}


