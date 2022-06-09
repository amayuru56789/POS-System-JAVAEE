function generateOrderID(){
    try{
        let lastOId = orderDB[orderDB.length-1].getOrderID();
        let newOId = parseInt(lastOId.substring(1,4))+1;
        if (newOId < 10){
            $("#txtOrderId").val("O00"+newOId);
        }else if (newOId < 100){
            $("#txtOrderId").val("O0"+newOId);
        }else {
            $("#txtOrderId").val("O"+newOId);
        }
    }catch (e){
        $("#txtOrderId").val("O001");
    }
}

$("#cmbCustIDs").click(function () {
    let custId = $("#cmbCustIDs").val();
    for (var i of customerDB){
        if (custId==i.getCustomerID()){
            $("#txtPCustName").val(i.getCustomerName());
            $("#txtPCustAddress").val(i.getCustomerAddress());
            $("#txtPCustSalary").val(i.getCustomerSalary());
        }
    }
});

function loadCustomerIds(){
    $("#cmbCustIDs").empty();
    var cutomer = customerDB;
    for (var i in cutomer){
        var opt = document.createElement("option");
        opt.value = cutomer[i].getCustomerID();
        opt.text = cutomer[i].getCustomerID();
        $("#cmbCustIDs").append(opt);
    }
}

$("#cmbItemCode").click(function () {
    let itemCode = $("#cmbItemCode").val();
    for (var i of itemDB){
        if (itemCode==i.getItemCODE()){
            $("#txtPItemName").val(i.getItemName());
            $("#txtPItemPrice").val(i.getItemPrice());
            $("#txtPItemQty").val(i.getItemQty());
        }
    }
});

function loadItemCode(){
    $("#cmbItemCode").empty();
    var item = itemDB;
    for (var i in item){
        var opt = document.createElement("option");
        opt.value = item[i].getItemCODE();
        opt.text = item[i].getItemCODE();
        $("#cmbItemCode").append(opt);
    }
}

function findTotal(){
    let tot = 0;
    $('#tblAddToCart > tr').each(function () {
        tot = tot + parseFloat($($(this).children().get(4)).text());
        $('#lblTot > span').text(tot).append('.00');

        if($("#txtDiscount").val()==""){

            $('#lblSubTot > span').text(tot).append('.00');
        }
    });
    t = tot;
}

$("#txtDiscount").on('keyup',function (){
    if ($('#txtDiscount').val() == '') {
        $('#lblSubTot > span').text('0.00');
    } else {
        let tot = parseFloat(t);
        let dis = tot/100 * parseFloat($('#txtDiscount').val());

        $('#lblSubTot > span').text(tot - dis).append('.00');
    }
});

function addToCart() {
    let itemCode = $("#cmbItemCode").val();
    let itemName = $("#txtPItemName").val();
    let itemPrice = $("#txtPItemPrice").val();
    let itemQty = $("#txtPItemQty").val();
    let OrderQty = $("#txtPOrderQty").val();

    let total = itemPrice * OrderQty;
    for (var i =0; i<cartDB.length; i++){
        if (cartDB[i].getICode()==itemCode){
            var newQTY = +cartDB[i].getIQty() + +OrderQty;
            var  newTot = itemPrice * newQTY;
            cartDB[i].setIQty(newQTY);
            cartDB[i].setITotal(newTot);
            /*console.log(newQTY);
            console.log(newTot);*/
            return;
        }
    }
    cartDB.push(new CartDTO(itemCode,itemName,itemPrice,OrderQty,total));
}

function qtyUpdate() {
    let item;
    var itemQty=$('#txtPItemQty').val();
    var orderQty=$('#txtPOrderQty').val();

    var updateQty=itemQty-orderQty;
    for (var i in itemDB){
        if($('#cmbItemCode').val()==itemDB[i].getItemCODE()){
            item=itemDB[i];
            item.setItemQty(updateQty);
            $('#txtPItemQty').val(item.getItemQty());
        }
    }
}

function loadCartTable(){
    $("#tblAddToCart").empty();
    for (var i of cartDB){
        let row = `<tr><td>${i.getICode()}</td><td>${i.getIName()}</td><td>${i.getIPrice()}</td><td>${i.getIQty()}</td><td>${i.getITotal()}</td></tr>`;
        $("#tblAddToCart").append(row);
    }
}

function PlaceOrder(){
    let oId = $("#txtOrderId").val();
    let date = $("#date").val();
    let custName = $("#txtPCustName").val();
    let total = $("#lblTot").text();
    orderDB.push(new OrderDTO(oId,custName,total,date));
}

function forOrder() {
    generateOrderID();
    loadCustomerIds();

    loadItemCode();
    findTotal();
}

$("#btnAddToCart").click(function () {
    addToCart();
    loadCartTable();
    findTotal();
    qtyUpdate();
});

$("#btnPurch").click(function () {
    PlaceOrder();
    generateOrderID();
    alert("Your order is confirmed");
});