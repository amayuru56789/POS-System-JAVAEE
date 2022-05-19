function forOrderDetails(){

    OrderTable();
}

function OrderTable(){
    /*alert("Hello");*/
    $("#orderTable").empty();
    for(var i of orderDB){
        /*console.log("Hi");*/
        let row = `<tr><td>${i.getOrderID()}</td><td>${i.getCustName()}</td><td>${i.getOrderTotal()}</td><td>${i.getOrderDate()}</td></tr>`;
        $("#orderTable").append(row);

    }
}