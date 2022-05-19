function ItemDTO(code,name,price,qty){
    var __code=code;
    var __name=name;
    var __price=price;
    var __qty=qty;

    this.getItemCODE=function() {
        return __code;
    }

    this.setItemCODE=function(code) {
        __code=code;
    }

    this.getItemName=function() {
        return __name;
    }

    this.setItemName=function(name) {
        __name=name;
    }

    this.getItemPrice=function() {
        return __price;
    }

    this.setItemPrice=function(price) {
        __price=price;
    }

    this.getItemQty=function() {
        return __qty;
    }

    this.setItemQty=function(qty) {
        __qty=qty;
    }
}