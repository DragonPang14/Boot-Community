window.onload = function () {
    var locationItem = window.localStorage.getItem("closeable");
    if(locationItem == "true"){
        window.close();
    }
}
