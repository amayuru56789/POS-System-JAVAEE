function CartDTO(itemCode,itemName,price,qty,total){
    var __itemCode=itemCode;
    var __itemName=itemName;
    var __itemPrice=price;
    var __itemQTY=qty;
    var __itemTot=total;

    this.getICode = function() {
        return __itemCode;
    }

    this.setICode = function(code) {
        __itemCode=itemCode;
    }

    this.getIName = function() {
        return __itemName;
    }

    this.setIName = function(name) {
        __itemName=itemName;
    }

    this.getIPrice = function() {
        return __itemPrice;
    }

    this.setIPrice = function(price) {
        __itemPrice=price;
    }

    this.getIQty = function() {
        return __itemQTY;
    }

    this.setIQty = function(qty) {
        __itemQTY=qty;
    }

    this.getITotal = function() {
        return __itemTot;
    }

    this.setITotal = function(total) {
        __itemTot=total;
    }
}